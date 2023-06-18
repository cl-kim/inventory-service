package com.project.inventoryservice.domain.monthlyInventory;


import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.domain.product.Product;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public List<StockResponseDto> findMonthlyInventory(LocalDate month, String categoryCode){
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
                .where(formattedDate.eq(conditionDate),
                        eqCategoryCode(categoryCode))
                .fetch();
    }

    @Override
    public Integer findLastMonthStock(Product product, LocalDate date) {
        Integer value = jpaQueryFactory
                .select(monthlyInventory.quantity)
                .from(monthlyInventory)
                .where(monthlyInventory.product.eq(product),
                        monthlyInventory.monthlyDate.loe(date))
                .orderBy(monthlyInventory.monthlyDate.desc())
                .fetchFirst();
        return value == null ? 0 : value;
    }

    private BooleanExpression eqCategoryCode(String categoryCode) {
        return categoryCode == null ? null : monthlyInventory.product.categoryCode.eq(categoryCode);
    }


}
