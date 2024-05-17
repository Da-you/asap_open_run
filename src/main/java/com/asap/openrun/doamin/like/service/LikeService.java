package com.asap.openrun.doamin.like.service;

import com.asap.openrun.doamin.like.domain.LikeProduct;
import com.asap.openrun.doamin.like.repository.LikeProductRepository;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.repository.UserRepository;
import com.asap.openrun.global.annotation.LoginUser;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final LikeProductRepository likeProductRepo;
  private final UserRepository userRepo;
  private final ProductRepository productRepo;

  @Transactional
  public void doLike(String asapName, String serialNumber) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    Product product = productRepo.findBySerialNumberWithPessimisticLock(serialNumber);
    if (product == null) {
      throw new BusinessException(ErrorCode.PRODUCT_IS_NOT_FOUND);
    }
    if (likeProductRepo.existsByUserAndProduct(user, product)) {
      throw new BusinessException(ErrorCode.LIKE_ALREADY_EXISTS);
    }
    likeProductRepo.save(LikeProduct.of(user, product));
  }

  @Transactional
  public void disLike(String asapName, String serialNumber) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    Product product = productRepo.findBySerialNumberWithPessimisticLock(serialNumber);
    if (product == null) {
      throw new BusinessException(ErrorCode.PRODUCT_IS_NOT_FOUND);
    }
    if (!likeProductRepo.existsByUserAndProduct(user, product)) {
      throw new BusinessException(ErrorCode.NOT_FOUNT_RESOURCE);
    }
    LikeProduct target = likeProductRepo.findByUserAndProduct(user, product);
    likeProductRepo.delete(target);
  }
}
