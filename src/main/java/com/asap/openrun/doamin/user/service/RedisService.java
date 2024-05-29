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


  @Scheduled(cron = "0 0 0 * * ?")
  public void closeProduct() {
    List<Product> products = productRepository.findAllByEventEndDate();
    for (Product product : products) {
      product.eventOpen(false);
      redisRepository.deleteStockInRedis(product);
      log.info("{}번 상품 판매 일정이 {}로 수정되었습니다.", product.getSerialNumber(), product.isOpen());
    }
    productRepository.saveAll(products);
  }

  @Scheduled(cron = "0 0 23 * * ?")
  public void openProduct() {
    List<Product> products = productRepository.findAllByEventStartDate();
    for (Product product : products) {
      product.eventOpen(true);
      redisRepository.saveProductStockToRedis(product);
      log.info("{}번 상품 판매 일정이 {}로 수정되었습니다.", product.getSerialNumber(), product.isOpen());
    }
    productRepository.saveAll(products);
  }
}
