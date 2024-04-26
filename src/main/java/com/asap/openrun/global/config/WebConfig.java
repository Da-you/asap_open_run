package com.asap.openrun.global.config;

import com.asap.openrun.doamin.user.service.UserService;
import com.asap.openrun.global.annotation.LoginUserArgumentResolver;
import com.asap.openrun.global.common.interceptor.LogCheckInterceptor;
import com.asap.openrun.global.common.interceptor.LoginCheckInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final LoginUserArgumentResolver loginUserArgumentResolver;
  private final LogCheckInterceptor logCheckInterceptor;
  private final LoginCheckInterceptor loginCheckInterceptor;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logCheckInterceptor)
        .order(1)
        .addPathPatterns("/**")//모든 요청 /**에 대해 적용
        .excludePathPatterns("/css/**", "/*.ico", "/error");//제외할 요청
    registry.addInterceptor(loginCheckInterceptor)
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns("/*/*/sign-up", "/*/*/login", "/swagger-ui/**", "/css/**",
            "/*.ico", "/error");
  }
}
