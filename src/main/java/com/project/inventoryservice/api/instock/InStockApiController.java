package com.project.inventoryservice.api.instock;


import com.project.inventoryservice.api.instock.dto.InStockResponseDto;
import com.project.inventoryservice.api.instock.dto.InStockSaveRequestDto;
import com.project.inventoryservice.api.instock.dto.InStockUpdateRequestDto;
import com.project.inventoryservice.service.stock.InStockService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class InStockApiController {
    private final InStockService inStockService;

    @Operation(summary = "입고 내역 조회", description = "조건 검색 : 상품번호, 날짜")
    @GetMapping("/in-stock")
    public Page<InStockResponseDto> getProductList(Pageable pageable, @RequestParam(required = false) Long productId,
                                  @RequestParam(required = false) String categoryCode,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
        return inStockService.getPage(pageable, productId, categoryCode, startDate, endDate);
    }

    @Operation(summary = "입고 내역 저장")
    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.CREATED)
    public InStockResponseDto save(@RequestBody InStockSaveRequestDto requestDto) {
        return inStockService.save(requestDto);
    }

    @Operation(summary = "입고 내역 수정")
    @PutMapping("/in-stock/{inStockId}")
    public InStockResponseDto update(@PathVariable Long inStockId, @RequestBody @Valid InStockUpdateRequestDto requestDto) {
        return inStockService.update(inStockId, requestDto);
    }

    @Operation(summary = "입고 내역 삭제")
    @DeleteMapping("/in-stock/{inStockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long inStockId) {
        inStockService.delete(inStockId);
    }
}
