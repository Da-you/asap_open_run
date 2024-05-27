package com.asap.openrun.doamin.brand.service;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.BrandCreateRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.BrandLoginRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.UpdateBrandNameRequest;
import com.asap.openrun.doamin.brand.dto.request.BrandRequest.UpdatePasswordRequest;
import com.asap.openrun.doamin.brand.dto.response.BrandResponse.BrandProductResponse;
import com.asap.openrun.doamin.brand.repository.BrandRepository;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.doamin.user.model.Role;
import com.asap.openrun.global.annotation.LoginUser;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import com.asap.openrun.global.utils.encryption.EncoderService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepo;
  private final ProductRepository productRepo;
  private final EncoderService encoder;
  private final HttpSession session;

  @Transactional
  public void signUpBrand(BrandCreateRequest request) {
    existsByAsapName(request.getAsapName());
    existsByBrandName(request.getAsapName());
    existsByBusinessNumber(request.getBusinessNumber());
    request.passwordEncoded(encoder);
    Brand brand = Brand.of(request);
    brandRepo.save(brand);
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

    session.setAttribute("SESSION_ID", request.getAsapName());
    session.setAttribute("ROLE", Role.BRAND);
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

  // 로그인한 브랜든가 등록한 상품 리스트
  @Transactional(readOnly = true)
  public List<BrandProductResponse> getProducts(@LoginUser String asapName) {
    Brand brand = brandRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(
            ErrorCode.USER_NOT_FOUND));
    return productRepo.findAllByBrand(brand).stream()
        .map(product -> BrandProductResponse.builder()
            .serialNumber(product.getSerialNumber())
            .productName(product.getProductName())
            .price(product.getPrice())
            .remainingStock(product.getRemainingStock(product.getStock(), product.getSalesStock()))
            .build())
        .collect(Collectors.toList());
  }

  public void updateBrandName(String asapName, UpdateBrandNameRequest request) {
    Brand brand = brandRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    String beforeNickname = brand.getBrandName();
    if (beforeNickname.equals(request.getBrandName())) {
      throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
    }
    existsByBrandName(request.getBrandName());
    brand.updatedBrandName(request.getBrandName());
  }

  public void updatePassword(String asapName, UpdatePasswordRequest request) {
    Brand brand = brandRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    String beforePassword = request.getBeforePassword();
    String afterPassword = request.getAfterPassword();
    if (!brandRepo.existsByAsapNameAndPassword(request.getAsapName(), beforePassword)) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);  // 변경 필요
    }
    if (beforePassword.equals(afterPassword)) {
      throw new BusinessException(ErrorCode.DUPLICATE_ASAP_NAME); // 변경 필요
    }
    brand.updatePassword(request.getAfterPassword());
  }
  // TODO: 판매 정보 총 판매 수량 , 수익금 ,구매자의 정보(asapName, username)
}
