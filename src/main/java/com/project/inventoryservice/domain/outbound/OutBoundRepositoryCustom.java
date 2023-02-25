package com.project.inventoryservice.domain.outbound;

import com.project.inventoryservice.api.monthly.dto.InventoryResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OutBoundRepositoryCustom {
    List<OutBound> findList(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate);

    List<InventoryResponseDto> findMonthStock(LocalDate date);
}
