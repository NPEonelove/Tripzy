package com.meowlove.profileservice.dto.profile;

import com.meowlove.profileservice.model.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateProfileResponseDTO {

    private UUID profileId;
    private ProfileRoleEnum role;

}
