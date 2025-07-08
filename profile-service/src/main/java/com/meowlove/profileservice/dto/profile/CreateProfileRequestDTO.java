package com.meowlove.profileservice.dto.profile;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfileRequestDTO {

    @NotBlank(message = "Enter a email")
    @Email(message = "Enter a correct email")
    @Size(min = 5, max = 128, message = "The length of the email should be from 5 to 128 characters")
    private String email;

    @NotBlank(message = "Enter a password")
    @Size(min = 6, max = 32, message = "The length of the password should be from 6 to 32 characters")
    private String password;

    @NotBlank(message = "Enter a username")
    @Size(min = 5, max = 32, message = "The length of the username should be from 5 to 32 characters.")
    private String username;

    @NotNull(message = "Enter an age")
    @Min(value = 14, message = "You must be at least 14 years old")
    @Max(value = 150, message = "You must be at least 150 years old")
    private Integer age;

}
