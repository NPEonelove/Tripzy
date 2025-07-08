package com.meowlove.profileservice.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateProfileResponseDTO {

    private UUID profileId;
    private String username;
    private String nickname;
    private Integer age;
    private String photoLink;
    private LocalDateTime registrationDate;

}
