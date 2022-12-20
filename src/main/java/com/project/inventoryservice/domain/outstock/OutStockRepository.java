package com.project.inventoryservice.domain.outstock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutStockRepository extends JpaRepository<OutStock, Long>, OutStockRepositoryCustom {


}
