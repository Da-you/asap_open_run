package com.asap.openrun.doamin.brand.service;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandCreateRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandDto.BrandLoginRequest;
import com.asap.openrun.doamin.brand.repository.BrandRepository;
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
    request.passwordEncoded(encoder);
    brandRepo.save(Brand.of(request));
  }

  @Transactional
  public void loginBrand(BrandLoginRequest request) {

    request.passwordEncoded(encoder);
    Brand brand = brandRepo.findByAsapName(request.getAsapName());
    session.setAttribute("SESSION_ID", request.getAsapName());
    session.setAttribute("ROLE", brand.getRole());
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
