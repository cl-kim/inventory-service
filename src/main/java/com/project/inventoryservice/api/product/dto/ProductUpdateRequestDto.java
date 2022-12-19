package com.project.inventoryservice.api.product.dto;

import com.project.inventoryservice.domain.product.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductUpdateRequestDto {

    @NotNull
    private Category category;

    @NotBlank
    private String productName;

    private String productUnit;

}
