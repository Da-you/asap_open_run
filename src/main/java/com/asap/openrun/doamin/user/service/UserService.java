package com.asap.openrun.doamin.user.service;

import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.dto.request.UserRequest.LoginRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.repository.UserRepository;
import com.asap.openrun.global.utils.encryption.EncoderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final HttpSession session;
  private final UserRepository userRepo;
  private final EncoderService encoder;

  @Transactional
  public void signUp(SignUpRequest request) {
    request.passwordEncoded(encoder);
    userRepo.save(User.of(request));
  }

  @Transactional
  public void login(LoginRequest request) {
    request.passwordEncoded(encoder);
    User user = userRepo.findByAsapName(request.getAsapName());
    session.setAttribute("SESSION_ID", request.getAsapName());
    session.setAttribute("ROLE", user.getRole());
  }

  @Transactional
  public void logout() {
    if (session.getAttribute("SESSION_ID") == null) {
      log.info("로그인 정보가 없습니다.");
    } else {
      log.info("로그아웃 요청자 = {}", session.getAttribute("SESSION_ID"));
      session.invalidate();
    }
  }

  public String getLoginUser() {
    return (String) session.getAttribute("SESSION_ID");
  }
}
