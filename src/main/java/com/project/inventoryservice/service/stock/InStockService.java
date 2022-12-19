package com.project.inventoryservice.service.stock;

import com.project.inventoryservice.api.instock.dto.InStockResponseDto;
import com.project.inventoryservice.api.instock.dto.InStockSaveRequestDto;
import com.project.inventoryservice.api.instock.dto.InStockUpdateRequestDto;
import com.project.inventoryservice.domain.instock.InStock;
import com.project.inventoryservice.domain.instock.InStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class InStockService {
    private final InStockRepository inStockRepository;

    public Page<InStockResponseDto> getList(Pageable pageable, Long ProductId, LocalDate startDate, LocalDate endDate) {
        return inStockRepository.findPage(pageable, ProductId, startDate, endDate);
    }

    @Transactional
    public InStockResponseDto save(InStockSaveRequestDto requestDto) {
        InStock entity = inStockRepository.save(requestDto.toEntity());

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
