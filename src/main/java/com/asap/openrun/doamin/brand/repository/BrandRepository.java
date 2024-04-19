package com.asap.openrun.doamin.brand.repository;

import com.asap.openrun.doamin.brand.domain.Brand;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  Optional<Brand> findByAsapName(String asapName);

  boolean existsByAsapName(String asapName);

  boolean existsByBrandName(String brandName);

  boolean existsByBusinessNumber(String businessNumber);

  boolean existsByAsapNameAndPassword(String asapName, String password);
}
