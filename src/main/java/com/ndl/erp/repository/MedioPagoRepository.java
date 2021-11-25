package com.ndl.erp.repository;


import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedioPagoRepository extends JpaRepository<MedioPago, Integer> {

    @Query(value = "select c from MedioPago c where estado='Activo'")
    List<MedioPago> findAllMedioPagosActivo();
}
