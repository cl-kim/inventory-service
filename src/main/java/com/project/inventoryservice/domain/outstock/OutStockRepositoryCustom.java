package com.project.inventoryservice.domain.outstock;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OutStockRepositoryCustom {
    List<OutStock> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate);
}
