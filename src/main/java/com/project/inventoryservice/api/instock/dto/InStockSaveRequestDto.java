package com.project.inventoryservice.api.instock.dto;

import com.project.inventoryservice.domain.instock.InStock;
import com.project.inventoryservice.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InStockSaveRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    private LocalDate inStockDate;
    @NotNull
    private Integer quantity;

    private String memo;

    public InStockSaveRequestDto(Long productId, LocalDate inStockDate, Integer quantity, String memo) {
        this.productId = productId;
        this.inStockDate = inStockDate;
        this.quantity = quantity;
        this.memo = memo;
    }

    public InStock toEntity() {
        return InStock.builder()
                .product(Product.builder()
                        .id(productId)
                        .build())
                .inStockDate(inStockDate)
                .quantity(quantity)
                .memo(memo)
                .build();
    }
}
