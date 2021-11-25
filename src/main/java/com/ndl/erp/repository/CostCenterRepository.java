package com.ndl.erp.repository;

import com.ndl.erp.domain.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CostCenterRepository extends JpaRepository<CostCenter, Integer> {
    @Query(value = "select cc from CostCenter cc where cc.code = ?1")
    CostCenter findCostCenterByCode(String code);

    @Query(value = "select cc from CostCenter cc where cc.id = ?1")
    CostCenter findCostCenterByid(Integer id);

}
