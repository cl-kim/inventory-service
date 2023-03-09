package com.project.inventoryservice.domain.monthlyInventory;


import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.inventoryservice.domain.monthlyInventory.QMonthlyInventory.monthlyInventory;


@RequiredArgsConstructor
public class MonthlyInventoryRepositoryImpl implements MonthlyInventoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 해당달 사이 날짜가 요청이 되면 그 달의 월간 재고를 반환
     */
    @Override
    public List<StockResponseDto> findMonthlyInventory(LocalDate month){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", monthlyInventory.monthlyDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",month, ConstantImpl.create("%y%m")
        );

        return jpaQueryFactory
                .select(Projections.constructor(
                        StockResponseDto.class,
                        monthlyInventory.product.categoryCode,
                        monthlyInventory.product.productCode,
                        monthlyInventory.product.productName,
                        monthlyInventory.quantity
                ))
                .from(monthlyInventory)
                .where(formattedDate.eq(conditionDate))
                .fetch();
    }

}
