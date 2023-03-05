package com.project.inventoryservice.api.closing.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockResponseDto {

    private Integer currentMonthStock;
    private Integer currentMonthOutStock;
    private Integer currentMonthInStock;
    private Integer lastMonthStock;
    private Integer lastMonthOutStock;
    private Integer lastMonthInStock;
    private String productCode;
    private String productName;

    public StockResponseDto(Integer currentMonthStock, Integer currentMonthOutStock, Integer currentMonthInStock,
                            Integer lastMonthStock, Integer lastMonthOutStock, Integer lastMonthInStock,
                            String productCode, String productName) {
        this.currentMonthStock = currentMonthStock;
        this.currentMonthOutStock = currentMonthOutStock;
        this.currentMonthInStock = currentMonthInStock;
        this.lastMonthStock = lastMonthStock;
        this.lastMonthOutStock = lastMonthOutStock;
        this.lastMonthInStock = lastMonthInStock;
        this.productCode = productCode;
        this.productName = productName;
    }
}
