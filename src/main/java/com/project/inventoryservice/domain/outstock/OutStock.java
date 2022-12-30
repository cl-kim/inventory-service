package com.project.inventoryservice.domain.outstock;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class OutStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate outStockDate;

    private String customer;

    private Integer price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutStock(Product product, LocalDate outStockDate, String customer,
                    Integer price, Integer quantity, String memo) {
        this.product = product;
        this.outStockDate = outStockDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;
    }

    public OutStock update(Product product, LocalDate outStockDate, String customer, Integer price, Integer quantity, String memo){
        this.product = product;
        this.outStockDate = outStockDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;

        return this;
    }
}
