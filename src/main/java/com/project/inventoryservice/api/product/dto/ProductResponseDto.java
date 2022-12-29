package com.project.inventoryservice.api.product.dto;

import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;

    private String categoryCode;

    private String categoryName;

    private String productCode;

    private String productName;

    private String productUnit;

    private Float amount;

    private Integer price;

    private String productStatus;

    private String memo;

    public ProductResponseDto(Product entity){
        this.id = entity.getId();
        this.categoryCode = entity.getCategoryCode();
        this.categoryName = Category.findByKey(entity.getCategoryCode()).getName();
        this.productCode = entity.getProductCode();
        this.productName = entity.getProductName();
        this.productUnit = entity.getProductUnit();
        this.amount = entity.getAmount();
        this.price = entity.getPrice();
        this.productStatus = entity.getProductStatus();
        this.memo = entity.getMemo();
    }
}
