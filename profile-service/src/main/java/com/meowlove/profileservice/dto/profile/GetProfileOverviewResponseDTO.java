package com.meowlove.profileservice.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO containing basic profile information for overview")
public class GetProfileOverviewResponseDTO {

    @Schema(
            description = "Unique identifier of the profile",
            example = "123e4567-e89b-12d3-a456-426614174000",
            format = "uuid"
    )
    private UUID profileId;

    @Schema(
            description = "User's unique username",
            example = "johndoe123",
            minLength = 5,
            maxLength = 32
    )
    private String username;

    @Schema(
            description = "User's display nickname",
            example = "John",
            minLength = 1,
            maxLength = 64
    )
    private String nickname;

    @Schema(
            description = "User's age in years",
            example = "28",
            minimum = "14",
            maximum = "150"
    )
    private Integer age;

    @Schema(
            description = "URL to user's profile photo",
            example = "https://meowlove.com/profiles/123e4567-e89b-12d3-a456-426614174000/avatar.jpg",
            nullable = true
    )
    private String photoLink;
}