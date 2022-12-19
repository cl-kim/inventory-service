package com.project.inventoryservice.domain.instock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockRepository extends JpaRepository<InStock, Long>, InStockRepositoryCustom {
}
