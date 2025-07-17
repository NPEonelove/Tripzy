package com.meowlove.profileservice.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO containing response data after profile creation")
public class CreateProfileResponseDTO {

    @Schema(
            description = "Unique identifier of the created profile",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "uuid"
    )
    private UUID profileId;

    @Schema(
            description = "Unique identifier of the user who owns the profile",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "uuid"
    )
    private UUID userId;

    @Schema(
            description = "Username of the created profile",
            example = "john_doe"
    )
    private String username;

    @Schema(
            description = "Display name of the profile",
            example = "Johnny"
    )
    private String nickname;

    @Schema(
            description = "Age of the user",
            example = "25"
    )
    private Integer age;

    @Schema(
            description = "URL link to the profile photo",
            example = "https://example.com/profiles/550e8400-e29b-41d4-a716-446655440000/photo.jpg",
            nullable = true
    )
    private String photoLink;
}