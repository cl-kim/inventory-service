package com.project.inventoryservice.domain.inventory;

import com.project.inventoryservice.api.closing.dto.StockResponseDto;
import com.project.inventoryservice.api.dashboard.dto.DaySummaryDto;
import com.project.inventoryservice.api.dashboard.dto.HotSummaryDto;
import com.project.inventoryservice.api.dashboard.dto.ProductDto;
import com.project.inventoryservice.api.dashboard.dto.YearSummaryDto;
import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import com.project.inventoryservice.domain.product.Product;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static com.project.inventoryservice.domain.inventory.QInventory.inventory;
import static com.project.inventoryservice.domain.monthlyInventory.QMonthlyInventory.monthlyInventory;
import static com.project.inventoryservice.domain.monthlycheck.QMonthlyCheck.monthlyCheck;
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

    @Override
    public Integer findOneInventory(Product product, LocalDate date) {
        LocalDate startDate = date.withDayOfMonth(1);
        Integer value = jpaQueryFactory
                .select(inventory.quantity.sum())
                .from(inventory)
                .where(inventory.product.eq(product),
                        inventory.date.between(startDate, date))
                .fetchFirst();
        return value == null ? 0 : value;
    }

    public List<InBoundResponseDto> findMonthStock(LocalDate date) {
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
                        monthlyCheck.endMonth.yearMonth(),
                        inventory.quantity.sum()))
                .from(inventory)
                .rightJoin(monthlyCheck)
                .on(monthlyCheck.endMonth.yearMonth().eq(inventory.date.yearMonth()),
                        inventory.quantity.gt(0),
                        inventory.date.between(startDate, endDate))
                .where(monthlyCheck.endMonth.between(startDate, endDate))
                .groupBy(monthlyCheck.endMonth.yearMonth())
                .fetch();

        List<ProductDto> outBoundList = jpaQueryFactory.select(
                        Projections.constructor(ProductDto.class,
                                monthlyCheck.endMonth.yearMonth(),
                                inventory.quantity.sum()))
                .from(monthlyCheck)
                .leftJoin(inventory)
                .on(inventory.date.yearMonth().eq(monthlyCheck.endMonth.yearMonth()),
                        inventory.quantity.lt(0),
                        inventory.date.between(startDate, endDate))
                .where(monthlyCheck.endMonth.between(startDate, endDate))
                .groupBy(monthlyCheck.endMonth.yearMonth())
                .fetch();

        List<ProductDto> inventoryList = jpaQueryFactory.select(
                        Projections.constructor(ProductDto.class,
                                monthlyCheck.endMonth.yearMonth(),
                                monthlyInventory.quantity.sum()))
                .from(monthlyInventory)
                .rightJoin(monthlyCheck)
                .on(monthlyCheck.endMonth.yearMonth().eq(monthlyInventory.monthlyDate.yearMonth()),
                        monthlyInventory.monthlyDate.between(startDate, endDate))
                .where(monthlyCheck.endMonth.between(startDate,endDate))
                .groupBy(monthlyCheck.endMonth.yearMonth())
                .fetch();

        return new YearSummaryDto(inBoundList, outBoundList, inventoryList);
    }

    @Override
    public DaySummaryDto findDaySummary(LocalDate date) {
        return DaySummaryDto.builder()
                .date(date)
                .inbound(findDayInbound(date))
                .outbound(findDayOutbound(date))
                .inventory(findDayInventory(date))
                .build();
    }

    public Integer findDayInbound(LocalDate date) {
        return Optional.ofNullable(
                jpaQueryFactory.select(inventory.quantity.sum())
                .from(inventory)
                .where(inventory.quantity.gt(0),
                        inventory.date.eq(date))
                .fetchOne())
                .orElse(0);
    }

    public Integer findDayOutbound(LocalDate date) {
        return Optional.ofNullable(
                jpaQueryFactory.select(inventory.quantity.sum())
                .from(inventory)
                .where(inventory.quantity.lt(0),
                        inventory.date.eq(date))
                .fetchOne())
                .orElse(0);
    }

    public Integer findDayInventory(LocalDate date) {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", monthlyInventory.monthlyDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",date, ConstantImpl.create("%y%m")
        );

        LocalDate startDay = date.withDayOfMonth(1);

        Integer lastMonthInventory = Optional.ofNullable(
                jpaQueryFactory.select(monthlyInventory.quantity.sum())
                .from(monthlyInventory)
                .where(formattedDate.eq(conditionDate))
                .fetchOne())
                .orElse(0);

        Integer thisMonthInventory = Optional.ofNullable(
                jpaQueryFactory.select(inventory.quantity.sum())
                .from(inventory)
                .where(inventory.date.between(startDay, date))
                .fetchOne())
                .orElse(0);

        return lastMonthInventory + thisMonthInventory;
    }

    public HotSummaryDto findHotSummary(LocalDate date){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", inventory.date, ConstantImpl.create("%y%m")
        );

        StringTemplate formattedDate1 = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", monthlyInventory.monthlyDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",date, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate1 = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",date.minusMonths(1), ConstantImpl.create("%y%m")
        );


        LocalDate startDay = date.withDayOfMonth(1);

        //카테고리 별 quantity sum
        Map<String, Integer> inventoryList = new HashMap<>();

        List<Tuple> results = jpaQueryFactory.select(
                inventory.product.categoryCode,
                inventory.quantity.sum())
                .from(inventory)
                .groupBy(inventory.product.categoryCode)
                .where(formattedDate.eq(conditionDate))
                .fetch();

        for(Tuple result : results){
            inventoryList.put(result.get(inventory.product.categoryCode), result.get(inventory.quantity.sum()));
        }

        // 현재 재고 금액
        // 현재 재고 = 전월 재고 + 입고량 - 출고량
        Integer stockPrice = jpaQueryFactory.select(
                monthlyInventory.quantity.multiply(monthlyInventory.product.price))
                .from(monthlyInventory)
                .where(conditionDate1.eq(formattedDate1))
                .fetch().stream().reduce(0, Integer::sum);

        Integer prices = jpaQueryFactory.select(
                inventory.quantity.multiply(inventory.product.price))
                .from(inventory)
                .where(inventory.date.between(startDay, date))
                .fetch().stream().reduce(0, Integer::sum);
        Integer inventoryPrice = stockPrice + prices;

        // 당월 출고 금액
        Integer outBoundPrice = jpaQueryFactory.select(
                        inventory.quantity.multiply(inventory.price))
                .from(inventory)
                .where(formattedDate.eq(conditionDate), inventory.quantity.lt(0))
                .fetch().stream().reduce(0, Integer::sum) * -1;

        // 당월 출고 제일 많은 제품명
        Tuple bestSellerTuple = jpaQueryFactory.select(
                        inventory.product.productName,
                        inventory.quantity.sum())
                .from(inventory)
                .where(formattedDate.eq(conditionDate), inventory.quantity.lt(0))
                .groupBy(inventory.product)
                .orderBy(inventory.quantity.sum().asc())
                .fetchFirst();

        String bestSeller = "";
        if(bestSellerTuple != null){
            bestSeller = bestSellerTuple.get(inventory.product.productName);
        }

        return HotSummaryDto.builder()
                .inventoryList(inventoryList)
                .inventoryPrice(inventoryPrice)
                .outBoundPrice(outBoundPrice)
                .bestSeller(bestSeller)
                .build();
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
