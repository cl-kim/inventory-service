package com.project.inventoryservice.domain.monthlyInventory;

import com.project.inventoryservice.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class MonthlyInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate monthlyDate;

    private Integer quantity;

    @Builder
    public MonthlyInventory(Product product, LocalDate monthlyDate, Integer quantity) {
        this.product = product;
        this.monthlyDate = monthlyDate;
        this.quantity = quantity;
    }
}
