package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.api.monthly.dto.*;
import com.project.inventoryservice.domain.inventory.InventoryRepository;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventoryRepository;
import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class MonthlyService {

    private final MonthlyInventoryRepository monthlyInventoryRepository;

    private final InventoryRepository inventoryRepository;

    private final ProductRepository productRepository;


    /**
     * 특정 시점의 재고 조회
     */
    public List<StockResponseDto> findInventory(LocalDate date) {
        // 전월 재고 조회 <List> 상품 특징 4개 ( 상품 코드, 이름, 수량 )
        List<StockResponseDto> lastInventory = findMonthInventory(date.minusMonths(1L));
        List<StockResponseDto> sumList = inventoryRepository.findTotalInventory(date);

        List<StockResponseDto> mergedList = Stream.concat(lastInventory.stream(), sumList.stream())
                .collect(Collectors.groupingBy(dto -> dto.getProductCode() + dto.getProductName()))
                .values().stream()
                .map(groupedDtos -> {
                    StockResponseDto dto = groupedDtos.get(0);
                    return new StockResponseDto(Category.findByName(dto.getCategoryName()).getKey(), dto.getProductCode(), dto.getProductName(),
                            groupedDtos.stream().mapToInt(StockResponseDto::getQuantity).sum());
                })
                .collect(Collectors.toList());

        return mergedList;
    }

    /**
     * 단일 월 재고 조회
     * 해당달 사이 날짜가 요청이 되면 그 달의 월간 재고를 반환
     */
    public List<StockResponseDto> findMonthInventory(LocalDate month) {
        if (month == null) {
            month = LocalDate.now().minusMonths(1L);
        }
        List<StockResponseDto> inventoryList = monthlyInventoryRepository.findMonthlyInventory(month);

        return inventoryList;
    }


    /**
     * 월별 재고 조회
     */
    public List<MonthlyResponseDto> findMonthlyInventory(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().minusYears(1L).minusMonths(1L);
            endDate = LocalDate.now().minusMonths(1L);
        }
        List<Product> inventoryList = productRepository.findInventory(startDate, endDate);

        return inventoryList.stream().map(MonthlyResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 월별 입고 조회
     */
    public List<MonthlyResponseDto> findMonthlyInbound(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            LocalDate now = LocalDate.now().minusMonths(1L);
            startDate = LocalDate.of(now.minusYears(1L).getYear(), now.getMonthValue(), 1);
            endDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.lengthOfMonth());
        }
        List<MonthlyResponseDto> inventoryList = productRepository.findInBound(startDate, endDate);
        return inventoryList;
    }

    /**
     * 월별 출고 조회
     */
    public List<MonthlyResponseDto> findMonthlyOutbound(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().minusYears(1L).minusMonths(1L);
            endDate = LocalDate.now().minusMonths(1L);
        }

        return productRepository.findOutBound(startDate, endDate);
    }

}
