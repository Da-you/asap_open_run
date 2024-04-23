package com.asap.openrun.doamin.product.repository;

import com.asap.openrun.doamin.product.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Product findBySerialNumber(String serialNumber);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Product p where p.serialNumber = :serialNumber")
  Product findBySerialNumberWithPessimisticLock(String serialNumber);

  boolean existsBySerialNumber(String serialNumber);
}
