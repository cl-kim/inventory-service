package com.project.inventoryservice.api.closing;

import com.project.inventoryservice.service.closing.ClosingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ClosingApiController {

    private final ClosingService closingService;

    @Operation(summary = "마감여부 조회", description = "각 월별 마감 여부를 조회합니다")
    @GetMapping("/closing/inventory/check/{date}")
    public Boolean checkMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return closingService.checkMonthlyClosing(date);
    }

    @Operation(summary = "마감 처리", description = "해당 월을 마감처리합니다.")
    @PostMapping("/closing/inventory/{date}")
    public Boolean saveMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)  {
        return closingService.saveMonthlyClosing(date);
    }
    @Operation(summary = "최초 마감 처리", description = "최초 월 설정 시 전월 마감처리합니다.")
    @PostMapping("/closing-first/inventory/{date}")
    public Boolean saveFirstMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return closingService.saveFirstMonthlyClosing(date);
    }

    @Operation(summary = "마감 처리 취소", description = "해당 월의 마감처리를 취소합니다.")
    @DeleteMapping("/closing/inventory/{date}")
    public Boolean cancelMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return closingService.cancelMonthlyClosing(date);
    }
}
