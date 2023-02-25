package com.project.inventoryservice.domain.product;

import com.project.inventoryservice.api.monthly.dto.*;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.project.inventoryservice.domain.inbound.QInBound.inBound;
import static com.project.inventoryservice.domain.monthlyInventory.QMonthlyInventory.monthlyInventory;
import static com.project.inventoryservice.domain.product.QProduct.product;


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

    private BooleanExpression eqCategory(String categoryCode){
        if(categoryCode == null){
            return null;
        }
        return product.categoryCode.eq(categoryCode);
    }

    @Override
    public List<Product> findInventory(LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory
                .selectDistinct(product)
                .from(product)
                .join(product.monthlyInventoryList, monthlyInventory).fetchJoin()
                .where(monthlyInventory.monthlyDate.between(startDate, endDate))
                .orderBy(monthlyInventory.monthlyDate.asc(), product.id.asc())
                .fetch();

    }
    @Override
    public List<MonthlyResponseDto> findInBound(LocalDate startDate, LocalDate endDate){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", inBound.inBoundDate, ConstantImpl.create("%y%m")
        );

//        List<MonthlyResponseDto> list = jpaQueryFactory
//                .select(product)
//                .from(product)
//                .rightJoin(inBound).on(inBound.product.id.eq(product.id))
//                .where(inBound.inBoundDate.between(startDate, endDate))
//                .groupBy(inBound.inBoundDate.yearMonth(),product.id)
//                .orderBy(inBound.inBoundDate.asc(), product.id.asc())
//                .transform(groupBy(inBound.inBoundDate.yearMonth()).list()
//                        MonthlyResponseDto.class,
//                        product.categoryCode,
//                        product.productCode,
//                        product.productName
//
//                ));
//                .transform(groupBy(product.id).(new QMonthlyResponseDto(
//                        inBound.product.categoryCode,
//                        inBound.product.productCode,
//                        inBound.product.productName,
//                        list(new QProductDto(inBound.inBoundDate.yearMonth().stringValue(),
//                                inBound.quantity.sum()))
//                )));
            return null;
//        return list.keySet().stream()
//                .map(list::get)
//                .collect(Collectors.toList());

//        return list.stream()
//                .collect(Collectors.groupingBy(dto -> dto.getProductCode(), LinkedHashMap::new, Collectors.toList()))
//                .values().stream()
//                .map(list -> {
//                    InventoryResponseDto first = list.get(0);
//                    return new InventoryResponseDto(
//                            first.getProductId(),
//                            first.getProductCode(),
//                            first.getProductName(),
//                            list.stream().map(dto -> new ProductDto(dto.get))
//                    )
//                }))
    }
}
