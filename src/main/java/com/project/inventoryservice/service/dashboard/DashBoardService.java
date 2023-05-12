package com.project.inventoryservice.service.dashboard;

import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.domain.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class DashBoardService {

    private final InventoryRepository inventoryRepository;

    public YearSummaryDto getYearSummary() {
        LocalDate now = LocalDate.now();
        return inventoryRepository.findYearSummary(now);
    }
}
