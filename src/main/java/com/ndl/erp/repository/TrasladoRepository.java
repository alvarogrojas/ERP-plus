package com.ndl.erp.repository;

import com.ndl.erp.domain.Traslado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface TrasladoRepository extends JpaRepository<Traslado, Integer> {
    @Query(value = "select coalesce(count(tr.consecutivo), null,0, count(tr.consecutivo)) from Traslado tr where tr.consecutivo = ?1")
    Integer  validarConsecutivoTraslado(String consecutivo);

    @Query(value = "select tr from Traslado tr where tr.id=?1 ")
    Traslado findTrasladoById(Integer trasladoId);

    @Query(value= "select t from Traslado t where (?1 = null or ?1= '' or t.consecutivo like %?1%) " +
           "and  (?2 = '' or ?2 = null or t.estado = ?2)" )
    Page<Traslado> getFilterPageableTrasladoByConsecutivoAndEstado(String filter, String estado, Pageable pageable);

    @Query(value= "select  count(t.id) from Traslado t where (?1 = null or ?1= '' or t.consecutivo like %?1%) " +
            "and (?2 = '' or ?2 = null or t.estado = ?2)" )
    Integer countFilterPageableTrasladoByConsecutivoAndEstado(String filter, String estado);



}
