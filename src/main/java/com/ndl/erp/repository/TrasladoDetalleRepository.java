package com.ndl.erp.repository;

import com.ndl.erp.domain.TrasladoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TrasladoDetalleRepository extends JpaRepository<TrasladoDetalle, Integer> {
}

