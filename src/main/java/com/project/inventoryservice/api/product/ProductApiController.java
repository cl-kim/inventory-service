package com.project.inventoryservice.api.product;

import com.project.inventoryservice.api.product.dto.ProductResponseDto;
import com.project.inventoryservice.api.product.dto.ProductSaveRequestDto;
import com.project.inventoryservice.api.product.dto.ProductUpdateRequestDto;
import com.project.inventoryservice.domain.product.Category;
import com.project.inventoryservice.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    @Operation(summary = "카테고리 조회", description = "카테고리 명 리스트을 조회합니다.")
    @GetMapping("/categories")
    public List<String> getCategories() {
        return Arrays.stream(Category.values())
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    @Operation(summary = "상품 리스트 조회", description = "상품 페이지 리스트을 조회합니다.")
    @GetMapping("/products")
    public Page<ProductResponseDto> getProductList(Pageable pageable) {
        return productService.getList(pageable);
    }

    @Operation(summary = "상품 조회", description = "상품 코드로 상품을 조회합니다.")
    @GetMapping("/products/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @Operation(summary = "상품 저장")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto save(@RequestBody ProductSaveRequestDto requestDto) {
        return productService.save(requestDto);
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/products/{productId}")
    public ProductResponseDto update(@PathVariable Long productId, @RequestBody ProductUpdateRequestDto requestDto) {
        return productService.update(productId, requestDto);
    }

    @Operation(summary = "상품 판매 상태 수정")
    @PutMapping("/products/{productId}/{status}")
    public Long updateStatus(@PathVariable Long productId, @PathVariable String status) {
        return productService.updateStatus(productId, status);
    }

}
