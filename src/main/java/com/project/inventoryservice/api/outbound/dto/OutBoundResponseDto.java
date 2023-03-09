package com.project.inventoryservice.api.outbound.dto;

import com.project.inventoryservice.domain.inventory.Inventory;
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
    public OutBoundResponseDto(Long id, String categoryCode, Long productId, String productCode, String productName,
                               LocalDate outBoundDate, String customer, Integer price, Integer quantity, String memo) {
        this.id = id;
        this.categoryName = Category.findByKey(categoryCode).getName();
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.outBoundDate = outBoundDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity * -1;
        this.memo = memo;
    }

    @Builder
    public OutBoundResponseDto(Inventory entity) {
        this.id = entity.getId();
        this.categoryName = Category.findByKey(entity.getProduct().getCategoryCode()).getName();
        this.productId =  entity.getProduct().getId();
        this.productCode = entity.getProduct().getProductCode();
        this.productName = entity.getProduct().getProductName();
        this.outBoundDate = entity.getDate();
        this.customer = entity.getCustomer();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity()  * -1;
        this.memo = entity.getMemo();
    }
}
