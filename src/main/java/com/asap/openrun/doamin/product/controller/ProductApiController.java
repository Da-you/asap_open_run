package com.asap.openrun.doamin.product.controller;

import com.asap.openrun.doamin.product.dto.request.ProductDto.ProductRegisterRequest;
import com.asap.openrun.doamin.product.service.ProductService;
import com.asap.openrun.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductApiController {

  private final ProductService productService;

  @PostMapping("/register")
  public void register(@LoginUser String asapName , @RequestBody ProductRegisterRequest request) {
    productService.registerProduct(asapName, request);
  }



}
