package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.monthly.dto.InventoryResponseDto;
import com.project.inventoryservice.api.monthly.dto.MonthlyResponseDto;
import com.project.inventoryservice.domain.inbound.InBoundRepository;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventoryRepository;
import com.project.inventoryservice.domain.outbound.OutBoundRepository;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MonthlyService {

    private final InBoundRepository inBoundRepository;

    private final OutBoundRepository outBoundRepository;

    private final MonthlyInventoryRepository monthlyInventoryRepository;

    private final ProductRepository productRepository;

    /**
     * 특정 시점의 재고 조회
     */
    public List<InventoryResponseDto> findInventory(LocalDate date){

        // 전월 재고 조회
//        List<InventoryResponseDto> lastInventory = monthlyInventoryRepository.findLastStock(date.minusMonths(1L));

        // 현월 입고 내역 조회
        List<InventoryResponseDto> currentInStock = inBoundRepository.findMonthStock(date);

        // 현월 출고 내역 조회
        List<InventoryResponseDto> currentOutStock = outBoundRepository.findMonthStock(date);


        return null;
    }

    /**
     * 월별 재고 조회
     */
    public List<MonthlyResponseDto> findMonthlyInventory(LocalDate startDate, LocalDate endDate){
        if(startDate == null){
            startDate = LocalDate.now().minusYears(1L).minusMonths(1L);
            endDate = LocalDate.now().minusMonths(1L);
        }
        List<Product> inventoryList = productRepository.findInventory(startDate, endDate);

        return inventoryList.stream().map(MonthlyResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 월별 입고 조회
     */
    public List<MonthlyResponseDto> findMonthlyInbound(LocalDate startDate, LocalDate endDate){
        if(startDate == null){
            startDate = LocalDate.now().minusYears(1L).minusMonths(1L);
            endDate = LocalDate.now().minusMonths(1L);
        }
        List<MonthlyResponseDto> inventoryList = productRepository.findInBound(startDate, endDate);
        return inventoryList;
//        return inventoryList.stream().map(MonthlyResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 월별 출고 조회
     */
    public List<MonthlyResponseDto> findMonthlyOutbound(LocalDate startDate, LocalDate endDate){
        return null;
    }

}
