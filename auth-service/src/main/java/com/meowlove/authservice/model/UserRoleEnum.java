package com.meowlove.authservice.model;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    UserRoleEnum(String value) {
        this.value = value;
    }
}
