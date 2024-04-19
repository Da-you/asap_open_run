package com.asap.openrun.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

  private final boolean status;
  private final T data;


  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, data);
  }


}
