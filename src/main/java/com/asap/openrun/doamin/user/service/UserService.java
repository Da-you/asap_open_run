package com.asap.openrun.doamin.user.service;

import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.dto.request.UserRequest.LoginRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdateNicknameRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdatePasswordRequest;
import com.asap.openrun.doamin.user.dto.request.UserRequest.UpdatePhoneNumberRequest;
import com.asap.openrun.doamin.user.dto.response.UserResponse.UserMyPageResponse;
import com.asap.openrun.doamin.user.model.Role;
import com.asap.openrun.doamin.user.repository.UserRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import com.asap.openrun.global.utils.encryption.EncoderService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.PushBuilder;
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
    existsByAsapName(request.getAsapName());
    existsByNickname(request.getNickname());
    existsByPhoneNumber(request.getPhoneNumber());
    request.passwordEncoded(encoder);
    userRepo.save(User.of(request));
  }

  private void existsByAsapName(String asapName) {
    if (userRepo.existsByAsapName(asapName)) {
      throw new BusinessException(ErrorCode.DUPLICATE_ASAP_NAME);
    }
  }

  private void existsByNickname(String nickname) {
    if (userRepo.existsByNickname(nickname)) {
      throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
    }
  }

  private void existsByPhoneNumber(String phoneNumber) {
    if (userRepo.existsByPhoneNumber(phoneNumber)) {
      throw new BusinessException(ErrorCode.DUPLICATE_PHONE_NUMBER);
    }
  }


  @Transactional
  public void login(LoginRequest request) {
    existsByAsapNameAndPassword(request);
    session.setAttribute("SESSION_ID", request.getAsapName());
    session.setAttribute("ROLE", Role.USER);
  }

  private void existsByAsapNameAndPassword(LoginRequest request) {
    request.passwordEncoded(encoder);
    if (!userRepo.existsByAsapNameAndPassword(request.getAsapName(), request.getPassword())) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
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

  @Transactional(readOnly = true)
  public UserMyPageResponse getMyPage(String asapName) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    return UserMyPageResponse.builder()
        .username(user.getName())
        .asapName(asapName)
        .nickname(user.getNickname())
        .phoneNumber(user.getPhoneNumber())
        .build();
  }

  @Transactional
  public void updateNickname(String asapName, UpdateNicknameRequest request) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    String beforeNickname = user.getNickname();
    if (beforeNickname.equals(request.getNickname())) {
      throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
    }
    existsByNickname(request.getNickname());
    user.updateNickname(request);
  }

  @Transactional
  public void updatePhoneNumber(String asapName, UpdatePhoneNumberRequest request) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    String beforePhoneNumber = user.getPhoneNumber();
    if (beforePhoneNumber.equals(request.getPhoneNumber())) {
      throw new BusinessException(ErrorCode.DUPLICATE_PHONE_NUMBER);
    }
    user.updatePhoneNumber(request);
  }

  @Transactional
  public void updatedPassword(String asapName, UpdatePasswordRequest request) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    String beforePassword = request.getBeforePassword();
    String afterPassword = request.getAfterPassword();
    if (!userRepo.existsByAsapNameAndPassword(request.getAsapName(), beforePassword)) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);  // 변경 필요
    }
    if (beforePassword.equals(afterPassword)) {
      throw new BusinessException(ErrorCode.DUPLICATE_ASAP_NAME); // 변경 필요
    }
    user.updatePassword(request);

  }


  public String getLoginUser() {
    return (String) session.getAttribute("SESSION_ID");
  }

}
