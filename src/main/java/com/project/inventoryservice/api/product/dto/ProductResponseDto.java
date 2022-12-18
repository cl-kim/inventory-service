package com.project.inventoryservice.api.product.dto;

import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    private Category category;

    private String productName;

    private String productUnit;

    @Builder
    public ProductResponseDto(Product entity){
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.productName = entity.getProductName();
        this.productUnit = entity.getProductUnit();
    }
}
