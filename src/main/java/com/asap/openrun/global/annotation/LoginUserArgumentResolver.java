package com.asap.openrun.global.annotation;

import com.asap.openrun.doamin.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final UserService userService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
    boolean hasMemberType = String.class.isAssignableFrom(parameter.getParameterType());
    return hasLoginAnnotation && hasMemberType;
//    return  parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // HttpServletRequest

//    HttpSession session = request.getSession(false);
//
//    if (session == null) {
//      return null;
//    }
    return userService.getLoginUser();
  }
}
