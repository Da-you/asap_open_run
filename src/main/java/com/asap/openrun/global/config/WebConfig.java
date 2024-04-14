package com.asap.openrun.global.config;

import com.asap.openrun.global.annotation.LoginUserArgumentResolver;
import com.asap.openrun.global.common.interceptor.LogCheckInterceptor;
import com.asap.openrun.global.common.interceptor.LoginCheckInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginUserArgumentResolver());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogCheckInterceptor())
        .order(1)
        .addPathPatterns("/**")//모든 요청 /**에 대해 적용
        .excludePathPatterns("/error");//제외할 요청
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns("/*/users/sign-up", "/*/users/login");
  }
}
