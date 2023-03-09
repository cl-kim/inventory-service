package com.project.inventoryservice.domain.inventory;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDate date;

    private Integer price;

    private String customer;

    private String memo;

    @Builder
    public Inventory(Product product, Integer quantity, LocalDate date, Integer price, String customer, String memo) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.memo = memo;
    }

    public Inventory updateOutBound(Product product, Integer quantity, LocalDate date, Integer price, String customer, String memo) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.memo = memo;
        return this;
    }

    public Inventory updateInBound(Product product, Integer quantity, LocalDate date, String memo) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.memo = memo;
        return this;
    }

}
