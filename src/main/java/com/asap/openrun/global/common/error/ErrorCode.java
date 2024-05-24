package com.asap.openrun.global.common.error;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 401
  EVENT_IS_NOT_OPEN(BAD_REQUEST, 40001, "this event is not open"),
  PRODUCT_IS_SOLD_OUT(BAD_REQUEST, 40002, "product is sold out"),
  // 403
  DUPLICATE_ASAP_NAME(CONFLICT, 40301, "duplicate asap name"),
  DUPLICATE_NICKNAME(CONFLICT, 40302, "duplicate nickname"),
  DUPLICATE_PHONE_NUMBER(CONFLICT, 40303, "duplicate phone number"),
  SERIALNUMBER_ALREADY_EXISTS(CONFLICT, 40304, "serial number is already exists"),
  LIKE_ALREADY_EXISTS(CONFLICT,40305 ,"this product is already like"),
  // 404
  USER_NOT_FOUND(NOT_FOUND, 40401, "user not found"),
  PRODUCT_IS_NOT_FOUND(NOT_FOUND, 40402, "product is not found"),
  HISTORY_IS_NOT_FOUND(NOT_FOUND, 40403, "history is not found"),
  NOT_FOUNT_RESOURCE(NOT_FOUND,40404 ,"not found this resource"),
  // server Error
  SERVER_ERROR(INTERNAL_SERVER_ERROR, 50001, "unknown error"),
  // 401
  UNAUTHORIZED_BRAND(UNAUTHORIZED, 40101, "unauthorized brand"),
  ;

  private final HttpStatus status;
  private final Integer code;
  private final String message;
}
