package com.asap.openrun.doamin.user.repository;

import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.domain.UserProductHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserHistoryRepository extends JpaRepository<UserProductHistory, Long> {

  List<UserProductHistory> findAllByUser(User user);
  UserProductHistory findByUserAndId(User user, Long id);

}
