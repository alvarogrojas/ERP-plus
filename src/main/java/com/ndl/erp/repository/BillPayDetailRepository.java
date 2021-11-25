package com.ndl.erp.repository;

import com.ndl.erp.domain.BillPayDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface BillPayDetailRepository extends JpaRepository<BillPayDetail, Integer> {


    @Query(value = "select d from BillPayDetail d where d.billPay.id = ?1 and d.id = ?2")
    BillPayDetail getBillPayDetailByBillPayAndId(Integer billPayId, Integer detailId);




}