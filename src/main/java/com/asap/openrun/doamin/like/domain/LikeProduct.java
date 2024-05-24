package com.asap.openrun.doamin.like.domain;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.global.common.response.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeProduct extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @Builder
  public LikeProduct(User user, Product product) {
    this.user = user;
    this.product = product;
  }

  public static LikeProduct of(User user, Product product) {
    return LikeProduct.builder()
        .user(user)
        .product(product)
        .build();
  }
}
