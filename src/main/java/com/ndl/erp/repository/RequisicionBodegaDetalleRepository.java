package com.ndl.erp.repository;

import com.ndl.erp.domain.RequisicionBodegaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

    @Component
    public interface RequisicionBodegaDetalleRepository extends JpaRepository<RequisicionBodegaDetalle, Integer> {

    }
