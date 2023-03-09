package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.inbound.dto.InBoundResponseDto;
import com.project.inventoryservice.api.inbound.dto.InBoundSaveRequestDto;
import com.project.inventoryservice.api.inbound.dto.InBoundUpdateRequestDto;
import com.project.inventoryservice.common.exception.BusinessException;
import com.project.inventoryservice.common.exception.dto.ErrorCode;
import com.project.inventoryservice.domain.inventory.Inventory;
import com.project.inventoryservice.domain.inventory.InventoryRepository;
import com.project.inventoryservice.domain.product.Product;
import com.project.inventoryservice.domain.product.ProductRepository;
import com.project.inventoryservice.service.closing.ClosingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class InBoundService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final ClosingService closingService;

    public Page<InBoundResponseDto> getPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<InBoundResponseDto> results = inventoryRepository.findInBoundPage(pageable, productId, categoryCode, startDate, endDate);
        long count = inventoryRepository.count();

        return new PageImpl<>(results, pageable, count);
    }

    @Transactional
    public InBoundResponseDto save(InBoundSaveRequestDto requestDto) {
        checkMonthlyEnd(requestDto.getInBoundDate());

        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        Inventory entity = inventoryRepository.save(requestDto.toEntity(product));

        return new InBoundResponseDto(entity);
    }

    @Transactional
    public InBoundResponseDto update(Long inStockId, InBoundUpdateRequestDto requestDto) {
        checkMonthlyEnd(requestDto.getInBoundDate());

        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        Inventory entity = findInStock(inStockId);
        entity.updateInBound(product, requestDto.getQuantity(), requestDto.getInBoundDate(), requestDto.getMemo());
        return new InBoundResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        Inventory entity = findInStock(id);
        checkMonthlyEnd(entity.getDate());

        inventoryRepository.delete(entity);
    }

    private Inventory findInStock(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 내역을 찾을 수 없습니다."));
    }

    private void checkMonthlyEnd(LocalDate endDate){
        if(closingService.checkMonthlyClosing(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), 1))){
            throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "마감된 월입니다.");
        };
    }

}
