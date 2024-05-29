package com.asap.openrun.doamin.product;

import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.doamin.user.repository.UserHistoryRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

  private final RedisTemplate<String, Integer> redisTemplate;
  private final ProductRepository productRepository;
  private final UserHistoryRepository userHistoryRepository;
  private static final String DECREASE_STOCK =
      "local leftStock = tonumber(redis.call('get', KEYS[1]))" +
          "if leftStock - ARGV[1] >= 0 then" +
          "  redis.call('decrby', KEYS[1], ARGV[1]) " +
          "  return true " +
          "else " +
          "  return false " +
          "end";
  private final RedisScript<Boolean> decreaseStockScript = new DefaultRedisScript<>(DECREASE_STOCK,
      Boolean.class);

  public void saveProductStockToRedis(Product product) {
    String key = "ls" + product.getSerialNumber();
    if (redisTemplate.hasKey(key)) {
      throw new BusinessException(ErrorCode.EXISTED_CACHE);
    }
    redisTemplate.opsForValue().set(key, product.getStock());
  }

  // 매분 캐시 변경분을 db에 저장
  @Scheduled(cron = "0 * * * * *")
  public void saveProductStockFromRedis() {
    Set<String> keys = redisTemplate.keys("ls*");
    if (keys.isEmpty() || keys == null) {
      return;
    }
    for (String key : keys) {
      Integer leftStock = redisTemplate.opsForValue().get(key);
      String serialNumber = key.substring(2);
      Product product = productRepository.findBySerialNumber(serialNumber);
      product.setStock(leftStock);
      productRepository.save(product);
    }
  }

  public Boolean hasStockInRedis(String serialNumber) {
    String key = "ls" + serialNumber;
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean decreaseStock(String serialNumber, int count) {
    String key = "ls" + serialNumber;
    return redisTemplate.execute(decreaseStockScript, Collections.singletonList(key), count);
  }

  // 티켓 취소시
  public void incrementStock(String serialNumber, int count) {
    String key = "ls" + serialNumber;
    redisTemplate.opsForValue().increment(key, count);
  }

  public void deleteStockInRedis(Product product) {
    String key = "ls" + product.getSerialNumber();
    if (!redisTemplate.hasKey(key)) {
      throw new BusinessException(ErrorCode.EXISTED_CACHE);
    }
    saveProductStockFromRedis();
    redisTemplate.delete(key);
    refreshProduct(product);
  }

  public void refreshProduct(Product product) {
    Integer accurateStock = userHistoryRepository.sumCountBySerialNumber(product.getSerialNumber());
    if (accurateStock == null) {
      accurateStock = 0;
    }
    Integer accurateLeftStock = product.getStock() - accurateStock;
    product.setStock(accurateLeftStock);
    if (hasStockInRedis(product.getSerialNumber())) {
      String key = "ls" + product.getSerialNumber();
      redisTemplate.opsForValue().set(key, accurateLeftStock);
    }
  }
}
