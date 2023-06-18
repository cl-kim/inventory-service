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

    @Operation(summary = "현재 재고 내역 조회", description = "현재 시점 재고를 조회합니다.")
    @GetMapping("/live/inventory")
    public List<StockResponseDto> findLiveInventory(@RequestParam(required = false) String categoryCode) {
        LocalDate now = LocalDate.now();
        return monthlyService.findInventory(categoryCode, now);
    }

    @Operation(summary = "현재 입고 내역 조회", description = "현재 시점 당월의 입고 내역을 조회합니다.")
    @GetMapping("/live/inbound")
    public List<StockResponseDto> findLiveInBound(@RequestParam(required = false) String categoryCode) {
        LocalDate now = LocalDate.now();
        return monthlyService.findLiveInBound(categoryCode, now);
    }

    @Operation(summary = "현재 출고 내역 조회", description = "현재 시점 당월의 출고 내역을 조회합니다.")
    @GetMapping("/live/outbound")
    public List<StockResponseDto> findLiveOutBound(@RequestParam(required = false) String categoryCode) {
        LocalDate now = LocalDate.now();
        return monthlyService.findLiveOutBound(categoryCode, now);
    }
    @Operation(summary = "단건 상품 재고 내역 조회", description = "현재 시점 특정 상품의 재고를 조회합니다.")
    @GetMapping("/live/one-check")
    public Integer findOneInventory(String productCode,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return monthlyService.findOneInventory(productCode,date);
    }

    @Operation(summary = "월별 재고 조회", description = "최근 12개월 간의 월별 재고를 조회합니다.")
    @GetMapping("/monthly/inventory")
    public List<MonthlyResponseDto> findMonthlyInventory(@RequestParam(required = false) String categoryCode,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyInventory(categoryCode, startDate, endDate);
    }

    @Operation(summary = "월별 입고 내역 조회", description = "최근 12개월 간의 월별 입고 내역을 조회합니다.")
    @GetMapping("/monthly/inbound")
    public List<MonthlyResponseDto> findMonthlyInbound(@RequestParam(required = false) String categoryCode,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyInbound(categoryCode, startDate, endDate);
    }

    @Operation(summary = "월별 출고 내역 조회", description = "최근 12개월 간의 월별 출고 내역을 조회합니다.")
    @GetMapping("/monthly/outbound")
    public List<MonthlyResponseDto> findMonthlyOutbound(@RequestParam(required = false) String categoryCode,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return monthlyService.findMonthlyOutbound(categoryCode, startDate, endDate);
    }

}
