package com.project.inventoryservice.api.dashboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DaySummaryDto {

    LocalDate date;

    Integer inbound;

    Integer outbound;

    Integer inventory;

    @Builder
    public DaySummaryDto(LocalDate date, Integer inbound, Integer outbound, Integer inventory) {
        this.date = date;
        this.inbound = inbound;
        this.outbound = outbound;
        this.inventory = inventory;
    }
}
