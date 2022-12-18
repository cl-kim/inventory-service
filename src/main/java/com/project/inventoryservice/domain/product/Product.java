package com.project.inventoryservice.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Category category;

    private String productName;

    private String productUnit;


    @Builder
    public Product(Category category, String productName, String productUnit){
        this.category = category;
        this.productName = productName;
        this.productUnit = productUnit;
    }

}
