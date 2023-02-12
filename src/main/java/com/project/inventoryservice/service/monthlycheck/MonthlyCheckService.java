package com.project.inventoryservice.service.monthlycheck;

import com.project.inventoryservice.domain.monthlycheck.MonthlyCheck;
import com.project.inventoryservice.domain.monthlycheck.MonthlyCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Transactional
public class MonthlyCheckService {
    private final MonthlyCheckRepository monthlyCheckRepository;

    public Boolean checkMonthlyEnd(LocalDate date){
        MonthlyCheck entity = monthlyCheckRepository.findByEndMonth(date)
                .orElse(MonthlyCheck.builder().isEnd(false).build());
        return entity.getIsEnd();
    }

    public Boolean saveMonthlyEnd(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);
        if(!monthlyCheckRepository.findByEndMonth(lastMonth).isPresent()){
            throw new RuntimeException("전월 마감이 필요합니다.");
        }
        saveEnd(date);
        return true;
    }

    public Boolean saveFirstMonthlyEnd(LocalDate date) {
        LocalDate lastMonth = date.minusMonths(1L);
        saveEnd(lastMonth);
        return true;
    }

    public Boolean cancelMonthlyEnd(LocalDate date) {
        MonthlyCheck entity =  monthlyCheckRepository.findByEndMonth(date).get();
        entity.updateEnd(false);
        return true;
    }

    public void saveEnd(LocalDate date){
        MonthlyCheck entity = MonthlyCheck.builder()
                .endMonth(date)
                .isEnd(true)
                .build();
        monthlyCheckRepository.save(entity);
    }

}
