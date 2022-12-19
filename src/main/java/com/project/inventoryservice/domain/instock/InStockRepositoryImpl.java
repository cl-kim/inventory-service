package com.project.inventoryservice.domain.instock;

import com.project.inventoryservice.api.instock.dto.InStockResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@RequiredArgsConstructor
public class InStockRepositoryImpl implements InStockRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<InStockResponseDto> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate) {
        QueryResults<InStockResponseDto> results = jpaQueryFactory.select(
                Projections.constructor(InStockResponseDto.class,
                        QInStock.inStock.id,
                        QInStock.inStock.product.id,
                        QInStock.inStock.inStockDate,
                        QInStock.inStock.quantity,
                        QInStock.inStock.memo
                        ))
                .from(QInStock.inStock)
                .where(QInStock.inStock.product.id.eq(productId),
                        betweenDate(startDate,endDate))
                .orderBy(QInStock.inStock.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return QInStock.inStock.inStockDate.between(startDate,endDate);
        }
        LocalDate today = LocalDate.now();
        return QInStock.inStock.inStockDate.between(today.minusMonths(1), today);
    }

}
