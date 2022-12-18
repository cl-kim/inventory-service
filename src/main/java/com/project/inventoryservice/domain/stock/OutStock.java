package com.project.inventoryservice.domain.stock;

import com.project.inventoryservice.domain.product.Product;
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

    private Integer quantity;

    private String memo;

}
