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

    @Override
    public List<StockResponseDto> findLastStock(LocalDate lastMonth){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", monthlyInventory.monthlyDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",lastMonth, ConstantImpl.create("%y%m")
        );

        return jpaQueryFactory
                .select(Projections.constructor(
                        StockResponseDto.class,
                        monthlyInventory.product.productName,
                        monthlyInventory.quantity
                ))
                .from(monthlyInventory)
                .where(formattedDate.eq(conditionDate))
                .fetch();
    }

}
