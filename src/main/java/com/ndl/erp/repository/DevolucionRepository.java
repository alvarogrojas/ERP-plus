package com.ndl.erp.repository;
import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.DevolucionDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DevolucionRepository extends CrudRepository<Devolucion, Integer> {


    @Query(value = "select d from Devolucion d where d.id = ?1")
    Devolucion findDevolucionById(Integer id);

    @Query(value = "select d from Devolucion d where d.referenciaId = ?1 and d.tipo = ?2")
    Devolucion getDevolucionByReferenciaIdAndType(Integer referenciaId, String type);

    @Query(value = "select d from Devolucion d where d.referenciaId = ?1")
    Devolucion getDevolucionByReferenciaId(Integer id);

    @Query(value = "select dev from Devolucion dev where dev.fechaDevolucion>=?1 and dev.fechaDevolucion<=?2  " +
            "and (?3=null or  ?3='' or dev.estado like %?3%)  " +
            "order by dev.fechaDevolucion  desc")
    Page<Devolucion> getPageableDevolucionByDateAndEstado(
            Date start, Date end, String status, Pageable pageable);
    @Query(value = "select count(dev) from Devolucion dev where dev.fechaDevolucion>=?1 and dev.fechaDevolucion<=?2  " +
            "and (?3=null or  ?3='' or dev.estado like %?3%)  " +
            "order by dev.fechaDevolucion  desc")
    Integer countAllDevolucionByDateAndEstado(
            Date start, Date end, String status);

    @Query(value = "select d from DevolucionDetalle d where d.devolucion.id = ?1 and id = ?2 ")
    DevolucionDetalle getDevolucionDetalleByIdAndDevolucionId(Integer devolucionId, Integer devolucionDetalleid);

    @Query(value = "select count(d) from DevolucionDetalle d where d.devolucion.id = ?1 and d.ingresadoBodega = false")
    Integer countDevolucionDetalleByDevolucionId(Integer devolucionId);


}
