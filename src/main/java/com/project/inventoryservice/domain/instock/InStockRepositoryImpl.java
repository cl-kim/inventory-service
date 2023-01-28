package com.project.inventoryservice.domain.instock;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


import static com.project.inventoryservice.domain.instock.QInStock.inStock;
import static com.project.inventoryservice.domain.product.QProduct.product;

@RequiredArgsConstructor
public class InStockRepositoryImpl implements InStockRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InStock> findPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(inStock)
                .from(inStock)
                .leftJoin(inStock.product, product)
                .where(eqId(productId),
                        eqCategoryCode(categoryCode),
                        betweenDate(startDate,endDate))
                .orderBy(inStock.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate == null) && !(endDate == null)){
            return inStock.inStockDate.between(startDate,endDate);
        }
        LocalDate today = LocalDate.now();
        return inStock.inStockDate.between(today.minusMonths(1), today);
    }

    private BooleanExpression eqId(Long productId){
        return productId == null ? null : inStock.product.id.eq(productId);
    }

    private BooleanExpression eqCategoryCode(String categoryCode){
        return categoryCode == null ? null : inStock.product.categoryCode.eq(categoryCode);
    }
}
