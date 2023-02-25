package com.project.inventoryservice.domain.inbound;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class InBound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate inBoundDate;

    private Integer quantity;

    private String memo;

    @Builder
    public InBound(Product product, LocalDate inBoundDate, Integer quantity, String memo) {
        this.product = product;
        this.inBoundDate = inBoundDate;
        this.quantity = quantity;
        this.memo = memo;
    }

    public InBound update(Product product, LocalDate inBoundDate, Integer quantity, String memo){
        this.product = product;
        this.inBoundDate = inBoundDate;
        this.quantity = quantity;
        this.memo = memo;

        return this;
    }
}
