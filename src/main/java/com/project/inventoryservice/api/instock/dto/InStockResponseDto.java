package com.project.inventoryservice.api.instock.dto;

import com.project.inventoryservice.domain.instock.InStock;
import com.project.inventoryservice.domain.product.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InStockResponseDto {

    private Long id;

    private String categoryName;

    private Long productId;

    private String productCode;

    private String productName;

    private LocalDate inStockDate;

    private Integer quantity;

    private String memo;

    @Builder
    public InStockResponseDto(Long id, String categoryName, Long productId, String productCode, String productName,
                              LocalDate inStockDate, Integer quantity, String memo) {
        this.id = id;
        this.categoryName = categoryName;
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.inStockDate = inStockDate;
        this.quantity = quantity;
        this.memo = memo;
    }

    @Builder
    public InStockResponseDto(InStock entity) {
        this.id = entity.getId();
        this.categoryName = Category.findByKey(entity.getProduct().getCategoryCode()).getName();
        this.productId = entity.getProduct().getId();
        this.productCode = entity.getProduct().getProductCode();
        this.productName = entity.getProduct().getProductName();
        this.inStockDate = entity.getInStockDate();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
