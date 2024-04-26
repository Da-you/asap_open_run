package com.asap.openrun.doamin.user.dto.request;

import com.asap.openrun.global.utils.encryption.EncoderService;
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

    public void passwordEncoded(EncoderService encryptionService) {
      this.password = encryptionService.encoded(asapName, password);
    }

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class LoginRequest {

    private String asapName;
    private String password;


    public void passwordEncoded(EncoderService encryptionService) {
      this.password = encryptionService.encoded(asapName, password);
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateNicknameRequest {

    private String nickname;

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdatePhoneNumberRequest {

    private String phoneNumber;

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdatePasswordRequest {

    private String asapName;

    private String beforePassword;
    private String afterPassword;

    public void passwordEncoded(EncoderService encryptionService) {
      this.beforePassword = encryptionService.encoded(asapName, beforePassword);
      this.afterPassword = encryptionService.encoded(asapName, afterPassword);
    }
  }

}
