package com.project.inventoryservice.domain.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
}
