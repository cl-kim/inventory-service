package com.project.inventoryservice.api.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class YearSummaryDto {
    List<ProductDto> inBoundList;

    List<ProductDto> outBoundList;

    List<ProductDto> inventoryList;

    public YearSummaryDto(List<ProductDto> inBoundList, List<ProductDto> outBoundList, List<ProductDto> inventoryList) {
        this.inBoundList = inBoundList;
        this.outBoundList = outBoundList;
        this.inventoryList = inventoryList;
    }
}