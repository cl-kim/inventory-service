package com.project.inventoryservice.api.outstock.dto;

import com.project.inventoryservice.domain.outstock.OutStock;
import com.project.inventoryservice.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockSaveRequestDto {
    @NotNull
    private Long productId;
    @NotNull
    private LocalDate outStockDate;

    private String customer;

    private Long price;
    @NotNull
    private Integer quantity;

    private String memo;

    public OutStock toEntity(){
        return OutStock.builder()
                .product(Product.builder().id(productId)
                        .build())
                .outStockDate(outStockDate)
                .customer(customer)
                .price(price)
                .quantity(quantity)
                .memo(memo)
                .build();
    }

}
