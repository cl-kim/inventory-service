package com.project.inventoryservice.api.closing.dto;


import com.project.inventoryservice.domain.product.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockResponseDto {

    private String categoryName;
    private String productCode;
    private String productName;
    private Integer quantity;

    @Builder
    public StockResponseDto(String categoryCode, String productCode, String productName, Integer quantity) {
        this.categoryName = Category.findByKey(categoryCode).getName();
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
    }
}
