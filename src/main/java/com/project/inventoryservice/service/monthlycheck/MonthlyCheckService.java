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
            } else {
                throw new RuntimeException("전월 마감이 필요합니다.");
            }
        } else {
            throw new RuntimeException("전월 마감이 필요합니다.");
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

    @Transactional
    public void saveEnd(LocalDate date) {
        if(monthlyCheckRepository.findByEndMonth(date).isPresent()){
            throw new RuntimeException("이미 마감된 월입니다.");
        }
        MonthlyCheck entity = MonthlyCheck.builder()
                .endMonth(date)
                .isEnd(true)
                .build();
        monthlyCheckRepository.save(entity);
    }

}
