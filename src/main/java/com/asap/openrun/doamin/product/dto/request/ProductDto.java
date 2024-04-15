package com.asap.openrun.doamin.product.dto.request;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {

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

}
