package com.project.inventoryservice.api.inventory;

import com.project.inventoryservice.service.inventory.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class InventoryApiController {

    private final InventoryService inventoryService;

    @Operation(summary = "첫 시작여부 조회", description = "프로그램의 첫 시작 여부를 조회합니다")
    @GetMapping("/inventory/check/isnew")
    public Boolean checkIsNew() {
        return inventoryService.checkIsNew();
    }

    @Operation(summary = "마감여부 조회", description = "각 월별 마감 여부를 조회합니다")
    @GetMapping("/inventory/end/check/{date}")
    public Boolean checkMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return inventoryService.checkMonthlyEnd(date);
    }

    @Operation(summary = "마감 처리", description = "해당 월을 마감처리합니다.")
    @PostMapping("/inventory/end/{date}")
    public Boolean saveMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)  {
        return inventoryService.saveMonthlyEnd(date);
    }
    @Operation(summary = "최초 마감 처리", description = "최초 월 설정 시 전월 마감처리합니다.")
    @PostMapping("/inventory/end-first/{date}")
    public Boolean saveFirstMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return inventoryService.saveFirstMonthlyEnd(date);
    }

    @Operation(summary = "마감 처리 취소", description = "해당 월의 마감처리를 취소합니다.")
    @DeleteMapping("/inventory/end/{date}")
    public Boolean cancelMonthlyEnd(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return inventoryService.cancelMonthlyEnd(date);
    }
}
