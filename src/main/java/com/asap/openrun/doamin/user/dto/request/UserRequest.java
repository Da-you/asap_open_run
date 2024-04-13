package com.asap.openrun.doamin.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class SignUpRequest {

    private String name;
    private String asapName;
    private String password;
    private String nickname;
    private String phoneNumber;


  }

}
