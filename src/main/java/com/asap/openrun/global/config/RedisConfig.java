package com.asap.openrun.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

  private static final String REDISSON_HOST_PREFIX = "redis://";

  @Bean
  public RedissonClient redissonClient() {
    Config config =  new Config();
      config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + "localhost:6379");
      return Redisson.create(config);
  }
}
