package com.asap.openrun.global.common.error;

import com.asap.openrun.global.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> businessException(BusinessException e) {
    log.error("BusinessException : " + e.getMessage());
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity
        .status(HttpStatus.valueOf(Integer.parseInt(errorCode.getCode().toString().substring(0, 3))))
        .body(new ErrorResponse(errorCode.getMessage()));
  }

}
