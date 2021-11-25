package com.ndl.erp.repository;

import com.ndl.erp.domain.Traslado;
import com.ndl.erp.domain.TrasladoJustificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TrasladoJustificacionRepository extends JpaRepository<TrasladoJustificacion, Integer> {

    @Query(value= "select t from TrasladoJustificacion t where t.estado = ?1" )
    List<TrasladoJustificacion> getByEstado(String estado);
}


