package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundSaveRequestDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundUpdateRequestDto;
import com.project.inventoryservice.common.exception.BusinessException;
import com.project.inventoryservice.common.exception.dto.ErrorCode;
import com.project.inventoryservice.domain.outbound.OutBound;
import com.project.inventoryservice.domain.outbound.OutBoundRepository;
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
public class OutBoundService {

    private final OutBoundRepository outBoundRepository;

    private final ProductRepository productRepository;

    public Page<OutBoundResponseDto> getPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<OutBound> results = outBoundRepository.findList(pageable, productId, categoryCode, startDate, endDate);
        List<OutBoundResponseDto> resultsDto = results.stream().map(OutBoundResponseDto::new).collect(Collectors.toList());
        long count = outBoundRepository.count();

        return new PageImpl<>(resultsDto, pageable, count);
    }

    @Transactional
    public OutBoundResponseDto save(OutBoundSaveRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        OutBound entity = outBoundRepository.save(requestDto.toEntity(product));

        return new OutBoundResponseDto(entity);
    }

    @Transactional
    public OutBoundResponseDto update(Long outStockId, OutBoundUpdateRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        OutBound entity = findInStock(outStockId);
        entity.update(product, requestDto.getOutBoundDate(), requestDto.getCustomer(), requestDto.getPrice(), requestDto.getQuantity(), requestDto.getMemo());
        return new OutBoundResponseDto(entity);
    }

    @Transactional
    public void delete(Long outStockId) {
        OutBound entity = findInStock(outStockId);
        outBoundRepository.delete(entity);
    }

    private OutBound findInStock(Long outStockId) {
        return outBoundRepository.findById(outStockId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE,"해당 내역을 찾을 수 없습니다."));
    }
}
