package com.asap.openrun.doamin.brand.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BrandResponse {

  @Builder
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class BrandProductResponse {

    private String serialNumber;
    private String productName;
    private Integer price;
    private String thumbNail;
    private Integer remainingStock;

  }

}
