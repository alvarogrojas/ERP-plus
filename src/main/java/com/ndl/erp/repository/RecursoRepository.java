package com.ndl.erp.repository;


import com.ndl.erp.domain.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecursoRepository extends JpaRepository<Recurso, Integer> {

    List<Recurso> findByReferenciaIdAndTipoReferencia(Integer referenciaId, String tipoReferencia );

    Recurso getById(Integer id);

}
