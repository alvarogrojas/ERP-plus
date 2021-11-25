package com.ndl.erp.repository;

import com.ndl.erp.domain.CotizacionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CotizacionDetalleRepository extends JpaRepository<CotizacionDetalle, Integer> {

}