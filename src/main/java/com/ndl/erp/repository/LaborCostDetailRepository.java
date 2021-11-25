package com.ndl.erp.repository;


import com.ndl.erp.domain.LaborCost;
import com.ndl.erp.domain.LaborCostDetail;
import com.ndl.erp.dto.HmHdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface LaborCostDetailRepository extends JpaRepository<LaborCostDetail, Integer> {





}
