package com.asap.openrun.doamin.user.service;

import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.dto.request.UserRequest.SignUpRequest;
import com.asap.openrun.doamin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;

  @Transactional
  public void signUp(SignUpRequest request) {
    userRepo.save(User.of(request));
  }

}
