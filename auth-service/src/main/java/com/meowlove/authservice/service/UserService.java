package com.meowlove.authservice.service;

import com.meowlove.authservice.dto.user.GetJwtUserClaimsResponseDTO;
import com.meowlove.authservice.exception.UserNotFoundException;
import com.meowlove.authservice.model.User;
import com.meowlove.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // получение айди и роли для генерации jwt токенов
    public GetJwtUserClaimsResponseDTO getJwtUserClaims(UUID userId) {
        return modelMapper.map(getUserByUUID(userId), GetJwtUserClaimsResponseDTO.class);
    }

    // получение юзера по UUID
    public User getUserByUUID(UUID userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId.toString() + " not found"));
    }
}
