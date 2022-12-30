package com.project.inventoryservice.domain.instock;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class InStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate inStockDate;

    private Integer quantity;

    private String memo;

    @Builder
    public InStock(Product product, LocalDate inStockDate, Integer quantity, String memo) {
        this.product = product;
        this.inStockDate = inStockDate;
        this.quantity = quantity;
        this.memo = memo;
    }

    public InStock update(Product product, LocalDate inStockDate, Integer quantity, String memo){
        this.product = product;
        this.inStockDate = inStockDate;
        this.quantity = quantity;
        this.memo = memo;

        return this;
    }
}
