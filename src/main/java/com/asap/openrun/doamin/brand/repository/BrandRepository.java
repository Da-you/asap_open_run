package com.asap.openrun.doamin.brand.repository;

import com.asap.openrun.doamin.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  Brand findByAsapName(String asapName);
}
