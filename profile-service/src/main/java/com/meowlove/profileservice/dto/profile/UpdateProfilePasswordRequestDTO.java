package com.meowlove.profileservice.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfilePasswordRequestDTO {

    private String oldPassword;
    private String newPassword;

}
