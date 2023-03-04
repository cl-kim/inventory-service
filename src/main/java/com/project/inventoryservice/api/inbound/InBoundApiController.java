package com.project.inventoryservice.api.inbound;


import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.inbound.dto.InBoundSaveRequestDto;
import com.project.inventoryservice.api.inbound.dto.InBoundUpdateRequestDto;
import com.project.inventoryservice.service.stock.InBoundService;
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
public class InBoundApiController {
    private final InBoundService inBoundService;

    @Operation(summary = "입고 내역 조회", description = "조건 검색 : 상품번호, 날짜")
    @GetMapping("/inbound")
    public Page<InBoundResponseDto> getProductList(Pageable pageable, @RequestParam(required = false) Long productId,
                                                   @RequestParam(required = false) String categoryCode,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
        return inBoundService.getPage(pageable, productId, categoryCode, startDate, endDate);
    }

    @Operation(summary = "입고 내역 저장")
    @PostMapping("/inbound")
    @ResponseStatus(HttpStatus.CREATED)
    public InBoundResponseDto save(@RequestBody InBoundSaveRequestDto requestDto) {
        return inBoundService.save(requestDto);
    }

    @Operation(summary = "입고 내역 수정")
    @PutMapping("/inbound/{id}")
    public InBoundResponseDto update(@PathVariable Long id, @RequestBody @Valid InBoundUpdateRequestDto requestDto) {
        return inBoundService.update(id, requestDto);
    }

    @Operation(summary = "입고 내역 삭제")
    @DeleteMapping("/inbound/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inBoundService.delete(id);
    }
}
