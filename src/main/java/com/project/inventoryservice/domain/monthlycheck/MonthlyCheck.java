package com.project.inventoryservice.domain.monthlycheck;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class MonthlyCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  마감월
     */
    private LocalDate endMonth;

    /**
     *  마감 여부
     */
    private Boolean isEnd;

    @Builder
    public MonthlyCheck(LocalDate endMonth, Boolean isEnd) {
        this.endMonth = endMonth;
        this.isEnd = isEnd;
    }

    public MonthlyCheck updateEnd(Boolean isEnd){
        this.isEnd = isEnd;
        return this;
    }
}
