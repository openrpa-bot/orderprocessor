package com.nigam.databasecontroller.repository;

import com.nigam.databasecontroller.entity.EquityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquityGroupRepository extends JpaRepository<EquityGroup, EquityGroup.EquityGroupId> {
}
