package com.asap.openrun.doamin.product.repository;

import com.asap.openrun.doamin.brand.domain.Brand;
import com.asap.openrun.doamin.product.domain.Product;
import jakarta.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Product findBySerialNumber(String serialNumber);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Product p where p.serialNumber = :serialNumber")
  Product findBySerialNumberWithPessimisticLock(String serialNumber);

  boolean existsBySerialNumber(String serialNumber);

  List<Product> findAllByBrand(Brand brand);

  Optional<Product> findByBrandAndSerialNumber(Brand brand, String serialNumber);

  @Query("SELECT p from Product p where DATE(p.eventStartDate) = CURRENT_DATE")
  List<Product> findAllByEventStartDate();

  @Query("SELECT p from Product p where DATE(p.eventEndDate) = CURRENT_DATE")
  List<Product> findAllByEventEndDate();
}
