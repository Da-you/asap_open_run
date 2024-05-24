package com.asap.openrun.doamin.like.repository;

import com.asap.openrun.doamin.like.domain.LikeProduct;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {

  boolean existsByUserAndProduct(User user, Product product);

  LikeProduct findByUserAndProduct(User user, Product product);
}
