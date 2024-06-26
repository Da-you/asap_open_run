package com.asap.openrun.doamin.user.domain;

import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdateNicknameRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdatePasswordRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdatePhoneNumberRequest;
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
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends UserBase {

  @Column(name = "nickname", nullable = false, unique = true)
  private String nickname;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Builder
  private User(Long id,String name, String asapName, String password, String nickname,
      String phoneNumber) {
    super(id,name, asapName, password, Role.USER);
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;

  }

  public static User of(SignUpRequest request) {
    return User.builder()
        .name(request.getName())
        .asapName(request.getAsapName())
        .password(request.getPassword())
        .nickname(request.getNickname())
        .phoneNumber(request.getPhoneNumber())
        .build();
  }

  public void updateNickname(UpdateNicknameRequest request) {
    this.nickname = request.getNickname();
  }

  public void updatePhoneNumber(UpdatePhoneNumberRequest request) {
    this.phoneNumber = request.getPhoneNumber();
  }

  public void updatePassword(UpdatePasswordRequest request) {
    this.password = request.getAfterPassword();
  }
}