package com.asap.openrun.doamin.user.repository;

import com.asap.openrun.doamin.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByAsapName(String asapName);

  boolean existsByAsapNameAndPassword(String asapName, String password);


  boolean existsByAsapName(String asapName);

  boolean existsByNickname(String nickname);

  boolean existsByPhoneNumber(String phoneNumber);
}
