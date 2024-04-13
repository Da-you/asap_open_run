package com.asap.openrun.doamin.user.controller;

import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;

  @PostMapping("/sign-up")
  public void signUpUser(@RequestBody SignUpRequest request) {
    userService.signUp(request);

  }

}
