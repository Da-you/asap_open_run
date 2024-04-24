package com.asap.openrun.doamin.brand.domain;

import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandCreateRequest;
import com.asap.openrun.doamin.user.model.Role;
import com.asap.openrun.doamin.user.model.UserBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends UserBase {

  @Column(nullable = false)
  private String brandName;

  @Column(nullable = false)
  private String businessNumber;

  private boolean isAuth;

  @Builder
  private Brand(String name, String asapName, String password, String brandName,
      String businessNumber,
      boolean isAuth) {
    super(name, asapName, password, Role.BRAND);
    this.brandName = brandName;
    this.businessNumber = businessNumber;
    this.isAuth = isAuth;
  }

  @Builder
  public static Brand of(BrandCreateRequest request) {
    return Brand.builder()
        .name(request.getName())
        .asapName(request.getAsapName())
        .password(request.getPassword())
        .brandName(request.getBrandName())
        .businessNumber(request.getBusinessNumber())
        .isAuth(false)
        .build();

  }
}
