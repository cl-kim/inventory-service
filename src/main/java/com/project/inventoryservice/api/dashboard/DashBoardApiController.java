package com.project.inventoryservice.api.dashboard;

import com.project.inventoryservice.api.dashboard.dto.DaySummaryDto;
import com.project.inventoryservice.api.dashboard.dto.HotSummaryDto;
import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.service.dashboard.DashBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class DashBoardApiController {

    private final DashBoardService dashBoardService;

    @Operation(summary = "연간 통계 내역 조회", description = "최근 12개월간 입고,출고,재고 내역 조회")
    @GetMapping("/dashboard/summary/year")
    public YearSummaryDto getYearSummary() {
        LocalDate date = LocalDate.now();
        return dashBoardService.getYearSummary(date);
    }

    @Operation(summary = "일별 통계 내역 조회", description = "금일, 전일 입고,출고,재고 내역 조회")
    @GetMapping("/dashboard/summary/days")
    public List<DaySummaryDto> getDaysSummary() {
        LocalDate date = LocalDate.now();
        return dashBoardService.getDaysSummary(date);
    }

    @Operation(summary = "이외 통계 내역 조회", description = "재고 카테고리 비율, 재고금액, 출고금액, 베스트 판매 아이템")
    @GetMapping("/dashboard/summary/hot")
    public HotSummaryDto getHotSummary() {
        LocalDate date = LocalDate.now();
        return dashBoardService.getHotSummary(date);
    }
}
