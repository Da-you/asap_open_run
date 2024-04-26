package com.asap.openrun.doamin.brand.dto.request;

import com.asap.openrun.global.utils.encryption.EncoderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BrandRequest {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class BrandCreateRequest {

    private String name;
    private String asapName;
    private String password;
    private String brandName;
    private String businessNumber;

    public void passwordEncoded(EncoderService encryptionService) {
      this.password = encryptionService.encoded(asapName, password);
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class BrandLoginRequest {

    private String asapName;
    private String password;

    public void passwordEncoded(EncoderService encryptionService) {
      this.password = encryptionService.encoded(asapName, password);
    }
  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateBrandNameRequest {

    private String brandName;

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
