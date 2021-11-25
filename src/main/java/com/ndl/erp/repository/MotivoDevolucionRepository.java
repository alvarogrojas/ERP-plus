package com.ndl.erp.repository;


import com.ndl.erp.domain.MotivoDevolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


    @Component
    public interface MotivoDevolucionRepository  extends JpaRepository<MotivoDevolucion, Integer> {

        @Query(value = "select md from MotivoDevolucion md where md.estado = 'Activo' ")
        List<MotivoDevolucion> getMotivoDevolucionActivo();

        @Query(value = "select md from MotivoDevolucion md where md.id = ?1")
        MotivoDevolucion findMotivoDevolucionById(Integer id);



    }