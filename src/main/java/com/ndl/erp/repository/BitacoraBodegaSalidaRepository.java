package com.ndl.erp.repository;


import com.ndl.erp.domain.BitacoraBodegaSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BitacoraBodegaSalidaRepository extends JpaRepository<BitacoraBodegaSalida, Integer> {



}
