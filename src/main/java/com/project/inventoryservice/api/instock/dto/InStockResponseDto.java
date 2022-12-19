package com.project.inventoryservice.api.instock.dto;

import com.project.inventoryservice.domain.instock.InStock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InStockResponseDto {

    private Long id;

    private Long productId;

    private LocalDate inStockDate;

    private Integer quantity;

    private String memo;

    @Builder
    public InStockResponseDto(InStock entity) {
        this.id = entity.getId();
        this.productId = entity.getProduct().getId();
        this.inStockDate = entity.getInStockDate();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
