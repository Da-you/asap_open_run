package com.asap.openrun.doamin.product.controller;

import com.asap.openrun.doamin.product.dto.request.ProductRequest.*;
import com.asap.openrun.doamin.product.service.ProductService;
import com.asap.openrun.global.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "상품 API",
    description = "상품 관련 api controller 입니다."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductApiController {

  private final ProductService productService;

  @Operation(
      summary = "상품 등록 API",
      description = "입력 정보로는 시리얼넘버, 상품명,가격,재고, 행사 주소, 상품 내용, 행사 일정을 입력 받습니다. "
  )
  @PostMapping("/register")
  public void register(@LoginUser String asapName, @RequestPart ProductRegisterRequest request,
      @RequestPart(required = false) MultipartFile file) {
    productService.registerProduct(asapName, request, file);
  }

  @Operation(
      summary = "상품명 변경 API",
      description = "시리얼넘버로 상품을 조회하여 입력받은 상품명으로 변경합니다. "
  )
  @PatchMapping("/name/{serialNumber}")
  public void updateProductName(@LoginUser String asapName, @PathVariable String serialNumber,
      @RequestBody
      UpdateProductName request) {
    productService.updateProductName(asapName, serialNumber, request);
  }

  @Operation(
      summary = "상품 가격 변경 API",
      description = "시리얼넘버로 상품을 조회하여 입력받은 갸격으로 상품 가격을 변경합니다. "
  )
  @PatchMapping("/price/{serialNumber}")
  public void updateProductPrice(@LoginUser String asapName, @PathVariable String serialNumber,
      @RequestBody
      UpdateProductPrice request) {
    productService.updateProductPrice(asapName, serialNumber, request);
  }

  @Operation(
      summary = "상품 재고 변경 API",
      description = "시리얼넘버로 상품을 조회하여 입력받은 재고 수량을 기존 재고에 추가합니다. "
  )
  @PatchMapping("/stock/{serialNumber}")
  public void updateProductStock(@LoginUser String asapName, @PathVariable String serialNumber,
      @RequestBody
      UpdateProductStock request) {
    productService.updateProductStock(asapName, serialNumber, request);
  }

  @Operation(
      summary = "상품 이미지 변경 API",
      description = "시리얼넘버로 상품을 조회하여 입력받은 이미지의 url로 상품의 이미지를 변경 합니다. "
  )
  @PatchMapping("/image/{serialNumber}")
  public void updateProductImage(@LoginUser String asapName, @PathVariable String serialNumber,
      @RequestPart UpdateProductImage request,
      @RequestPart(required = false) MultipartFile productImage) {
    productService.updateProductImage(asapName, serialNumber, request, productImage);
  }


}
