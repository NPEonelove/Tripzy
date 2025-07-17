package com.meowlove.profileservice.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO containing updated profile information in response")
public class UpdateProfileResponseDTO {

    @Schema(
            description = "Unique identifier of the updated profile",
            example = "123e4567-e89b-12d3-a456-426614174000",
            format = "uuid"
    )
    private UUID profileId;

    @Schema(
            description = "Unique identifier of the user who owns the profile",
            example = "123e4567-e89b-12d3-a456-426614174000",
            format = "uuid"
    )
    private UUID userId;

    @Schema(
            description = "Updated username of the profile",
            example = "new_username123",
            minLength = 5,
            maxLength = 32
    )
    private String username;

    @Schema(
            description = "Updated display nickname",
            example = "Cool Nickname",
            minLength = 1,
            maxLength = 64
    )
    private String nickname;

    @Schema(
            description = "Updated age of the user",
            example = "26",
            minimum = "14",
            maximum = "150"
    )
    private Integer age;

    @Schema(
            description = "URL to the updated profile photo",
            example = "https://storage.meowlove.com/profiles/123e4567/avatar.jpg",
            nullable = true
    )
    private String photoLink;
}