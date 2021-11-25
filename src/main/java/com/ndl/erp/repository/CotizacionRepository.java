package com.ndl.erp.repository;

import com.ndl.erp.domain.Cotizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    @Query(value = "select c from Cotizacion c " +
                   "where c.id = ?1 ")
    Cotizacion findCotizacionById(Integer id);

    @Query(value = "select c from Cotizacion c " +
            "where ((?1='' or  ?1=null or c.observaciones like %?1% ) or " +
            "(?1='' or  ?1=null or c.client.name like %?1% ) or " +
            "(?1='' or  ?1=null or c.cotizacionNumber like %?1% )) " +
            "and (?2=null or c.fechaEmision >= ?2) " +
            "and (?3=null or c.fechaEmision <= ?3) ")
    Page<Cotizacion> getFilterPageableCotizacionByFechaEmision(String filter,
                                                              Date startFecha,
                                                              Date endFecha,
                                                              Pageable pageable);

    @Query(value = "select count(c.id) from Cotizacion c " +
            "where ((?1='' or  ?1=null or c.observaciones like %?1% ) or " +
            "(?1='' or  ?1=null or c.client.name like %?1% ) or " +
            "(?1='' or  ?1=null or c.cotizacionNumber like %?1% )) " +
            "and (?2=null or c.fechaEmision >= ?2) " +
            "and (?3=null or c.fechaEmision <= ?3) ")
    Integer countAllFilterPageableCotizacionByFechaEmision(String filter,
                                                          Date startFecha,
                                                          Date endFecha);


    @Query(value = "select coalesce(count(c.id), null, 0, count(c.id))  from Cotizacion c " +
            "where c.cotizacionNumber = ?1 ")
    Integer countAllCotizacionByCotizacionNumber(String cotizacionNumber);


    @Query(value = "select c from Cotizacion c " +
            "where  c.fechaVencimiento >= ?1 " +
            "and c.fechaVencimiento <= ?2 " +
            "and c.fechaVencimiento <= current_date() " +
            "and c.estado  not in ('Vencida') ")
    List<Cotizacion> getCotizacionByFechaVencimiento(Date startFecha,
                                                     Date endFecha);

}