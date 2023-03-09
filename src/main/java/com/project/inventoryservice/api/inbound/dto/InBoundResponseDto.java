package com.project.inventoryservice.api.inbound.dto;

import com.project.inventoryservice.domain.inventory.Inventory;
import com.project.inventoryservice.domain.product.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InBoundResponseDto {

    private Long id;

    private String categoryName;

    private Long productId;

    private String productCode;

    private String productName;

    private LocalDate date;

    private Integer quantity;

    private String memo;

    @Builder
    public InBoundResponseDto(Long id, String categoryCode, Long productId, String productCode, String productName,
                              LocalDate date, Integer quantity, String memo) {
        this.id = id;
        this.categoryName = Category.findByKey(categoryCode).getName();
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.date = date;
        this.quantity = quantity;
        this.memo = memo;
    }

    @Builder
    public InBoundResponseDto(Inventory entity) {
        this.id = entity.getId();
        this.categoryName = Category.findByKey(entity.getProduct().getCategoryCode()).getName();
        this.productId = entity.getProduct().getId();
        this.productCode = entity.getProduct().getProductCode();
        this.productName = entity.getProduct().getProductName();
        this.date = entity.getDate();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
