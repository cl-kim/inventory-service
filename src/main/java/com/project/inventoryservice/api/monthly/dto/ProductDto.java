package com.project.inventoryservice.api.monthly.dto;

import com.project.inventoryservice.domain.inbound.InBound;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {

    private String month;

    private Integer quantity;

    @QueryProjection
    public ProductDto(String month, Integer quantity) {
        this.month = month;
        this.quantity = quantity;
    }

    public ProductDto(MonthlyInventory entity){
        this.month = entity.getMonthlyDate().toString();
        this.quantity = entity.getQuantity();
    }

    public ProductDto(InBound entity){
        this.month = entity.getInBoundDate().toString();
        this.quantity = entity.getQuantity();
    }
}
