package com.project.inventoryservice.api.outbound.dto;

import com.project.inventoryservice.domain.outbound.OutBound;
import com.project.inventoryservice.domain.product.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutBoundResponseDto {
    private Long id;

    private String categoryName;

    private Long productId;

    private String productCode;

    private String productName;

    private LocalDate outBoundDate;

    private String customer;

    private Integer price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutBoundResponseDto(OutBound entity) {
        this.id = entity.getId();
        this.categoryName = Category.findByKey(entity.getProduct().getCategoryCode()).getName();
        this.productId =  entity.getProduct().getId();
        this.productCode = entity.getProduct().getProductCode();
        this.productName = entity.getProduct().getProductName();
        this.outBoundDate = entity.getOutBoundDate();
        this.customer = entity.getCustomer();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.memo = entity.getMemo();
    }
}
