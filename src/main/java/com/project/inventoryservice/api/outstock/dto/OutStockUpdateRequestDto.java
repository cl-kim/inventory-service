package com.project.inventoryservice.api.outstock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OutStockUpdateRequestDto {

    private LocalDate outStockDate;

    private String customer;

    private Long price;

    private Integer quantity;

    private String memo;
}
