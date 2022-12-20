package com.project.inventoryservice.domain.outstock;

import com.project.inventoryservice.api.outstock.dto.OutStockResponseDto;
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
public class OutStockRepositoryImpl implements OutStockRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<OutStockResponseDto> findPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate) {
        QueryResults<OutStockResponseDto> results = jpaQueryFactory.select(
                        Projections.constructor(OutStockResponseDto.class,
                                QOutStock.outStock.id,
                                QOutStock.outStock.product.id,
                                QOutStock.outStock.outStockDate,
                                QOutStock.outStock.customer,
                                QOutStock.outStock.price,
                                QOutStock.outStock.quantity,
                                QOutStock.outStock.memo
                        ))
                .from(QOutStock.outStock)
                .where(QOutStock.outStock.product.id.eq(productId),
                        betweenDate(startDate,endDate))
                .orderBy(QOutStock.outStock.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return QOutStock.outStock.outStockDate.between(startDate,endDate);
        }
        return null;
    }
}
