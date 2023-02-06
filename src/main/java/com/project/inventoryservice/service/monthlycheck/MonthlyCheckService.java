package com.project.inventoryservice.service.monthlycheck;

import com.project.inventoryservice.domain.monthlycheck.MonthlyCheck;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MonthlyCheckService {
    private final MonthlyCheckRepository monthlyCheckRepository;

    public Boolean checkMonthlyEnd(LocalDate date){
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date)
                .orElse(MonthlyCheck.builder().isEnd(false).build());
        return entity.getIsEnd();
    }

    public Boolean saveMonthlyEnd(LocalDate date) throws RuntimeException {
        LocalDate lastMonth = date.minusMonths(1L);
        if(!monthlyCheckRepository.findByEndMonth(lastMonth).isPresent()){
            throw new RuntimeException("전월 마감이 필요합니다.");
        }

        MonthlyCheck entity = MonthlyCheck.builder()
                .endMonth(date)
                .isEnd(true)
                .build();
        monthlyCheckRepository.save(entity);
        return true;
    }

    public Boolean cancelMonthlyEnd(LocalDate date) {
        MonthlyCheck entity =  monthlyCheckRepository.findByEndMonth(date).get();
        entity.updateEnd(false);
        return true;
    }

}
