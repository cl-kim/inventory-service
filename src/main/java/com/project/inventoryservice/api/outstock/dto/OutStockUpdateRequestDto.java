package com.project.inventoryservice.api.outstock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockUpdateRequestDto {

    @Schema(description = "상품 id", required = true)
    private Long productId;
    @Schema(description = "출고일", required = true)
    private LocalDate outStockDate;
    @Schema(description = "거래처")
    private String customer;
    @Schema(description = "출고가격", required = true)
    private Integer price;
    @Schema(description = "수량", required = true)
    private Integer quantity;
    @Schema(description = "비고")
    private String memo;

    @Builder
    public OutStockUpdateRequestDto(Long productId, LocalDate outStockDate, String customer, Integer price, Integer quantity, String memo) {
        this.productId = productId;
        this.outStockDate = outStockDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;
    }
}
