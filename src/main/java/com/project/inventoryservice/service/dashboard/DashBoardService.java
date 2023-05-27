package com.project.inventoryservice.service.dashboard;

import com.project.inventoryservice.api.dashboard.dto.DaySummaryDto;
import com.project.inventoryservice.api.dashboard.dto.HotSummaryDto;
import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.domain.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashBoardService {

    private final InventoryRepository inventoryRepository;

    public YearSummaryDto getYearSummary(LocalDate date) {
        return inventoryRepository.findYearSummary(date);
    }

    public List<DaySummaryDto> getDaysSummary(LocalDate date) {
        List<DaySummaryDto> results = new ArrayList<>();
        results.add(inventoryRepository.findDaySummary(date));
        results.add(inventoryRepository.findDaySummary(date.minusDays(1)));
        return  results;
    }

    public HotSummaryDto getHotSummary(LocalDate date){
        return inventoryRepository.findHotSummary(date);
    }
}
