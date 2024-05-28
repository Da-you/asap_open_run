package com.asap.openrun.doamin.user.service;

import com.asap.openrun.doamin.product.RedisRepository;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

  private final ProductRepository productRepository;
  private final RedisRepository redisRepository;
  private final RedisTemplate<String, Integer> redisTemplate;


  public void openProduct() {
    List<Product> products = productRepository.findAll();
    for (Product product : products) {
      redisRepository.saveProductStockToRedis(product);
      log.info("{}번 공연이 예매가능으로 수정되었습니다.", product.getSerialNumber());
    }
    productRepository.saveAll(products);
  }
}
