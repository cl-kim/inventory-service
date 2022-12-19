package com.project.inventoryservice.api.instock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class InStockUpdateRequestDto {
    @NotNull
    private LocalDate inStockDate;
    @NotNull
    private Integer quantity;

    private String memo;

}
