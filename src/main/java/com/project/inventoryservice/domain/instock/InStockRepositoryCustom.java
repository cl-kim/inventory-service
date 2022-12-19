package com.project.inventoryservice.domain.instock;

import com.project.inventoryservice.api.instock.dto.InStockResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InStockRepositoryCustom {
    Page<InStockResponseDto> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate);
}
