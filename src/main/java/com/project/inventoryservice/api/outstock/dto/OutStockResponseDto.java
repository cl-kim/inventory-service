package com.project.inventoryservice.api.outstock.dto;

import com.project.inventoryservice.domain.outstock.OutStock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockResponseDto {
    private Long id;

    private Long productId;

    private LocalDate outStockDate;

    private String customer;

    private Long price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutStockResponseDto(OutStock entity) {
        this.id = entity.getId();
        this.productId =  entity.getProduct().getId();
        this.outStockDate = entity.getOutStockDate();
        this.customer = entity.getCustomer();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
