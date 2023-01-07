package com.project.inventoryservice.service.product;

import com.project.inventoryservice.api.product.dto.ProductResponseDto;
import com.project.inventoryservice.api.product.dto.ProductSaveRequestDto;
import com.project.inventoryservice.api.product.dto.ProductUpdateRequestDto;
import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductResponseDto> getList(String categoryCode, Pageable pageable){
        List<ProductResponseDto> results = productRepository.findProduct(categoryCode, pageable).stream()
                .map(ProductResponseDto::new).collect(Collectors.toList());
        long total = productRepository.count();
        return new PageImpl<>(results, pageable, total);
    }

    public ProductResponseDto getProduct(Long productId) {
        Product entity = findProduct(productId);
        return new ProductResponseDto(entity);
    }

    @Transactional
    public ProductResponseDto save(ProductSaveRequestDto requestDto){
        String categoryCode = Category.findByName(requestDto.getCategoryName()).getKey();

        Product entity =  Product.builder()
                .categoryCode(categoryCode)
                .productCode(getNewProductCode(categoryCode))
                .productName(requestDto.getProductName())
                .productUnit(requestDto.getProductUnit())
                .amount(requestDto.getAmount())
                .price(requestDto.getPrice())
                .productStatus(requestDto.getProductStatus())
                .memo(requestDto.getMemo())
                .build();

        Product savedEntity = productRepository.save(entity);

        return new ProductResponseDto(savedEntity);
    }

    @Transactional
    public ProductResponseDto update(Long productId, ProductUpdateRequestDto requestDto){
        Product entity = findProduct(productId);
        String categoryCode = Category.findByName(requestDto.getCategoryName()).getKey();
        entity.update(categoryCode, requestDto.getProductName(), requestDto.getProductUnit(),
                requestDto.getAmount(), requestDto.getPrice(), requestDto.getProductStatus(), requestDto.getMemo());

        return new ProductResponseDto(entity);
    }
    @Transactional
    public Long updateStatus(Long productId, String status){
        Product entity = findProduct(productId);
        entity.updateStatus(status);

        return entity.getId();
    }

    private Product findProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("잘못된 상품번호입니다."));
    }

    private String getNewProductCode(String categoryCode){
        Long id = productRepository.countByCategoryCode(categoryCode) + 1;
        String code = Category.findByKey(categoryCode).name().substring(0,1);
        return code + String.format("%06d", id);
    }

}
