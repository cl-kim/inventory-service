package com.project.inventoryservice.domain.product;

import com.project.inventoryservice.api.monthly.dto.MonthlyResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findProduct(String categoryCode, Pageable pageable);

    List<Product> findInventory(LocalDate startDate, LocalDate endDate);

    List<MonthlyResponseDto> findInBound(LocalDate startDate, LocalDate endDate);
}