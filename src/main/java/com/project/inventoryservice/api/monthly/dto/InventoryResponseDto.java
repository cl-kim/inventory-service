package com.project.inventoryservice.api.monthly.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InventoryResponseDto {

    private Integer productId;

    private String productCode;

    private String productName;

    private Integer quantity;

    public InventoryResponseDto(Integer productId, String productCode, String productName, Integer quantity) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
    }
}
