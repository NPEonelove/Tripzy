package com.meowlove.profileservice.dto.profile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDTO {

    @NotBlank(message = "Enter a username")
    @Size(min = 5, max = 32, message = "The length of the username should be from 5 to 32 characters")
    private String username;

    @NotBlank(message = "Enter a nickname")
    @Size(min = 1, max = 32, message = "he length of the nickname should be from 1 to 32 characters")
    private String nickname;

    @NotBlank(message = "Enter an age")
    @Min(value = 14, message = "You must be at least 14 years old")
    @Max(value = 150, message = "You must be at least 150 years old")
    private Integer age;

}
