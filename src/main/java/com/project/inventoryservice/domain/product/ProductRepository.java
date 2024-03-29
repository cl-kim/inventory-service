package com.project.inventoryservice.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Long countByCategoryCode(String categoryCode);

    Product findByProductCode(String productCode);
}
