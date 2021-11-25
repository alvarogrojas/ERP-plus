package com.ndl.erp.repository;

import com.ndl.erp.domain.InvoiceNotaCreditoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceNotaCreditoDetailRepository extends JpaRepository<InvoiceNotaCreditoDetail, Integer> {

}
