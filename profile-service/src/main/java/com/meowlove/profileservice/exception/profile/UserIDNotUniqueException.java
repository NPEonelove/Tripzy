package com.meowlove.profileservice.exception.profile;

public class UserIDNotUniqueException extends RuntimeException {
  public UserIDNotUniqueException(String message) {
    super(message);
  }
}
