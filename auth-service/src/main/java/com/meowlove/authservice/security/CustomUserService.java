package com.meowlove.authservice.security;

import com.meowlove.authservice.exception.UserNotFoundException;
import com.meowlove.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findUserByUserId(UUID.fromString(userId)).map(CustomUserDetails::new).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + " not found"));
    }
}
