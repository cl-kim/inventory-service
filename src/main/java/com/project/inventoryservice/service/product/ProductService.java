package com.project.inventoryservice.service.product;

import com.project.inventoryservice.api.product.dto.ProductResponseDto;
import com.project.inventoryservice.api.product.dto.ProductSaveRequestDto;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto save(ProductSaveRequestDto requestDto){
        Product entity = productRepository.save(requestDto.toEntity());

        return new ProductResponseDto(entity);
    }

    public Page<ProductResponseDto> getList(Pageable pageable){
        List<ProductResponseDto> results = productRepository.findAll(pageable).stream()
                .map(ProductResponseDto::new).collect(Collectors.toList());
        return new PageImpl<>(results);
    }

    public ProductResponseDto getProduct(Long productId) throws Exception {
        Optional<Product> entity = productRepository.findById(productId);
        if(!entity.isPresent()) {
            throw new Exception("잘못된 상품번호입니다.");
        }
        return new ProductResponseDto(entity.get());
    }


}
