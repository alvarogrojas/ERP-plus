package com.ndl.erp.repository;

import com.ndl.erp.domain.CotizacionHistoricoVersionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CotizacionHistoricoVersionDetalleRepository extends JpaRepository<CotizacionHistoricoVersionDetalle, Integer> {

}