package com.asap.openrun.doamin.brand.dto.request;

import com.asap.openrun.global.utils.encryption.EncoderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BrandDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class BrandCreateRequest{
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
  public static class BrandLoginRequest{
    private String asapName;
    private String password;

    public void passwordEncoded(EncoderService encryptionService) {
      this.password = encryptionService.encoded(asapName, password);
    }
  }

}
