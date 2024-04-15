package com.asap.openrun.doamin.user.model;

import com.asap.openrun.global.common.response.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 사용자 구분을 일반 사용자, 브랜드 사용자, 관리자 사용자로 나눌 예정으로 공통 정보를 따로 빼두고 상속관계 매핑중 Join 전략을 사용
@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class UserBase extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "asap_name", nullable = false, unique = true)
  private String asapName;

  @Column(name = "password", nullable = false)
  private String password;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  protected UserBase(String name, String asapName, String password, Role role) {
    this.name = name;
    this.asapName = asapName;
    this.password = password;
    this.role =role;
  }

}
