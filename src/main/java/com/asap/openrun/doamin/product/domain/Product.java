package com.asap.openrun.doamin.product.domain;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.product.dto.request.ProductRequest.ProductRegisterRequest;
import com.asap.openrun.global.common.response.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "brand_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Brand brand;

  @Column(nullable = false, unique = true)
  private String serialNumber;

  @Column(nullable = false, name = "product_name")
  private String productName;

  private String thumbnailUrl;
  private String resizedUrl;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Integer stock;

  private Integer leftStock;

  @Column(nullable = false)
  private LocalDateTime eventStartDate;

  @Column(nullable = false)
  private LocalDateTime eventEndDate;

  private boolean isOpen = false;

  @Builder
  private Product(Brand brand, String serialNumber, String productName, Integer price,
      Integer stock, String thumbnailUrl, String resizedUrl,
      LocalDateTime eventStartDate, LocalDateTime eventEndDate) {
    this.brand = brand;
    this.serialNumber = serialNumber;
    this.productName = productName;
    this.thumbnailUrl = thumbnailUrl;
    this.resizedUrl = resizedUrl;
    this.price = price;
    this.stock = stock;
    this.leftStock = stock;
    this.eventStartDate = eventStartDate;
    this.eventEndDate = eventEndDate;
  }

  @Builder
  public static Product of(Brand brand, ProductRegisterRequest request) {
    return Product.builder()
        .brand(brand)
        .serialNumber(request.getSerialNumber())
        .productName(request.getProductName())
        .thumbnailUrl(request.getThumbnailUrl())
        .price(request.getPrice())
        .stock(request.getStock())
        .eventStartDate(request.getEventStartDate())
        .eventEndDate(request.getEventEndDate())
        .build();
  }


  public void decrease() {
    if (this.leftStock == 0) {
      throw new IllegalArgumentException("매진 된 상품 입니다.");
    }
    this.leftStock--;
  }
  public void  increase(){
    this.leftStock++;
  }

  public void updateProductName(String productName) {
    this.productName = productName;
  }

  public void updateProductPrice(Integer price) {
    this.price = price;
  }

  public void updateStock(Integer additionalStock
  ) {
    this.stock += additionalStock;
    this.leftStock += additionalStock;
  }

  public void setStock(Integer leftStock) {
    this.stock = leftStock;
  }

  public void eventOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }
}
