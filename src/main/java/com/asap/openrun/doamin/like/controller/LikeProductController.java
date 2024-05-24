package com.asap.openrun.doamin.like.controller;

import com.asap.openrun.doamin.like.service.LikeService;
import com.asap.openrun.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/likes")
@RequiredArgsConstructor
public class LikeProductController {

  private final LikeService likeService;


  @PostMapping("/{serialNumber}")
  public void doLike(@LoginUser String asapName, @PathVariable String serialNumber) {
    likeService.doLike(asapName, serialNumber);
  }


  @DeleteMapping("/{serialNumber}")
  public void disLike(@LoginUser String asapName, @PathVariable String serialNumber) {
    likeService.disLike(asapName, serialNumber);
  }
}
