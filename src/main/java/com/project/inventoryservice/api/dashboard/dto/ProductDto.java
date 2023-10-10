package com.project.inventoryservice.api.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {

    private Integer days;

    private Integer quantity;

    public ProductDto(Integer days, Integer quantity) {
        this.days = days;
        if(quantity != null){
            this.quantity = quantity < 0 ? quantity * -1 : quantity;
        } else {
            this.quantity = 0;
        }
    }
}
