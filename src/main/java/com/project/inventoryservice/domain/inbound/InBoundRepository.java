package com.project.inventoryservice.domain.inbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InBoundRepository extends JpaRepository<InBound, Long>, InBoundRepositoryCustom {
}
