package com.meowlove.profileservice.service;

import com.meowlove.profileservice.client.FileFeignClient;
import com.meowlove.profileservice.dto.profile.*;
import com.meowlove.profileservice.exception.profile.ProfileNotFoundException;
import com.meowlove.profileservice.exception.profile.UploadProfileImageException;
import com.meowlove.profileservice.exception.profile.UserIDNotUniqueException;
import com.meowlove.profileservice.exception.profile.UsernameNotUniqueException;
import com.meowlove.profileservice.exception.security.PermissionDeniedException;
import com.meowlove.profileservice.model.Profile;
import com.meowlove.profileservice.repository.ProfileRepository;
import com.meowlove.profileservice.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final SecurityService securityService;
    private final ModelMapper modelMapper;
    private final FileFeignClient fileFeignClient;

    /// создание профиля (для сервиса аунтетификации)
    @Transactional
    public CreateProfileResponseDTO createProfile(CreateProfileRequestDTO createProfileRequestDTO) {

        if (!isUsernameUnique(createProfileRequestDTO.getUsername())) {
            throw new UsernameNotUniqueException("The username is already in use");
        }

        UUID userId = securityService.getUUIDFromSecurityContext();

        if (!isUserIdUnique(userId)) {
            throw new UserIDNotUniqueException("The userId is already in use");
        }

        Profile profile = new Profile();

        profile.setUserId(userId);
        profile.setUsername(createProfileRequestDTO.getUsername());
        profile.setNickname(createProfileRequestDTO.getNickname());
        profile.setAge(createProfileRequestDTO.getAge());

        return modelMapper.map(profileRepository.save(profile), CreateProfileResponseDTO.class);
    }

    // получение профиля для страницы профиля
    public GetProfileOverviewResponseDTO getProfileOverview(UUID profileId) {
        Profile profile = getProfile(profileId);
        return modelMapper.map(profile, GetProfileOverviewResponseDTO.class);
    }

    // редактирование профиля
    @Transactional
    public UpdateProfileResponseDTO updateProfile(UUID profileId,
                                                  UpdateProfileRequestDTO updateProfileRequestDTO) {
        Profile profile = getProfile(profileId);

        verifyProfileOwner(profile.getUserId());

        if (!isUsernameUnique(updateProfileRequestDTO.getUsername()) &&
        !updateProfileRequestDTO.getUsername().equals(profile.getUsername())) {
            throw new UsernameNotUniqueException("The username is already in use");
        }

        profile.setUsername(updateProfileRequestDTO.getUsername());
        profile.setNickname(updateProfileRequestDTO.getNickname());
        profile.setAge(updateProfileRequestDTO.getAge());

        return modelMapper.map(profileRepository.save(profile), UpdateProfileResponseDTO.class);
    }

    // обновление фотографии
    @Transactional
    public String uploadProfileImage(String uuid, MultipartFile image) {

        if (image.isEmpty()) {
            throw new UploadProfileImageException("The image is empty");
        }

        Profile profile = profileRepository.findProfileByProfileId(UUID.fromString(uuid)).orElseThrow(
                () -> new ProfileNotFoundException("Profile not found")
        );

        verifyProfileOwner(profile.getUserId());

        String photoLink = fileFeignClient.uploadMedia("profiles/profileImages",
                Collections.singletonList(image).toArray(new MultipartFile[0])).getFirst();

        String oldPhotoLink = profile.getPhotoLink();

        if (oldPhotoLink != null) {
            fileFeignClient.deleteMedia(Collections.singletonList(oldPhotoLink).toArray(new String[0]));
        }

        profile.setPhotoLink(photoLink);

        profileRepository.save(profile);

        return photoLink;
    }

    // удаление профиля
    @Transactional
    public void deleteProfile(UUID profileId) {
        Profile profile = getProfile(profileId);

        verifyProfileOwner(profile.getUserId());

        profileRepository.delete(profile);
    }

    // получение всего профиля по id
    private Profile getProfile(UUID profileId) {
        return profileRepository.findProfileByProfileId(profileId).orElseThrow(
                () -> new ProfileNotFoundException("Profile with id " + profileId.toString() + " not found"));
    }

    // проверка уникальности юзернейма (для создания профиля)
    private Boolean isUsernameUnique(String username) {
        return !profileRepository.existsProfileByUsername(username);
    }

    // проверка уникальности user id (для создания профиля)
    private Boolean isUserIdUnique(UUID userId) {
        return !profileRepository.existsProfileByUserId(userId);
    }

    // проверка владельца профиля
    private void verifyProfileOwner(UUID userId) {
        if (!securityService.getUUIDFromSecurityContext().equals(userId)) {
            throw new PermissionDeniedException("Permission denied");
        }
    }
}
