package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.outstock.dto.OutStockResponseDto;
import com.project.inventoryservice.api.outstock.dto.OutStockSaveRequestDto;
import com.project.inventoryservice.api.outstock.dto.OutStockUpdateRequestDto;
import com.project.inventoryservice.domain.outstock.OutStock;
import com.project.inventoryservice.domain.outstock.OutStockRepository;
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
public class OutStockService {

    private final OutStockRepository outStockRepository;

    private final ProductRepository productRepository;

    public Page<OutStockResponseDto> getPage(Pageable pageable, Long productId, LocalDate startDate, LocalDate endDate) {
        List<OutStock> results = outStockRepository.findPage(pageable, productId, startDate, endDate);
        List<OutStockResponseDto> resultsDto = results.stream().map(OutStockResponseDto::new).collect(Collectors.toList());
        long count = outStockRepository.count();

        return new PageImpl<>(resultsDto, pageable, count);
    }

    @Transactional
    public OutStockResponseDto save(OutStockSaveRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        OutStock entity = outStockRepository.save(requestDto.toEntity(product));

        return new OutStockResponseDto(entity);
    }

    @Transactional
    public OutStockResponseDto update(Long outStockId, OutStockUpdateRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        OutStock entity = findInStock(outStockId);
        entity.update(product, requestDto.getOutStockDate(), requestDto.getCustomer(), requestDto.getPrice(), requestDto.getQuantity(), requestDto.getMemo());
        return new OutStockResponseDto(entity);
    }

    @Transactional
    public void delete(Long outStockId) {
        OutStock entity = findInStock(outStockId);
        outStockRepository.delete(entity);
    }

    private OutStock findInStock(Long outStockId) {
        return outStockRepository.findById(outStockId)
                .orElseThrow(() -> new EntityNotFoundException("해당 내역을 찾을 수 없습니다."));
    }
}
