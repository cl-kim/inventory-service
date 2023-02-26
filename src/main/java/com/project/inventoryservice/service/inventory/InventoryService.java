package com.project.inventoryservice.service.inventory;

import com.project.inventoryservice.common.exception.BusinessException;
import com.project.inventoryservice.common.exception.dto.ErrorCode;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheck;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Transactional
public class InventoryService {
    private final MonthlyCheckRepository monthlyCheckRepository;

    public Boolean checkIsNew(){
        return monthlyCheckRepository.findAll().isEmpty();
    }

    public Boolean checkMonthlyEnd(LocalDate date) {
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date)
                .orElse(MonthlyCheck.builder().isEnd(false).build());
        return entity.getIsEnd();
    }

    @Transactional
    public Boolean saveMonthlyEnd(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);

        // 전월 마감이 존재하고
        if (monthlyCheckRepository.findByEndMonth(lastMonth).isPresent()) {
            // 전월 마감이 true일때만 이번달 마감이 가능하다.
            if (monthlyCheckRepository.findByEndMonth(lastMonth).get().getIsEnd()) {
                saveEnd(date);
                saveMonthlyInventory(date);
            } else {
                throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "전월 마감이 필요합니다.");
            }
        } else {
            throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "전월 마감이 필요합니다.");
        }
        return true;
    }

    @Transactional
    public Boolean saveFirstMonthlyEnd(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);
        saveEnd(lastMonth);
        return true;
    }

    @Transactional
    public Boolean cancelMonthlyEnd(LocalDate date) {
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date).get();
        monthlyCheckRepository.delete(entity);
        return true;
    }

    public void saveEnd(LocalDate date) {
        if(monthlyCheckRepository.findByEndMonth(date).isPresent()){
            throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "이미 마감된 월입니다.");
        }
        MonthlyCheck entity = MonthlyCheck.builder()
                .endMonth(date)
                .isEnd(true)
                .build();
        monthlyCheckRepository.save(entity);
    }

    @Transactional
    public void saveMonthlyInventory(LocalDate date){
        // 해당 날짜 재고 조회
//        List<MonthlyInventory> list = monthlyService.find() ;
    }

}
