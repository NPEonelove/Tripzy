package com.meowlove.profileservice.exception.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProfileErrorResponse {
    int statusCode;
    String error;
    String message;
    LocalDateTime timestamp;
}
