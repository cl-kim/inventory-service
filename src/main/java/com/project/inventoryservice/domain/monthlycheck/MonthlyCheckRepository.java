package com.project.inventoryservice.domain.monthlycheck;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MonthlyCheckRepository extends JpaRepository<MonthlyCheck, Long> {
    Optional<MonthlyCheck> findByEndMonth(LocalDate date);
}