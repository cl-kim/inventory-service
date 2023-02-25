package com.project.inventoryservice.domain.inbound;

import com.project.inventoryservice.api.monthly.dto.InventoryResponseDto;
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


import static com.project.inventoryservice.domain.inbound.QInBound.inBound;
import static com.project.inventoryservice.domain.product.QProduct.product;

@RequiredArgsConstructor
public class InBoundRepositoryImpl implements InBoundRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InBound> findPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(inBound)
                .from(inBound)
                .leftJoin(inBound.product, product)
                .where(eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate,endDate))
                .orderBy(inBound.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<InventoryResponseDto> findMonthStock(LocalDate date){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", inBound.inBoundDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})",date, ConstantImpl.create("%y%m")
        );

        return jpaQueryFactory.select(
                        Projections.constructor(InventoryResponseDto.class,
                                inBound.product.id,
                                inBound.product.productCode,
                                inBound.product.productName,
                                inBound.quantity.sum()))
                .from(inBound)
                .where(formattedDate.eq(conditionDate))
                .groupBy(inBound.product.id, formattedDate)
                .fetch();
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return inBound.inBoundDate.between(startDate,endDate);
        }
        LocalDate today = LocalDate.now();
        return inBound.inBoundDate.between(today.minusMonths(1), today);
    }

    private BooleanExpression eqId(Long productId){
        return productId == null ? null : inBound.product.id.eq(productId);
    }

    private BooleanExpression eqCategoryCode(String categoryCode){
        return categoryCode == null ? null : inBound.product.categoryCode.eq(categoryCode);
    }
}
