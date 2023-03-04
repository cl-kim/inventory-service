package com.project.inventoryservice.service.closing;

import com.project.inventoryservice.common.exception.BusinessException;
import com.project.inventoryservice.common.exception.dto.ErrorCode;
import com.project.inventoryservice.domain.monthlyInventory.MonthlyInventoryRepository;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheck;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheckRepository;
import com.project.inventoryservice.service.stock.MonthlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * 마감 여부 확인 및 처리 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ClosingService {
    private final MonthlyCheckRepository monthlyCheckRepository;

    private final MonthlyInventoryRepository monthlyInventoryRepository;

    private final MonthlyService monthlyService;

    /**
     * 마감 여부 확인
     */
    public Boolean checkMonthlyClosing(LocalDate date) {
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date)
                .orElse(MonthlyCheck.builder().isEnd(false).build());
        return entity.getIsEnd();
    }

    /**
     * 마감 처리
     */
    @Transactional
    public Boolean saveMonthlyClosing(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);

        // 전월 마감이 존재하고
        if (monthlyCheckRepository.findByEndMonth(lastMonth).isPresent()) {
            // 전월 마감이 true일때만 이번달 마감이 가능하다.
            if (monthlyCheckRepository.findByEndMonth(lastMonth).get().getIsEnd()) {
                saveClosing(date);
                saveMonthlyInventory(date);
            } else {
//                throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "전월 마감이 필요합니다.");
            }
        } else {
//            throw new BusinessException(ErrorCode.BUSINESS_CUSTOM_MESSAGE, "전월 마감이 필요합니다.");
        }
        return true;
    }

    /**
     * 최초 마감 처리
     */
    @Transactional
    public Boolean saveFirstMonthlyClosing(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);
        saveClosing(lastMonth);
        return true;
    }

    /**
     * 마감 취소 처리
     */
    @Transactional
    public Boolean cancelMonthlyClosing(LocalDate date) {
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date).get();
        monthlyCheckRepository.delete(entity);
        return true;
    }

    public void saveClosing(LocalDate date) {
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
        //
    }

}
