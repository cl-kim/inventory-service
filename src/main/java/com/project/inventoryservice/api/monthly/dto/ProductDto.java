package com.project.inventoryservice.api.monthly.dto;

import com.project.inventoryservice.domain.inventory.Inventory;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProductDto {

    private Integer month;

    private Integer quantity;

    @QueryProjection
    public ProductDto(Integer month, Integer quantity) {
        this.month = month;
        this.quantity = (quantity < 0) ? quantity * -1 : quantity;
    }

    public ProductDto(MonthlyInventory entity){
        this.month = entity.getMonthlyDate().getYear()*100 + entity.getMonthlyDate().getMonthValue();
        this.quantity = entity.getQuantity();
    }

    public ProductDto(Inventory entity){
        this.month = entity.getDate().getYear()*100 + entity.getDate().getMonthValue();
        this.quantity = entity.getQuantity();
    }
}
