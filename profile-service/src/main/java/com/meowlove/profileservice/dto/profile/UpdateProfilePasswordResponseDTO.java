package com.meowlove.profileservice.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateProfilePasswordResponseDTO {

    private UUID profileId;
    private String password;

}
