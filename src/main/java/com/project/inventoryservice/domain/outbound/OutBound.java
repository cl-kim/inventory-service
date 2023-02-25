package com.project.inventoryservice.domain.outbound;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class OutBound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate outBoundDate;

    private String customer;

    private Integer price;

    private Integer quantity;

    private String memo;

    @Builder
    public OutBound(Product product, LocalDate outBoundDate, String customer,
                    Integer price, Integer quantity, String memo) {
        this.product = product;
        this.outBoundDate = outBoundDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;
    }

    public OutBound update(Product product, LocalDate outBoundDate, String customer, Integer price, Integer quantity, String memo){
        this.product = product;
        this.outBoundDate = outBoundDate;
        this.customer = customer;
        this.price = price;
        this.quantity = quantity;
        this.memo = memo;

        return this;
    }
}
