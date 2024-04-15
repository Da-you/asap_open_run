package com.asap.openrun.doamin.product.service;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.brand.repository.BrandRepository;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.dto.request.ProductDto.ProductRegisterRequest;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepo;
  private final BrandRepository brandRepo;

  @Transactional
  public void registerProduct(Long brandId, ProductRegisterRequest request) {
    Brand brand = brandRepo.findById(brandId)
        .orElseThrow(() -> new BusinessException(ErrorCode.SERVER_ERROR));
    if (!brand.isAuth()) {
      throw new IllegalArgumentException("상품 등록 권한이 없습니다.");
    }
    productRepo.save(Product.of(brand, request));
  }

}