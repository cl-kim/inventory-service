package com.project.inventoryservice.domain.product;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findProduct(String categoryCodecategery, Pageable pageable);
}