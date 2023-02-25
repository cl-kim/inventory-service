package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.inbound.dto.InBoundSaveRequestDto;
import com.project.inventoryservice.api.inbound.dto.InBoundUpdateRequestDto;
import com.project.inventoryservice.common.exception.BusinessException;
import com.project.inventoryservice.common.exception.dto.ErrorCode;
import com.project.inventoryservice.domain.inbound.InBound;
import com.project.inventoryservice.domain.inbound.InBoundRepository;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class InBoundService {
    private final InBoundRepository inBoundRepository;

    private final ProductRepository productRepository;

    public Page<InBoundResponseDto> getPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<InBound> results = inBoundRepository.findPage(pageable, productId, categoryCode, startDate, endDate);
        List<InBoundResponseDto> resultsDto = results.stream()
                .map(InBoundResponseDto::new)
                .collect(Collectors.toList());
        long count = inBoundRepository.count();

        return new PageImpl<>(resultsDto, pageable, count);
    }

    @Transactional
    public InBoundResponseDto save(InBoundSaveRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        InBound entity = inBoundRepository.save(requestDto.toEntity(product));

        return new InBoundResponseDto(entity);
    }

    @Transactional
    public InBoundResponseDto update(Long inStockId, InBoundUpdateRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        InBound entity = findInStock(inStockId);
        entity.update(product, requestDto.getInBoundDate(), requestDto.getQuantity(), requestDto.getMemo());
        return new InBoundResponseDto(entity);
    }

    @Transactional
    public void delete(Long inStockId) {
        InBound entity = findInStock(inStockId);
        inBoundRepository.delete(entity);
    }

    private InBound findInStock(Long inStockId) {
        return inBoundRepository.findById(inStockId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE,"해당 내역을 찾을 수 없습니다."));
    }

}
