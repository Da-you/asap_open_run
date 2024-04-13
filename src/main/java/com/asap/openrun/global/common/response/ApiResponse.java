package com.asap.openrun.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

  private final boolean status;
  private final T data;


  public static <T> ApiResponse ok(T data) {
    return new ApiResponse<>(true, data);
  }


}
