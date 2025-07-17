package com.meowlove.profileservice.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating existing profile information")
public class UpdateProfileRequestDTO {

    @NotBlank(message = "Enter a username")
    @Size(min = 5, max = 32, message = "The length of the username should be from 5 to 32 characters.")
    @Schema(
            description = "New username for the profile",
            example = "updated_username",
            minLength = 5,
            maxLength = 32,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Enter a nickname")
    @Size(min = 1, max = 64, message = "The length of the nickname should be from 1 to 64 characters.")
    @Schema(
            description = "New display nickname for the profile",
            example = "Updated Nickname",
            minLength = 1,
            maxLength = 64,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nickname;

    @NotNull(message = "Enter an age")
    @Min(value = 14, message = "You must be at least 14 years old")
    @Max(value = 150, message = "You must be at least 150 years old")
    @Schema(
            description = "Updated age of the user",
            example = "25",
            minimum = "14",
            maximum = "150",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer age;
}