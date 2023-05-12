package com.project.inventoryservice.api.dashboard;

import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.service.dashboard.DashBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class DashBoardApiController {

    private final DashBoardService dashBoardService;

    @Operation(summary = "연간 통계 내역 조회", description = "최근 12개월간 입고,출고,재고 내역 조회")
    @GetMapping("/dashboard/summary/year")
    public YearSummaryDto getYearSummary() {
        return dashBoardService.getYearSummary();
    }
}
