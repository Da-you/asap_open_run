package com.asap.openrun.global.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LogCheckInterceptor implements HandlerInterceptor {

  private static final String LOG_ID = "logId";


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String requestURI = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    request.setAttribute(LOG_ID, uuid);

    //@RequestMapping: HandlerMethod
    //정적 리소스: ResourceHttpRequestHandler
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함되어 있음
    }
    log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
    return true;//false로 하면 다음 단계로 안 넘어감
  }


  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    Object logId = (String) request.getAttribute(LOG_ID);
    log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
    if (ex != null) {
      log.error("afterCompletion error!!", ex);
    }

  }
}
