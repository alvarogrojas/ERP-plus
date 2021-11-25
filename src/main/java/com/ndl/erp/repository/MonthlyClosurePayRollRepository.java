package com.ndl.erp.repository;


import com.ndl.erp.domain.MonthlyClosureBillPay;
import com.ndl.erp.domain.MonthlyClosurePayRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface MonthlyClosurePayRollRepository extends JpaRepository<MonthlyClosurePayRoll, Integer> {
    



}