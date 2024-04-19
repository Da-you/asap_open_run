package com.asap.openrun.doamin.brand.service;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandCreateRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandLoginRequest;
import com.asap.openrun.doamin.brand.repository.BrandRepository;
import com.asap.openrun.doamin.user.dto.request.UserRequest.LoginRequest;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import com.asap.openrun.global.utils.encryption.EncoderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepo;
  private final EncoderService encoder;
  private final HttpSession session;

  @Transactional
  public void signUpBrand(BrandCreateRequest request) {
    existsByAsapName(request.getAsapName());
    existsByBrandName(request.getAsapName());
    existsByBusinessNumber(request.getBusinessNumber());
    request.passwordEncoded(encoder);
    brandRepo.save(Brand.of(request));
  }

  private void existsByAsapName(String asapName) {
    if (brandRepo.existsByAsapName(asapName)) {
      throw new BusinessException(ErrorCode.DUPLICATE_ASAP_NAME);
    }
  }

  private void existsByBrandName(String brandName) {
    if (brandRepo.existsByBrandName(brandName)) {
      throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
    }
  }

  private void existsByBusinessNumber(String businessNumber) {
    if (brandRepo.existsByBusinessNumber(businessNumber)) {
      throw new BusinessException(ErrorCode.DUPLICATE_PHONE_NUMBER);
    }
  }


  @Transactional
  public void loginBrand(BrandLoginRequest request) {
    existsByAsapNameAndPassword(request);
    Brand brand = brandRepo.findByAsapName(request.getAsapName())
        .orElseThrow(() -> new BusinessException(
            ErrorCode.USER_NOT_FOUND));
    session.setAttribute("SESSION_ID", request.getAsapName());
    session.setAttribute("ROLE", brand.getRole());
  }

  private void existsByAsapNameAndPassword(BrandLoginRequest request) {
    request.passwordEncoded(encoder);
    if (!brandRepo.existsByAsapNameAndPassword(request.getAsapName(), request.getPassword())) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
  }

  @Transactional
  public void logout() {
    if (session.getAttribute("SESSION_ID") != null) {
      session.invalidate();
    } else {
      log.info("로그인 정보가 없습니다.");
    }
  }
}
