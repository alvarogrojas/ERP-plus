package com.ndl.erp.repository;


import com.ndl.erp.domain.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {


    @Query(value = "select  t from Terminal t " +
            "where t.id in (Select tu.terminal.id from TerminalUsuario tu where tu.estado = 'Cerrada'  and tu.usuario.id = ?1) ")
    Terminal findTerminalDisponibleByUsuario(Integer usuarioId);

    @Query(value = "select  t from Terminal t " +
            "where (?1 = null or ?1 = '' or t.estado = ?1)  " +
            " and (?2 = null or  ?2 = 0 or t.bodega.id = ?2 )"
            )
    List<Terminal> getTerminalesByEstadoAndBodega(String estado,Integer bodegaId);

    Terminal getById(Integer id);
}


