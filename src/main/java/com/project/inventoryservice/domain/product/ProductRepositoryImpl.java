package com.project.inventoryservice.domain.product;

import com.project.inventoryservice.api.monthly.dto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.inventoryservice.domain.inventory.QInventory.inventory;
import static com.project.inventoryservice.domain.monthlyInventory.QMonthlyInventory.monthlyInventory;
import static com.project.inventoryservice.domain.product.QProduct.product;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> findProduct(String categoryCode, Pageable pageable) {
        return jpaQueryFactory.selectFrom(product)
                .where(eqCategory(categoryCode))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression eqCategory(String categoryCode) {
        if (categoryCode == null) {
            return null;
        }
        return product.categoryCode.eq(categoryCode);
    }

    @Override
    public List<Product> findInventory(String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory
                .selectDistinct(product)
                .from(product)
                .join(product.monthlyInventoryList, monthlyInventory).fetchJoin()
                .where(monthlyInventory.monthlyDate.between(startDate, endDate),
                        eqCategory(categoryCode))
                .orderBy(monthlyInventory.monthlyDate.asc(), product.id.asc())
                .fetch();
    }

    @Override
    public List<MonthlyResponseDto> findInBound(String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<MonthlyResponseDto> result = jpaQueryFactory.select(
                product.categoryCode,
                product.productCode,
                product.productName,
                inventory.date.yearMonth(),
                inventory.quantity.sum())
                .from(inventory)
                .innerJoin(inventory.product, product)
                .where(inventory.date.between(startDate, endDate),
                        inventory.quantity.gt(0),
                        eqCategory(categoryCode))
                .groupBy(product.categoryCode, product.productCode, product.productName, inventory.date.yearMonth())
                .orderBy(inventory.date.yearMonth().asc(), product.id.asc())
                .distinct()
                .transform(groupBy(product.productCode).list(
                        Projections.constructor(MonthlyResponseDto.class,
                                product.categoryCode,
                                product.productCode,
                                product.productName,
                                list(Projections.constructor(
                                        ProductDto.class,
                                        inventory.date.yearMonth(),
                                        inventory.quantity.sum()
                                ))))
                );
        return getMonthlyResponseDtos(result);
    }

    @Override
    public List<MonthlyResponseDto> findOutBound(String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<MonthlyResponseDto> result = jpaQueryFactory
                .select(product.categoryCode,
                        product.productCode,
                        product.productName,
                        inventory.date.yearMonth(),
                        inventory.quantity.sum())
                .from(inventory)
                .innerJoin(inventory.product, product)
                .where(inventory.date.between(startDate, endDate),
                        inventory.quantity.lt(0),
                        eqCategory(categoryCode))
                .groupBy(product.categoryCode, product.productCode, product.productName,  inventory.date.yearMonth())
                .orderBy(inventory.date.yearMonth().asc(), product.id.asc())
                .distinct()
                .transform(groupBy(product.id).list(
                        Projections.constructor(MonthlyResponseDto.class,
                                product.categoryCode,
                                product.productCode,
                                product.productName,
                                list(Projections.constructor(
                                        ProductDto.class,
                                        inventory.date.yearMonth(),
                                        inventory.quantity.sum()
                                ))))
                );
        return getMonthlyResponseDtos(result);
    }

    private List<MonthlyResponseDto> getMonthlyResponseDtos(List<MonthlyResponseDto> result) {
        Map<String, List<MonthlyResponseDto>> groupedProductMap = result.stream()
                .collect(Collectors.groupingBy(MonthlyResponseDto::getProductName));

        return groupedProductMap.entrySet().stream()
                .map(entry -> {
                    return MonthlyResponseDto.builder()
                            .categoryName(Category.findByKey(entry.getValue().get(0).getCategoryName()).getName())
                            .productCode(entry.getValue().get(0).getProductCode())
                            .productName(entry.getKey())
                            .monthlyQuantityList(entry.getValue().stream()
                                    .flatMap(dto -> dto.getMonthlyQuantityList().stream())
                                    .collect(Collectors.toList())).build();
                })
                .collect(Collectors.toList());
    }
}
