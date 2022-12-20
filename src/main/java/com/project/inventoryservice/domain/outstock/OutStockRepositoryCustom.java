package com.project.inventoryservice.domain.outstock;

import com.project.inventoryservice.api.outstock.dto.OutStockResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OutStockRepositoryCustom {
    Page<OutStockResponseDto> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate);
}
