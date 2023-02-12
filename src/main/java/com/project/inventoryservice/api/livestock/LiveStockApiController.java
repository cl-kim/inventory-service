package com.project.inventoryservice.api.livestock;

import com.project.inventoryservice.service.monthlycheck.MonthlyCheckService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class LiveStockApiController {

    private final MonthlyCheckService monthlyCheckService;

    @Operation(summary = "마감여부 조회", description = "각 월별 마감 여부를 조회합니다")
    @GetMapping("/live-stock/check/{date}")
    public Boolean checkMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return monthlyCheckService.checkMonthlyEnd(date);
    }

    @Operation(summary = "마감 처리", description = "해당 월을 마감처리합니다.")
    @PostMapping("/live-stock/end/{date}")
    public Boolean saveMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)  {
        return monthlyCheckService.saveMonthlyEnd(date);
    }
    @Operation(summary = "최초 마감 처리", description = "최초 월 설정 시 전월 마감처리합니다.")
    @PostMapping("/live-stock/firstend/{date}")
    public Boolean saveFirstMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return monthlyCheckService.saveFirstMonthlyEnd(date);
    }

    @Operation(summary = "마감 처리 취소", description = "해당 월의 마감처리를 취소합니다.")
    @PutMapping("/live-stock/end-cancel/{date}")
    public Boolean cancelMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return monthlyCheckService.cancelMonthlyEnd(date);
    }
}