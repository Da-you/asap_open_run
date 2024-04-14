package com.asap.openrun.doamin.user.controller;

import com.asap.openrun.doamin.user.dto.request.UserRequest.LoginRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.service.UserService;
import com.asap.openrun.global.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "/v1/users")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;


  @Operation(
      summary = "회원가입을 하는 API",
      description = "회원가입을 하는 API로 입력 정보로는 사용자 이름, 로그인시 사용할 서비스ID, 패스워드 "
          + "서비스에서 이용할 닉네임, 핸드폰 번호를 입력 받습니다. "
  )
  @PostMapping("/sign-up")
  public void signUpUser(@RequestBody SignUpRequest request) {
    userService.signUp(request);

  }

  @PostMapping("/login")
  public void login(@RequestBody LoginRequest request) {
    userService.login(request);
  }

  @DeleteMapping("/logout")
  public void logout(@LoginUser Long userId){
    userService.logout();
  }



}
