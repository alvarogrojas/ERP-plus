package com.ndl.erp.repository;

import com.ndl.erp.domain.DevolucionDetalle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionDetalleRepository extends CrudRepository<DevolucionDetalle, Integer> {
}