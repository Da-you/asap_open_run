package com.asap.openrun.doamin.brand.controller;

import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandCreateRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandLoginRequest;
import com.asap.openrun.doamin.brand.service.BrandService;
import com.asap.openrun.global.annotation.LoginUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Tag(name = "브랜드 API", description = "브랜드 관련 API 컨트롤러 입니다.")
@RestController
@RequestMapping("/v1/brands")
@RequiredArgsConstructor
public class BrandApiController {

  private final BrandService brandService;

  @PostMapping("/sign-up")
  public void signUp(@RequestBody BrandCreateRequest request) {
    brandService.signUpBrand(request);

  }

  @PostMapping("/login")
  public void login(@RequestBody BrandLoginRequest request) {
    brandService.loginBrand(request);
  }

  @DeleteMapping("/logout")
  public void logout(@LoginUser String asapName){
    brandService.logout();
  }

}
