package com.asap.openrun.doamin.user.model;

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

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class UserBase {

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
    this.role = role;
  }
}
