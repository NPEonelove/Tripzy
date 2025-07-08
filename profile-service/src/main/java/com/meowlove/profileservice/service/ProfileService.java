package com.meowlove.profileservice.service;

import com.meowlove.profileservice.dto.profile.*;
import com.meowlove.profileservice.exception.profile.EmailNotUniqueException;
import com.meowlove.profileservice.exception.profile.IncorrectPasswordException;
import com.meowlove.profileservice.exception.profile.ProfileNotFoundException;
import com.meowlove.profileservice.exception.profile.UsernameNotUniqueException;
import com.meowlove.profileservice.model.Profile;
import com.meowlove.profileservice.model.ProfileRoleEnum;
import com.meowlove.profileservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    /// создание профиля (для сервиса аунтетификации)
    @Transactional
    public CreateProfileResponseDTO createProfile(CreateProfileRequestDTO createProfileRequestDTO) {

        if (!isEmailUnique(createProfileRequestDTO.getEmail())) {
            throw new EmailNotUniqueException("Email already exists");
        }

        if (!isUsernameUnique(createProfileRequestDTO.getUsername())) {
            throw new UsernameNotUniqueException("The username is already in use");
        }

        Profile profile = new Profile();

        profile.setEmail(createProfileRequestDTO.getEmail());
        profile.setPassword(createProfileRequestDTO.getPassword());
        profile.setUsername(createProfileRequestDTO.getUsername());
        profile.setNickname("User " + createProfileRequestDTO.getUsername());
        profile.setAge(createProfileRequestDTO.getAge());
        profile.setRole(ProfileRoleEnum.USER);

        profile = profileRepository.save(profile);

        CreateProfileResponseDTO createProfileResponseDTO = new CreateProfileResponseDTO();

        createProfileResponseDTO.setProfileId(profile.getProfileId());
        createProfileResponseDTO.setRole(profile.getRole());

        return createProfileResponseDTO;
    }

    // редактирование пароля (для сервиса аутентификации)
    @Transactional
    public UpdateProfilePasswordResponseDTO updateProfilePassword(UUID profileId,
                                                  UpdateProfilePasswordRequestDTO updateProfilePasswordRequestDTO) {
        Profile profile = getProfile(profileId);

        if (!profile.getPassword().equals(updateProfilePasswordRequestDTO.getOldPassword())) {
            throw new IncorrectPasswordException("Old password doesn't match");
        }

        profile.setPassword(updateProfilePasswordRequestDTO.getNewPassword());
        return modelMapper.map(profileRepository.save(profile), UpdateProfilePasswordResponseDTO.class);
    }

    // получение профиля для страницы профиля
    public GetProfileOverviewResponseDTO getProfileOverview(UUID profileId) {
        Profile profile = profileRepository.findProfileByProfileId(profileId).orElseThrow(
                () -> new ProfileNotFoundException("Profile with id " + profileId.toString() + " not found")
        );
        return modelMapper.map(profile, GetProfileOverviewResponseDTO.class);
    }

    // редактирование профиля (без credentials)
    @Transactional
    public UpdateProfileResponseDTO updateProfile(UUID profileId,
                                                  UpdateProfileRequestDTO updateProfileRequestDTO) {
        Profile profile = getProfile(profileId);

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

    // проферка уникальности емаила (для создания профиля)
    private Boolean isEmailUnique(String email) {
        return !profileRepository.existsProfileByEmail(email);
    }

}
