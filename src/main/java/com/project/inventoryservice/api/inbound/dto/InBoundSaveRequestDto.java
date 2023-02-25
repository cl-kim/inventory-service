package com.project.inventoryservice.api.inbound.dto;

import com.project.inventoryservice.domain.inbound.InBound;
import com.project.inventoryservice.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InBoundSaveRequestDto {

    @Schema(description = "상품 id", required = true)
    private Long productId;
    @Schema(description = "입고일", required = true)
    private LocalDate inBoundDate;
    @Schema(description = "수량", required = true)
    private Integer quantity;
    @Schema(description = "비고")
    private String memo;

    @Builder
    public InBoundSaveRequestDto(Long productId, LocalDate inBoundDate, Integer quantity, String memo) {
        this.productId = productId;
        this.inBoundDate = inBoundDate;
        this.quantity = quantity;
        this.memo = memo;
    }

    public InBound toEntity(Product product) {
        return InBound.builder()
                .product(product)
                .inBoundDate(inBoundDate)
                .quantity(quantity)
                .memo(memo)
                .build();
    }
}
