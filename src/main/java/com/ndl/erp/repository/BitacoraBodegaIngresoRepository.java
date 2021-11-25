package com.ndl.erp.repository;


import com.ndl.erp.domain.BitacoraBodegaIngreso;
import com.ndl.erp.domain.BodegaIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface BitacoraBodegaIngresoRepository extends JpaRepository<BitacoraBodegaIngreso, Integer> {


}
