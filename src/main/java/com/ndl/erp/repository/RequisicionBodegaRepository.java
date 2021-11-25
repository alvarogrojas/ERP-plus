package com.ndl.erp.repository;


import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.RequisicionBodega;
import com.ndl.erp.domain.RequisicionBodegaDetalle;
import com.ndl.erp.dto.DetailsDTO;
import com.ndl.erp.dto.InvoiceProductTotalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface RequisicionBodegaRepository extends JpaRepository<RequisicionBodega, Integer> {

    @Query(value = "select rb from RequisicionBodega rb where rb.id=?1 ")
    RequisicionBodega findRequisicionBodegaById(Integer requisicionId);


    @Query(value = "select rb from RequisicionBodega rb " +
            "where  ((?1='' or  ?1=null or rb.motivoSalida like %?1% ) or " +
            "(?1='' or  ?1=null or rb.bodega.name like %?1% ) or " +
            "(?2='' or  ?2=null or rb.consecutivo like %?2% ) or " +
            "(?3=null or  ?3=0 or rb.bodega.id = ?3 )) " +
            "and (?4= null or rb.fechaSolicitada >= ?4) " +
            "and (?5= null or rb.fechaSolicitada <= ?5) " +
            "and (?6= null or ?6= '' or rb.estado = ?6)")
    Page<RequisicionBodega> getFilterPageableRequisicionBodegaByConsecutivo(String filter,
                                                                            String consecutivo,
                                                                            Integer bodegaId,
                                                                            Date startFecha,
                                                                            Date endFecha,
                                                                            String estado,
                                                                            Pageable pageable);

    @Query(value = "select count(rb.id) from RequisicionBodega rb " +
            "where  ((?1='' or  ?1=null or rb.motivoSalida like %?1% ) or " +
            "(?1='' or  ?1=null or rb.bodega.name like %?1% ) or " +
            "(?2='' or  ?2=null or rb.consecutivo like %?2% ) or " +
            "(?3=null or  ?3=0 or rb.bodega.id = ?3 )) " +
            "and (?4= null or rb.fechaSolicitada >= ?4) " +
            "and (?5= null or rb.fechaSolicitada <= ?5) " +
            "and (?6= null or ?6= '' or rb.estado = ?6)")
    Integer countFilterPageableRequisicionBodegaByConsecutivo(String filter,
                                                              String consecutivo,
                                                              Integer bodegaId,
                                                              Date startFecha,
                                                              Date endFecha,
                                                              String estado);

    @Query(value = "select rb from RequisicionBodega rb " +
            "where " +
            "(rb.bodega.name like %?1% ) or " +
            "(rb.consecutivo like %?1% ) "
           )
    List<RequisicionBodega> getFilter(String filter);




    @Query(value = "select new com.ndl.erp.dto.InvoiceProductTotalDTO(rb.consecutivo,'REQUI', '', rb.id, rb.fechaSolicitada, sum(rbd.montoTotalDespachado)) " +
            "from RequisicionBodega rb, RequisicionBodegaDetalle rbd " +
            "where rb.id = rbd.requisicionBodega.id " +
            "and (?1= null or rb.fechaSolicitada >= ?1) " +
            "and (?2= null or rb.fechaSolicitada <= ?2) " +
            "and (?3=null or  ?3=0 or rbd.inventario.bodega.id = ?3 ) " +
            "and rb.estado = 'Aprobado' and rb.salidaBodega = false " +
            "group by rb.consecutivo, rb.id, rb.fechaSolicitada" )
    List<InvoiceProductTotalDTO> getRequisicionProductTotalByPayDates(Date startInvoiceDate, Date endInvoiceDate, Integer bodegaId);


    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('REQUI', " +
             "rqd.id, rqd.inventario.producto.codigo || ' - ' || rqd.inventario.producto.descripcionEspanol, " +
             "rqd.inventario.bodega.name, rq.motivoSalida, rqd.cantidadDespachada, rqd.precioUnidad, rqd.montoTotalDespachado," +
             "rqd.costCenter.code || ' - '|| rqd.costCenter.name) " +
             " from   RequisicionBodega rq, RequisicionBodegaDetalle rqd " +
             "where  rqd.requisicionBodega.id = ?1 " +
             "and rq.id = rqd.requisicionBodega.id and rq.salidaBodega = false")
    List<DetailsDTO> getRequisicionDetailsPendings(Integer id);

    @Query(value = "select coalesce(count(rq.consecutivo), null,0, count(rq.consecutivo)) from RequisicionBodega rq where rq.consecutivo = ?1")
    Integer  validarConsecutivoRequisicion(String consecutivo);

    @Query(value = "select new com.ndl.erp.domain.Devolucion(r) " +
            " from   RequisicionBodega r " +
            "where r.id = ?1 "
    )
    Devolucion getDevolucionFromRequisicion(Integer requisicionId);

    @Query(value = "select rbd from RequisicionBodegaDetalle rbd where rbd.id=?1 ")
    RequisicionBodegaDetalle findRequisicionBodegaDetalleById(Integer requisicionDetalleId);

}