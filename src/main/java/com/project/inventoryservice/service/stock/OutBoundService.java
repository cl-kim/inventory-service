package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.outbound.dto.OutBoundResponseDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundSaveRequestDto;
import com.project.inventoryservice.api.outbound.dto.OutBoundUpdateRequestDto;
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
public class OutBoundService {

    private final InventoryRepository inventoryRepository;

    private final ProductRepository productRepository;

    private final ClosingService closingService;

    public Page<OutBoundResponseDto> getPage(Pageable pageable, Long productId, String categoryCode, LocalDate startDate, LocalDate endDate) {
        List<OutBoundResponseDto> results = inventoryRepository.findOutBoundPage(pageable, productId, categoryCode, startDate, endDate);
        long count = inventoryRepository.count();

        return new PageImpl<>(results, pageable, count);
    }

    @Transactional
    public OutBoundResponseDto save(OutBoundSaveRequestDto requestDto) {
        checkMonthlyEnd(requestDto.getOutBoundDate());

        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        Inventory entity = inventoryRepository.save(requestDto.toEntity(product));

        return new OutBoundResponseDto(entity);
    }

    @Transactional
    public OutBoundResponseDto update(Long outStockId, OutBoundUpdateRequestDto requestDto) {
        checkMonthlyEnd(requestDto.getOutBoundDate());

        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        Inventory entity = findOutBound(outStockId);

        entity.updateOutBound(product, requestDto.getQuantity(), requestDto.getOutBoundDate(),requestDto.getPrice(), requestDto.getCustomer(), requestDto.getMemo());
        return new OutBoundResponseDto(entity);
    }

    @Transactional
    public void delete(Long outStockId) {
        Inventory entity = findOutBound(outStockId);
        checkMonthlyEnd(entity.getDate());

        inventoryRepository.delete(entity);
    }

    private Inventory findOutBound(Long outBoundId) {
        return inventoryRepository.findById(outBoundId)
                .orElseThrow(() -> new EntityNotFoundException("해당 내역을 찾을 수 없습니다."));
    }

    private void checkMonthlyEnd(LocalDate endDate){
        if(closingService.checkMonthlyClosing(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), 1))){
            throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "마감된 월입니다.");
        }
    }
}
