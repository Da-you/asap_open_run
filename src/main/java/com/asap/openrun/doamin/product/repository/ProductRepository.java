package com.asap.openrun.doamin.product.repository;

import com.asap.openrun.doamin.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Product findBySerialNumber(String serialNumber);

}
