package com.asap.openrun.global.common.error;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // server Error
  SERVER_ERROR(INTERNAL_SERVER_ERROR,50001,"unknown error")
  ;

  private final HttpStatus status;
  private final Integer code;
  private final String message;
}
