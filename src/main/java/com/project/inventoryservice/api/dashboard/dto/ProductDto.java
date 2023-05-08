package com.project.inventoryservice.api.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {

    private Integer month;

    private Integer quantity;

    public ProductDto(Integer month, Integer quantity) {
        this.month = month;
        this.quantity = quantity < 0 ? quantity * -1 : quantity;
    }
}
