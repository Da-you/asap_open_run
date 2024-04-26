package com.asap.openrun.doamin.brand.controller;

import com.asap.openrun.doamin.brand.dto.request.BrandRequest.BrandCreateRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.BrandLoginRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.UpdateBrandNameRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.UpdatePasswordRequest;
import com.asap.openrun.doamin.brand.dto.response.BrandResponse.BrandProductResponse;
import com.asap.openrun.doamin.brand.service.BrandService;
import com.asap.openrun.global.annotation.LoginUser;
import com.asap.openrun.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "브랜드 API",
    description = "브랜드 관련 API 컨트롤러 입니다."
)
@RestController
@RequestMapping("/v1/brands")
@RequiredArgsConstructor
public class BrandApiController {

  private final BrandService brandService;

  @Operation(
      summary = "브랜드 사용자 회원가입 API",
      description = "서비스에서 사용할 서비스ID, 브랜드명 ,사업자 번호, 사용자 이름, 비밀번호를 입력 받습니다."
  )
  @PostMapping("/sign-up")
  public void signUp(@RequestBody BrandCreateRequest request) {
    brandService.signUpBrand(request);

  }

  @Operation(
      summary = "로그인 API",
      description = "입력 정보로는 서비스ID(asapName), 패스워드를 입력받으며,"
          + " 세션ID(asapID)와 ROLE정보를 세션에 저장합니다. "
  )
  @PostMapping("/login")
  public void login(@RequestBody BrandLoginRequest request) {
    brandService.loginBrand(request);
  }

  @Operation(
      summary = "로그아웃 API",
      description = "ArgumentResolver를 통해 @LoginUser이 붙은 String 타입의 파라미터로 "
          + "접속한 사용자의 세션정보를 받으며, 사용자의 세션정보가 있다면 해당 세션정보를 삭제합니다. "
  )
  @DeleteMapping("/logout")
  public void logout(@LoginUser String asapName) {
    brandService.logout();
  }

  @Operation(
      summary = "브랜드 사용자가 등록한 상품을 리스트로 조회 API",
      description = "로그인한 브랜드 사용자의 등록한 상품의 시리얼번호, 상품명, 가격, 썸네일 이미지, 남은재고를 리스트로 보여줍니다. "
  )
  @GetMapping("/product-list")
  public ApiResponse<List<BrandProductResponse>> getProducts(@LoginUser String asapName) {
    return ApiResponse.ok(brandService.getProducts(asapName));
  }

  @Operation(
      summary = "브랜드 사용자의 브랜드명 변경 API",
      description = "변경할 브랜드명을 입력받아 브랜드명을 변경합니다.."
  )
  @PatchMapping("/name")
  public void updateBrandName(@LoginUser String asapName,
      @RequestBody UpdateBrandNameRequest request) {

  }

  @Operation(
      summary = "사용자 비밀번호 변경 API",
      description = "변경할 비밀번호와 로그인 입력정보를 입력받아 사용자의 비밀번호를 변경합니다."
  )
  @PatchMapping("/password")
  public void updatePassword(@LoginUser String asapName,
      @RequestBody UpdatePasswordRequest request) {

  }

}
