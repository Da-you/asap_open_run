package com.asap.openrun.doamin.user.repository;

import com.asap.openrun.doamin.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
