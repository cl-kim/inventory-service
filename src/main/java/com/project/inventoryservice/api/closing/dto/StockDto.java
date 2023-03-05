package com.project.inventoryservice.api.closing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockDto {

    private Long productId;

    private String month;

    private Integer quantity;

    public StockDto(Long productId, String month, Integer quantity) {
        this.productId = productId;
        this.month = month;
        this.quantity = quantity;
    }
}
