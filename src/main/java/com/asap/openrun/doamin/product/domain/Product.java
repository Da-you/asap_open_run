package com.asap.openrun.doamin.product.domain;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.product.dto.request.ProductDto.ProductRegisterRequest;
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

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Integer stock;

  private Integer salesStock;


  @Column(nullable = false)
  private String address;

  private String content;

  @Column(nullable = false)
  private LocalDateTime eventStartDate;

  @Column(nullable = false)
  private LocalDateTime eventEndDate;

  private boolean isOpen = false;

  @Builder
  private Product(Brand brand, String serialNumber, String productName, Integer price,
      Integer stock, Integer salesStock,
      String address, String content, LocalDateTime eventStartDate, LocalDateTime eventEndDate) {
    this.brand = brand;
    this.serialNumber = serialNumber;
    this.productName = productName;
    this.price = price;
    this.stock = stock;
    this.salesStock = 0;
    this.address = address;
    this.content = content;
    this.eventStartDate = eventStartDate;
    this.eventEndDate = eventEndDate;
  }

  @Builder
  public static Product of(Brand brand, ProductRegisterRequest request) {
    return Product.builder()
        .brand(brand)
        .serialNumber(request.getSerialNumber())
        .productName(request.getProductName())
        .price(request.getPrice())
        .stock(request.getStock())
        .address(request.getAddress())
        .content(request.getContent())
        .eventStartDate(request.getEventStartDate())
        .eventEndDate(request.getEventEndDate())
        .build();
  }


  public void decrease() {
    if (this.stock == 0) {
      throw new IllegalArgumentException("매진 된 상품 입니다.");
    }
    this.stock--;
    this.salesStock++;
  }

}
