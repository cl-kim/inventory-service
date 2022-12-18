package com.project.inventoryservice.api.product.dto;

import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
public class ProductSaveRequestDto {

    @NotNull
    private Category category;

    @NotBlank
    private String productName;

    private String productUnit;

    public Product toEntity(){
        return Product.builder()
                .category(category)
                .productName(productName)
                .productUnit(productUnit)
                .build();
    }

}
