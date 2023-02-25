package com.project.inventoryservice.domain.outbound;

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

import static com.project.inventoryservice.domain.outbound.QOutBound.outBound;
import static com.project.inventoryservice.domain.product.QProduct.product;

@RequiredArgsConstructor
public class OutBoundRepositoryImpl implements OutBoundRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutBound> findList(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(outBound)
                .from(outBound)
                .leftJoin(outBound.product, product)
                .where(eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate, endDate))
                .orderBy(outBound.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<InventoryResponseDto> findMonthStock(LocalDate date) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", outBound.outBoundDate, ConstantImpl.create("%y%m")
        );

        StringTemplate conditionDate = Expressions.stringTemplate(
                "DATE_FORMAT({0},{1})", date, ConstantImpl.create("%y%m")
        );

        return jpaQueryFactory.select(Projections.constructor(InventoryResponseDto.class,
                        outBound.product.id,
                        outBound.product.productCode,
                        outBound.product.productName,
                        outBound.quantity.sum()))
                .from(outBound)
                .where(formattedDate.eq(conditionDate))
                .groupBy(outBound.product.id)
                .fetch();
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate) {
        if (!(startDate == null) && !(endDate == null)) {
            return outBound.outBoundDate.between(startDate, endDate);
        }
        return null;
    }

    private BooleanExpression eqId(Long productId) {
        return productId == null ? null : outBound.product.id.eq(productId);
    }

    private BooleanExpression eqCategoryCode(String categoryCode) {
        return categoryCode == null ? null : outBound.product.categoryCode.eq(categoryCode);
    }
}
