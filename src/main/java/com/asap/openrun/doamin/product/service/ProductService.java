package com.asap.openrun.doamin.product.service;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.brand.repository.BrandRepository;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.dto.request.ProductDto.ProductRegisterRequest;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepo;
  private final BrandRepository brandRepo;

  @Transactional
  public void registerProduct(String asapName, ProductRegisterRequest request) {
    Brand brand = brandRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    // TODO : 임시 주석 처리(어드민 유저 생성떄까지 잠시 보류)
//    checkAuthorization(brand);
    existsBySerialNumber(request.getSerialNumber());

    productRepo.save(Product.of(brand, request));
  }

  private void existsBySerialNumber(String serialNumber) {
    if (productRepo.existsBySerialNumber(serialNumber)) {
      throw new BusinessException(ErrorCode.SERIALNUMBER_ALREADY_EXISTS);
    }
  }

  private static void checkAuthorization(Brand brand) {
    if (!brand.isAuth()) {
      throw new BusinessException(ErrorCode.UNAUTHORIZED_BRAND);
    }
  }

}
