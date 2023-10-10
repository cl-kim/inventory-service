package com.project.inventoryservice.domain.monthlyInventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyInventoryRepository extends JpaRepository<MonthlyInventory, Long>, MonthlyInventoryRepositoryCustom {

    List<MonthlyInventory> findByMonthlyDate(LocalDate date);
}
