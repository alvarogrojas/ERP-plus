package com.ndl.erp.repository;

import com.ndl.erp.domain.InvoiceMedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface InvoiceMedioPagoRepository extends JpaRepository<InvoiceMedioPago, Integer> {

}
