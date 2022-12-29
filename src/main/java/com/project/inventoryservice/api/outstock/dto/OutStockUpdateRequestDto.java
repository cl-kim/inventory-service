package com.project.inventoryservice.api.outstock.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockUpdateRequestDto {

    private LocalDate outStockDate;

    private String customer;

    private Integer price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutStockUpdateRequestDto(LocalDate outStockDate, String customer, Integer price, Integer quantity, String memo) {
        this.outStockDate = outStockDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;
    }
}
