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


@Tag(name = "일반 사용자 API",
    description = "일반 사용자 api controller 입니다."
)
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;
  private final UserTicketService userTicketService;


  @Operation(
      summary = "회원가입 API",
      description = "입력 정보로는 사용자 이름, 로그인시 사용할 서비스ID, 패스워드 "
          + "서비스에서 이용할 닉네임, 핸드폰 번호를 입력 받습니다. "
  )
  @PostMapping("/sign-up")
  public void signUpUser(@RequestBody SignUpRequest request) {
    userService.signUp(request);

  }

  @Operation(
      summary = "로그인 API",
      description = "입력 정보로는 서비스ID(asapName), 패스워드를 입력받으며,"
          + " 세션ID(asapID)와 ROLE정보를 세션에 저장합니다. "
  )
  @PostMapping("/login")
  public void login(@RequestBody LoginRequest request) {
    userService.login(request);
  }

  @Operation(
      summary = "로그아웃 API",
      description = "ArgumentResolver를 통해 @LoginUser이 붙은 String 타입의 파라미터로 "
          + "접속한 사용자의 세션정보를 받으며, 사용자의 세션정보가 있다면 해당 세션정보를 삭제합니다. "
  )
  @DeleteMapping("/logout")
  public void logout(@LoginUser String asapName) {
    userService.logout();
  }

  @Operation(
      summary = "티켓팅 API",
      description = "특정 상품의 serialNumber을 PathVariable로 이용해 상품을 찾은 후 티켓팅을 합니다."
          + "제약조건으로는 상품의 존재 유무확인, 티켓팅 오픈일정, 재고 확인을 거쳐 진행을 합니다."
  )
  @PostMapping("/apply/{serialNumber}")
  public void createTicketing(@LoginUser String asapName, @PathVariable String serialNumber) {
    userTicketService.createTicketing(asapName, serialNumber);
  }

  @Operation(
      summary = "티켓팅 내역 리스트 조회 API",
      description = "로그인한 유저가 티켓팅한 내역을 확인 가능한 API입니다. "
          + "응답으로는 예매내역ID, 상품을 등록한 브랜드 이름, 상품 이름, 판매가 open된 상품인지, 행사 일정을 리스트로 보여줍니다."
  )
  @GetMapping("/histories")
  public ApiResponse<List<UserTicket>> getHistories(@LoginUser String asapName) {
    return ApiResponse.ok(userTicketService.getHistories(asapName));
  }

  @Operation(
      summary = "티켓팅 내역 단건 조회 API",
      description = "로그인한 유저가 티켓팅한 내역을 확인 가능한 API입니다. "
          + "응답으로는 예매내역ID, 상품을 등록한 브랜드 이름, 상품 이름, 판매가 open된 상품인지, 행사 일정을 보여줍니다."
  )
  @GetMapping("/histories/{historyId}")
  public ApiResponse<UserTicket> getHistory(@LoginUser String asapName,
      @PathVariable Long historyId) {
    return ApiResponse.ok(userTicketService.getHistory(asapName, historyId));
  }

  @Operation(
      summary = "티켓팅 내역 단건 취소 API",
      description = "예매 내역 ID를 경로로 받아 티켓팅을 취소합니다."
  )
  @DeleteMapping("/histories/{historyId}")
  public void cancelHistory(@LoginUser String asapName,
      @PathVariable Long historyId) {
    userTicketService.cancelTicket(asapName, historyId);
  }

  @Operation(
      summary = "티켓팅 상품 수령 API",
      description = "예매 내역 ID를 경로로 받아 티켓팅한 상품을 수령 상태로 변경합니다."
  )
  @PatchMapping("/histories/{historyId}")
  public void receiveProduct(@LoginUser String asapName,
      @PathVariable Long historyId) {
    userTicketService.receiveProduct(asapName, historyId);
  }

  @Operation(
      summary = "사용자 마이페이지 조회 API",
      description = "로그인한 사용자가 자신의 마이페이지를 조회합니다. 응답값으로는 이름,서비스ID, 닉네임, 핸드폰 번호이 존재합니다."
  )
  @GetMapping("/my-page")
  public ApiResponse<UserMyPageResponse> getUserMyPage(@LoginUser String asapName) {
    return ApiResponse.ok(userService.getMyPage(asapName));

  }

  @Operation(
      summary = "사용자 닉네임 변경 API",
      description = "변경할 닉네임을 입력받아 사용자의 닉네임을 변경합니다."
  )
  @PatchMapping("/nickname")
  public void updateNickname(@LoginUser String asapName,
      @RequestBody UpdateNicknameRequest request) {
    userService.updateNickname(asapName, request);
  }

  @Operation(
      summary = "사용자 핸드폰 번호 변경 API",
      description = "변경할 핸드폰 번호를 입력받아 사용자의 핸드폰 번호를 변경합니다."
  )
  @PatchMapping("/phone-number")
  public void changePhoneNumber(@LoginUser String asapName,
      @RequestBody UpdatePhoneNumberRequest request) {
    userService.updatePhoneNumber(asapName, request);
  }

  @Operation(
      summary = "사용자 비밀번호 변경 API",
      description = "변경할 비밀번호와 로그인 입력정보를 입력받아 사용자의 비밀번호를 변경합니다."
  )
  @PatchMapping("/password")
  public void updatedPassword(@LoginUser String asapName,
      @RequestBody UpdatePasswordRequest request) {
    userService.updatedPassword(asapName, request);
  }
}
