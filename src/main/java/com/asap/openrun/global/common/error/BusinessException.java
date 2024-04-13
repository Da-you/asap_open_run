package com.asap.openrun.global.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
  private final ErrorCode errorCode;


}
