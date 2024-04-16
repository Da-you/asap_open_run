package com.asap.openrun.doamin.user.controller;

import com.asap.openrun.doamin.user.dto.request.UserRequest.*;
import com.asap.openrun.doamin.user.dto.response.UserResponse.*;
import com.asap.openrun.doamin.user.service.UserService;
import com.asap.openrun.doamin.user.service.UserTicketService;
import com.asap.openrun.global.annotation.LoginUser;
import com.asap.openrun.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "/v1/users")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;
  private final UserTicketService userTicketService;


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
  public void logout(@LoginUser String asapName) {
    userService.logout();
  }

  @PostMapping("/apply/{serialNumber}")
  public void createTicketing(@LoginUser String asapName, @PathVariable String serialNumber) {
    userTicketService.createTicketing(asapName, serialNumber);
  }

  @GetMapping("/histories")
  public ApiResponse<List<UserTicket>> getHistories(@LoginUser String asapName) {
    return ApiResponse.ok(userTicketService.getHistories(asapName));
  }

  @GetMapping("/histories/{historyId}")
  public ApiResponse<UserTicket> getHistory(@LoginUser String asapName,
      @PathVariable Long historyId) {
    return ApiResponse.ok(userTicketService.getHistory(asapName, historyId));
  }

  @DeleteMapping("/histories/{historyId}")
  public void cancelHistory(@LoginUser String asapName,
      @PathVariable Long historyId) {
    userTicketService.cancelTicket(asapName, historyId);
  }

  @PatchMapping("/histories/{historyId}")
  public void receiveProduct(@LoginUser String asapName,
      @PathVariable Long historyId) {
    userTicketService.receiveProduct(asapName, historyId);
  }

}
