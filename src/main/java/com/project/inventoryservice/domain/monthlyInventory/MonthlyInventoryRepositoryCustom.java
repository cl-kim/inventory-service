package com.project.inventoryservice.domain.monthlyInventory;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.domain.product.Product;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyInventoryRepositoryCustom {

    Integer findLastMonthStock(Product product, LocalDate date);

    List<StockResponseDto> findMonthlyInventory(LocalDate month, String categoryCode);

}
