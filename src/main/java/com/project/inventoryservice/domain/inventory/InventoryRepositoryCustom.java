package com.project.inventoryservice.domain.inventory;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepositoryCustom {

    List<InBoundResponseDto> findInBoundPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate);

    List<OutBoundResponseDto> findOutBoundPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate);

    List<StockResponseDto> findTotalInventory(LocalDate date);
}