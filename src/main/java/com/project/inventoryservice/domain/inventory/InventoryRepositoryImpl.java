package com.project.inventoryservice.domain.inventory;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.api.dashboard.dto.ProductDto;
import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.project.inventoryservice.domain.inventory.QInventory.inventory;
import static com.project.inventoryservice.domain.monthlyInventory.QMonthlyInventory.monthlyInventory;
import static com.project.inventoryservice.domain.product.QProduct.product;


@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InBoundResponseDto> findInBoundPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                InBoundResponseDto.class,
                                inventory.id,
                                product.categoryCode,
                                product.id,
                                product.productCode,
                                product.productName,
                                inventory.date,
                                inventory.quantity,
                                inventory.memo
                        )
                )
                .from(inventory)
                .innerJoin(inventory.product, product)
                .where(inventory.quantity.gt(0),
                        eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate,endDate))
                .orderBy(inventory.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<OutBoundResponseDto> findOutBoundPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                OutBoundResponseDto.class,
                                inventory.id,
                                product.categoryCode,
                                product.id,
                                product.productCode,
                                product.productName,
                                inventory.date,
                                inventory.customer,
                                inventory.price,
                                inventory.quantity,
                                inventory.memo
                        )
                )
                .from(inventory)
                .innerJoin(inventory.product, product)
                .where(inventory.quantity.lt(0),
                        eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate,endDate))
                .orderBy(inventory.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<StockResponseDto> findTotalInventory(String categoryCode, LocalDate date) {
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return jpaQueryFactory.select(
                Projections.constructor(StockResponseDto.class,
                        inventory.product.categoryCode,
                        inventory.product.productCode,
                        inventory.product.productName,
                        inventory.quantity.sum()))
                .from(inventory)
                .where(eqCategoryCode(categoryCode),
                        inventory.date.between(startDate,date))
                .groupBy(product.categoryCode, product.productCode, product.productName)
                .fetch();
    }

    @Override
    public List<StockResponseDto> findTotalInBound(String categoryCode, LocalDate date) {
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return jpaQueryFactory.select(
                        Projections.constructor(StockResponseDto.class,
                                inventory.product.categoryCode,
                                inventory.product.productCode,
                                inventory.product.productName,
                                inventory.quantity.sum()))
                .from(inventory)
                .where(eqCategoryCode(categoryCode),
                        inventory.quantity.gt(0),
                        inventory.date.between(startDate,date)
                )
                .groupBy(product.categoryCode, product.productCode, product.productName)
                .fetch();
    }

    @Override
    public List<StockResponseDto> findTotalOutBound(String categoryCode, LocalDate date) {
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return jpaQueryFactory.select(
                        Projections.constructor(StockResponseDto.class,
                                inventory.product.categoryCode,
                                inventory.product.productCode,
                                inventory.product.productName,
                                inventory.quantity.sum()))
                .from(inventory)
                .where(eqCategoryCode(categoryCode),
                        inventory.quantity.lt(0),
                        inventory.date.between(startDate,date)
                )
                .groupBy(product.categoryCode, product.productCode, product.productName)
                .fetch();
    }

    public List<InBoundResponseDto> findMonthStock(LocalDate date){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", inventory.date, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",date, ConstantImpl.create("%y%m")
        );

        return jpaQueryFactory.select(
                Projections.constructor(InBoundResponseDto.class,
                        inventory.product.id,
                        inventory.product.productCode,
                        inventory.product.productName,
                        inventory.quantity.sum()))
                .from(inventory)
                .where(formattedDate.eq(conditionDate))
                .groupBy(inventory.product.id, formattedDate)
                .fetch();
    }

    @Override
    public YearSummaryDto findYearSummary(LocalDate now) {
        LocalDate date = now.minusMonths(1L);
        LocalDate startDate = date.minusYears(1L).withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        List<ProductDto> inBoundList = jpaQueryFactory.select(
                Projections.constructor(ProductDto.class,
                        inventory.date.yearMonth(),
                        inventory.quantity.sum()))
                .from(inventory)
                .where(inventory.quantity.gt(0),
                        inventory.date.between(startDate, endDate))
                .groupBy(inventory.date.yearMonth())
                .fetch();

        List<ProductDto> outBoundList = jpaQueryFactory.select(
                        Projections.constructor(ProductDto.class,
                                inventory.date.yearMonth(),
                                inventory.quantity.sum()))
                .from(inventory)
                .where(inventory.quantity.lt(0),
                        inventory.date.between(startDate, endDate))
                .groupBy(inventory.date.yearMonth())
                .fetch();

        List<ProductDto> inventoryList = jpaQueryFactory.select(
                        Projections.constructor(ProductDto.class,
                                monthlyInventory.monthlyDate.yearMonth(),
                                monthlyInventory.quantity.sum()))
                .from(monthlyInventory)
                .where(monthlyInventory.monthlyDate.between(startDate,endDate))
                .groupBy(monthlyInventory.monthlyDate.yearMonth())
                .fetch();

        return new YearSummaryDto(inBoundList, outBoundList, inventoryList);
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return inventory.date.between(startDate,endDate);
        }
        LocalDate today = LocalDate.now();
        return inventory.date.between(today.minusMonths(1), today);
    }

    private BooleanExpression eqId(Long productId){
        return productId == null ? null : inventory.product.id.eq(productId);
    }

    private BooleanExpression eqCategoryCode(String categoryCode){
        return categoryCode == null ? null : inventory.product.categoryCode.eq(categoryCode);
    }

}
