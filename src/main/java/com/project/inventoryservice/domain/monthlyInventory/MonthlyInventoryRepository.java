package com.project.inventoryservice.domain.monthlyInventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyInventoryRepository extends JpaRepository<MonthlyInventory, Long>, MonthlyInventoryRepositoryCustom {

//    @Query(value = "select b.product_name as productName, sum(quantity) as quantity from(select product_id, quantity * -1 as quantity from out_stock union all select product_id, quantity from in_stock union all select product_id, quantity from monthly_stock where DATE_FORMAT(monthly_date,'%Y-%m') = DATE_FORMAT(:date,'2023-01') ) a join product b on b.id = a.product_id group by a.product_id", nativeQuery = true)
//    List<StockResponseInterface> findLastStock(@Param("date") LocalDate lastMonth);

//    @Query(nativeQuery = true, value = "select product_name as productName, sum(quantity) as quantity from (select product_id, sum(quantity) from in_stock where DATE_FORMAT())")
//    List<StockResponseInterface> getLiveStock(@Param("date") LocalDate date);

    List<MonthlyInventory> findByMonthlyDate(LocalDate date);
}
