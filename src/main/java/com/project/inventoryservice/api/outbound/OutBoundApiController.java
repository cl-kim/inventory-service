package com.project.inventoryservice.api.outbound;


import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundSaveRequestDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundUpdateRequestDto;
import com.project.inventoryservice.service.stock.OutBoundService;
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
public class OutBoundApiController {

    private final OutBoundService outBoundService;

    @Operation(summary = "츨고 내역 조회", description = "조건 검색 : 카테고리코드, 상품번호, 날짜 m")
    @GetMapping("/outbound")
    public Page<OutBoundResponseDto> getProductList(Pageable pageable, @RequestParam(required = false) Long productId,
                                                    @RequestParam(required = false) String categoryCode,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
        return outBoundService.getPage(pageable, productId, categoryCode, startDate, endDate);
    }

    @Operation(summary = "출고 내역 저장")
    @PostMapping("/outbound")
    @ResponseStatus(HttpStatus.CREATED)
    public OutBoundResponseDto save(@RequestBody OutBoundSaveRequestDto requestDto) {
        return outBoundService.save(requestDto);
    }

    @Operation(summary = "출고 내역 수정")
    @PutMapping("/outbound/{outBoundId}")
    public OutBoundResponseDto update(@PathVariable Long outBoundId, @RequestBody @Valid OutBoundUpdateRequestDto requestDto) {
        return outBoundService.update(outBoundId, requestDto);
    }

    @Operation(summary = "출고 내역 삭제")
    @DeleteMapping("/outbound/{outBoundId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long outBoundId) {
        outBoundService.delete(outBoundId);
    }
}
