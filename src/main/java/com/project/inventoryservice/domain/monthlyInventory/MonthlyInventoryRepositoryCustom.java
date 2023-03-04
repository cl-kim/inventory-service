package com.project.inventoryservice.domain.monthlyInventory;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyInventoryRepositoryCustom {

    List<StockResponseDto> findLastStock(LocalDate lastMonth);

}
