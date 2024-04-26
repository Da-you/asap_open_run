package com.asap.openrun.doamin.user.domain;

import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.global.common.response.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLRestriction(value = "is_deleted = true")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProductHistory extends BaseTimeEntity {

  @Id
  @Column(name = "history_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;


  private String ticketNumber;

  private boolean isDeleted = false;

  private boolean isReceived = false;

  private UserProductHistory(User user, Product product) {
    this.user = user;
    this.product = product;
  }

  public static UserProductHistory from(User user, Product product) {
    UserProductHistory history = new UserProductHistory();
    history.user = user;
    history.product = product;
    return history;
  }

  public void softDelete() {
    this.isDeleted = true;
  }

  public void setIsReceive() {
    if (this.isReceived) {
      throw new IllegalArgumentException("이미 수령한 상품 입니다.");
    }
    this.isReceived = true;
  }

}
