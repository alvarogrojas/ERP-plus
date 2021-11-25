package com.ndl.erp.repository;


import com.ndl.erp.domain.CostsCenterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CostCenterTypeRepository extends JpaRepository<CostsCenterType, Integer> {


	@Query(value = "select ct.type from CostsCenterType ct")
    List<String> findAllType();
}
