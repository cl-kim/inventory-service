package com.project.inventoryservice.domain.instock;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface InStockRepositoryCustom {
    List<InStock> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate);
}
