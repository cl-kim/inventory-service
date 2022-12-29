package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.instock.dto.InStockResponseDto;
import com.project.inventoryservice.api.instock.dto.InStockSaveRequestDto;
import com.project.inventoryservice.api.instock.dto.InStockUpdateRequestDto;
import com.project.inventoryservice.domain.instock.InStock;
import com.project.inventoryservice.domain.instock.InStockRepository;
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
public class InStockService {
    private final InStockRepository inStockRepository;

    private final ProductRepository productRepository;

    public Page<InStockResponseDto> getList(Pageable pageable, Long ProductId, LocalDate startDate, LocalDate endDate) {
        List<InStock> results = inStockRepository.findPage(pageable, ProductId, startDate, endDate);
        List<InStockResponseDto> resultsDto = results.stream().map(InStockResponseDto::new).collect(Collectors.toList());
        long count = inStockRepository.count();

        return new PageImpl<>(resultsDto, pageable, count);
    }

    @Transactional
    public InStockResponseDto save(InStockSaveRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        InStock entity = inStockRepository.save(requestDto.toEntity(product));

        return new InStockResponseDto(entity);
    }

    @Transactional
    public InStockResponseDto update(Long inStockId, InStockUpdateRequestDto requestDto) {
        InStock entity = findInStock(inStockId);
        entity.update(requestDto.getInStockDate(), requestDto.getQuantity(), requestDto.getMemo());
        return new InStockResponseDto(entity);
    }

    @Transactional
    public void delete(Long inStockId) {
        InStock entity = findInStock(inStockId);
        inStockRepository.delete(entity);
    }

    private InStock findInStock(Long inStockId) {
        return inStockRepository.findById(inStockId)
                .orElseThrow(() -> new EntityNotFoundException("해당 내역을 찾을 수 없습니다."));
    }

}
