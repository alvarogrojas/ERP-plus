package com.ndl.erp.repository;


import com.ndl.erp.domain.BillPayNotaCreditoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPayNotaCreditoDetailRepository extends JpaRepository<BillPayNotaCreditoDetail, Integer> {

}