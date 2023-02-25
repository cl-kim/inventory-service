package com.project.inventoryservice.api.monthly.dto;

import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MonthlyResponseDto {

    private String categoryName;

    private String productCode;

    private String productName;

    private List<ProductDto> monthlyQuantityList;

    public MonthlyResponseDto(Product entity){
        this.categoryName = Category.findByKey(entity.getCategoryCode()).getName();
        this.productCode = entity.getProductCode();
        this.productName = entity.getProductName();
        this.monthlyQuantityList = entity.getInBoundList().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @QueryProjection
    public MonthlyResponseDto(String categoryName, String productCode, String productName, List<ProductDto> monthlyQuantityList) {
        this.categoryName = categoryName;
        this.productCode = productCode;
        this.productName = productName;
        this.monthlyQuantityList = monthlyQuantityList;
    }
}
