package com.project.inventoryservice.api.outstock.dto;

import com.project.inventoryservice.domain.outstock.OutStock;
import com.project.inventoryservice.domain.product.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockResponseDto {
    private Long id;

    private String categoryName;

    private Long productId;

    private String productCode;

    private String productName;

    private LocalDate outStockDate;

    private String customer;

    private Integer price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutStockResponseDto(OutStock entity) {
        this.id = entity.getId();
        this.categoryName = Category.findByKey(entity.getProduct().getCategoryCode()).getName();
        this.productId =  entity.getProduct().getId();
        this.productCode = entity.getProduct().getProductCode();
        this.productName = entity.getProduct().getProductName();
        this.outStockDate = entity.getOutStockDate();
        this.customer = entity.getCustomer();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
