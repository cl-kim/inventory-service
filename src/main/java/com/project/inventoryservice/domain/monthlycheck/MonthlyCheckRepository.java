package com.project.inventoryservice.domain.monthlycheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MonthlyCheckRepository extends JpaRepository<MonthlyCheck, Long> {
    @Query(value = "SELECT * FROM monthly_check mc WHERE DATE_FORMAT(end_month, '%yy-%MM') = DATE_FORMAT(:date , '%yy-%MM')", nativeQuery = true)
    MonthlyCheck findByCloseMonth(@Param("date") LocalDate date);
}