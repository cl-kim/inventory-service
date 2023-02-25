package com.project.inventoryservice.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventoryservice.domain.inbound.InBound;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventory;
import com.project.inventoryservice.domain.outbound.OutBound;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryCode;

    private String productCode;

    private String productName;

    private String productUnit;

    private Float amount;
    private Integer price;

    private String productStatus;

    private String memo;

    @JsonIgnoreProperties(value = {"product"})
    @OneToMany(mappedBy = "product")
    private List<MonthlyInventory> monthlyInventoryList = new ArrayList<>();

    @JsonIgnoreProperties(value = {"product"})
    @OneToMany(mappedBy = "product")
    private List<InBound> inBoundList = new ArrayList<>();

    @JsonIgnoreProperties(value = {"product"})
    @OneToMany(mappedBy = "product")
    private List<OutBound> outBoundList = new ArrayList<>();

    @Builder
    public Product(Long id, String categoryCode, String productCode, String productName,
                   String productUnit, Float amount, Integer price, String productStatus, String memo) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.productCode = productCode;
        this.productName = productName;
        this.productUnit = productUnit;
        this.amount = amount;
        this.price = price;
        this.productStatus = productStatus;
        this.memo = memo;
    }

    public Product update(String categoryCode, String productName,
                          String productUnit, Float amount, Integer price, String productStatus, String memo){
        this.categoryCode = categoryCode;
        this.productName = productName;
        this.productUnit = productUnit;
        this.amount = amount;
        this.price = price;
        this.productStatus = productStatus;
        this.memo = memo;

        return this;
    }

    public Product updateStatus(String status){
        this.productStatus = status;

        return this;
    }

}
