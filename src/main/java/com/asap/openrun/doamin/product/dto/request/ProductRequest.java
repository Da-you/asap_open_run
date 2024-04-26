package com.asap.openrun.doamin.product.dto.request;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequest {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductRegisterRequest {

    private String serialNumber;
    private String productName;
    private Integer price;
    private Integer stock;
    private String address;
    private String content;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;


  }
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateProductName {

    private String productName;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateProductPrice {

    private Integer price;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateProductStock {

    private Integer additionalStock;
  }

}
