package com.project.inventoryservice.domain.outstock;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.project.inventoryservice.domain.outstock.QOutStock.outStock;
import static com.project.inventoryservice.domain.product.QProduct.product;

@RequiredArgsConstructor
public class OutStockRepositoryImpl implements OutStockRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutStock> findList(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(outStock)
                .from(outStock)
                .leftJoin(outStock.product, product)
                .where(eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate,endDate))
                .orderBy(outStock.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return outStock.outStockDate.between(startDate,endDate);
        }
        return null;
    }

    private BooleanExpression eqId(Long productId){
        return productId == null ? null : outStock.product.id.eq(productId);
    }

    private BooleanExpression eqCategoryCode(String categoryCode){
        return categoryCode == null ? null : outStock.product.categoryCode.eq(categoryCode);
    }
}
