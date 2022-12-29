package com.project.inventoryservice.api.product.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ProductUpdateRequestDto {
    @Schema(description = "카테고리명", required = true)
    private String categoryName;
    @Schema(description = "상품명", required = true)
    private String productName;
    @Schema(description = "상품 단위")
    private String productUnit;
    @Schema(description = "상품 용량")
    private Float amount;
    @Schema(description = "상품가격", required = true)
    private Integer price;
    @Schema(description = "판매상태", required = true)
    private String productStatus;
    @Schema(description = "메모")
    private String memo;

    @Builder
    public ProductUpdateRequestDto(String categoryName, String productName, String productUnit, Float amount, Integer price, String productStatus, String memo) {
        this.categoryName = categoryName;
        this.productName = productName;
        this.productUnit = productUnit;
        this.amount = amount;
        this.price = price;
        this.productStatus = productStatus;
        this.memo = memo;
    }
}
