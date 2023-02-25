package com.project.inventoryservice.domain.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoundRepository extends JpaRepository<OutBound, Long>, OutBoundRepositoryCustom {


}
