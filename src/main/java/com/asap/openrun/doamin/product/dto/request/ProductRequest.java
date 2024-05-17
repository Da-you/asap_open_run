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
    private String originImageUrl;
    private String thumbnailUrl;
    private String resizedUrl;
    private Integer price;
    private Integer stock;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;

    public void setImageUrl(String thumbnailUrl, String resizedUrl) {
      this.thumbnailUrl = thumbnailUrl;
      this.resizedUrl = resizedUrl;
    }


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

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateProductImage {

    private String thumbnailUrl;
    private String resizedUrl;

    public void setImageUrl(String thumbnailUrl, String resizedUrl) {
      this.thumbnailUrl = thumbnailUrl;
      this.resizedUrl = resizedUrl;
    }

    public void deleteImageUrl() {
      setImageUrl(null, null);
    }
  }

}
