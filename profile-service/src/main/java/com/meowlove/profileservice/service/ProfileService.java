package com.meowlove.profileservice.service;

import com.meowlove.profileservice.dto.profile.*;
import com.meowlove.profileservice.exception.profile.ProfileNotFoundException;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final SecurityService securityService;
    private final ModelMapper modelMapper;

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
