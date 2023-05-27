package com.project.inventoryservice.api.dashboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class HotSummaryDto {
    Map<String, Integer> inventoryList;
    Integer inventoryPrice;
    Integer outBoundPrice;
    String bestSeller;

    @Builder
    public HotSummaryDto(Map<String, Integer> inventoryList, Integer inventoryPrice, Integer outBoundPrice, String bestSeller) {
        this.inventoryList = inventoryList;
        this.inventoryPrice = inventoryPrice;
        this.outBoundPrice = outBoundPrice;
        this.bestSeller = bestSeller;
    }
}
