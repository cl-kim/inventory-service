package com.project.inventoryservice.api.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InBoundUpdateRequestDto {

    @Schema(description = "상품 id", required = true)
    private Long productId;

    @Schema(description = "입고일", required = true)
    private LocalDate inStockDate;

    @Schema(description = "수량", required = true)
    private Integer quantity;

    @Schema(description = "비고")
    private String memo;

    @Builder
    public InBoundUpdateRequestDto(Long productId, LocalDate inStockDate, Integer quantity, String memo) {
        this.productId = productId;
        this.inStockDate = inStockDate;
        this.quantity = quantity;
        this.memo = memo;
    }
}
