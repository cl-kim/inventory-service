package com.project.inventoryservice.api.outstock;


import com.project.inventoryservice.api.outstock.dto.OutStockResponseDto;
import com.project.inventoryservice.api.outstock.dto.OutStockSaveRequestDto;
import com.project.inventoryservice.api.outstock.dto.OutStockUpdateRequestDto;
import com.project.inventoryservice.service.stock.OutStockService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class OutStockApiController {

    private final OutStockService outStockService;

    @Operation(summary = "츨고 내역 조회", description = "조건 검색 : 상품번호, 날짜")
    @GetMapping("/out-stock")
    public Page<OutStockResponseDto> getProductList(Pageable pageable, @RequestParam(required = false) Long productId,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
        return outStockService.getPage(pageable, productId, startDate, endDate);
    }

    @Operation(summary = "출고 내역 저장")
    @PostMapping("/out-stock")
    @ResponseStatus(HttpStatus.CREATED)
    public OutStockResponseDto save(@RequestBody OutStockSaveRequestDto requestDto) {
        return outStockService.save(requestDto);
    }

    @Operation(summary = "출고 내역 수정")
    @PutMapping("/out-stock/{outStockId}")
    public OutStockResponseDto update(@PathVariable Long outStockId, @RequestBody @Valid OutStockUpdateRequestDto requestDto) {
        return outStockService.update(outStockId, requestDto);
    }

    @Operation(summary = "출고 내역 삭제")
    @DeleteMapping("/out-stock/{outStockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long outStockId) {
        outStockService.delete(outStockId);
    }
}
