package com.project.inventoryservice.api.monthly;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.api.monthly.dto.MonthlyResponseDto;
import com.project.inventoryservice.service.stock.MonthlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MonthlyApiController {

    private final MonthlyService monthlyService;

    @Operation(summary = "현재 재고 조회", description = "현재 시점 재고를 조회합니다.")
    @GetMapping("/live/inventory")
    public List<StockResponseDto> findLiveInventory() {
        LocalDate now = LocalDate.now();
        return monthlyService.findInventory(now);
    }


    @Operation(summary = "월별 재고 조회", description = "최근 12개월 간의 월별 재고를 조회합니다.")
    @GetMapping("/monthly/inventory")
    public List<MonthlyResponseDto> findMonthlyInventory(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyInventory(startDate, endDate);
    }

    @Operation(summary = "월별 입고 내역 조회", description = "최근 12개월 간의 월별 입고 내역을 조회합니다.")
    @GetMapping("/monthly/inbound")
    public List<MonthlyResponseDto> findMonthlyInbound(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyInbound(startDate, endDate);
    }

    @Operation(summary = "월별 출고 내역 조회", description = "최근 12개월 간의 월별 출고 내역을 조회합니다.")
    @GetMapping("/monthly/outbound")
    public List<MonthlyResponseDto> findMonthlyOutbound(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyOutbound(startDate, endDate);
    }

}
