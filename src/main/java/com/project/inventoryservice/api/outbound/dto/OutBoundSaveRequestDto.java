package com.project.inventoryservice.api.outbound.dto;

import com.project.inventoryservice.domain.outbound.OutBound;
import com.project.inventoryservice.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutBoundSaveRequestDto {

    @Schema(description = "상품 id", required = true)
    private Long productId;
    @Schema(description = "출고일", required = true)
    private LocalDate outBoundDate;
    @Schema(description = "거래처")
    private String customer;
    @Schema(description = "출고가격", required = true)
    private Integer price;
    @Schema(description = "수량", required = true)
    private Integer quantity;
    @Schema(description = "비고")
    private String memo;

    @Builder
    public OutBoundSaveRequestDto(Long productId, LocalDate outBoundDate, String customer, Integer price, Integer quantity, String memo) {
        this.productId = productId;
        this.outBoundDate = outBoundDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;
    }

    public OutBound toEntity(Product product){
        return OutBound.builder()
                .product(product)
                .outBoundDate(outBoundDate)
                .customer(customer)
                .price(price)
                .quantity(quantity)
                .memo(memo)
                .build();
    }

}
