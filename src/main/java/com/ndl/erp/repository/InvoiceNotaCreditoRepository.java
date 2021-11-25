package com.ndl.erp.repository;

import com.ndl.erp.domain.BillCollectDetail;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.domain.InvoiceNotaCreditoDetail;
import com.ndl.erp.dto.DetailsDTO;
import com.ndl.erp.dto.ProductsPendingReturnTotalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface InvoiceNotaCreditoRepository extends JpaRepository<InvoiceNotaCredito, Integer> {

    @Query(value = "select inc from InvoiceNotaCredito inc  where inc.id = ?1")
    public InvoiceNotaCredito findByInvoiceNotaCreditoById(Integer id);

    @Query(value = "select inc from InvoiceNotaCredito inc where inc.date>=?1 and inc.date<=?2  " +
            "and (?3=null or  ?3='' or inc.status like %?3%)  " +
            "order by inc.date  desc")
    Page<InvoiceNotaCredito> getPageableInvoiceNotaCreditoByDateAndStatus(
            Date start, Date end, String status, Pageable pageable);

    @Query(value = "select count(inc.id) from InvoiceNotaCredito inc where inc.date>=?1 and inc.date<=?2  " +
            "and (?3=null or  ?3='' or inc.status like %?3%)  " +
            "order by inc.date  desc")
    Integer  countAllInvoiceNotaCreditoByDateAndStatus(
            Date start, Date end, String status);

//Carga del DTO con los totales por documento de articulos por retornar de una Nota de Credito
@Query(value = "select new com.ndl.erp.dto.ProductsPendingReturnTotalDTO(inc.number, inc.client.name, inc.id,'DEV-NC',  inc.date, sum(incd.total)) " +
        " from  InvoiceNotaCredito inc, InvoiceNotaCreditoDetail incd " +
        "where incd.type = 'Producto' and inc.id = incd.invoiceNotaCredito.id " +
        "and (inc.date   >= ?1 and inc.date  <= ?2) " +
        "and  (?3=null or  ?3=0 or incd.inventario.bodega.id =?3) " +
        "and incd.ingresadoBodega = false " +
        "and inc.ingresadoBodega = false " +
        "and inc.status <> 'Edicion' "
        + "group by inc.number, inc.client.name, inc.id,inc.date"
)
List<ProductsPendingReturnTotalDTO> getInvoiceNotaCreditoProductReturnPendingTotalByDates(Date startDate, Date endDate, Integer bodegaId);

    @Query(value = "select count(inc.id) " +
            " from  InvoiceNotaCredito inc, InvoiceNotaCreditoDetail incd " +
            "where incd.type = 'Producto' and inc.id = incd.invoiceNotaCredito.id " +
            "and (inc.date   >= ?1 and inc.date  <= ?2) " +
            "and  (?3=null or  ?3=0 or incd.inventario.bodega.id =?3) " +
            "and incd.ingresadoBodega = false " +
            "and inc.ingresadoBodega = false " +
            "and inc.status <> 'Edicion' "
            + "group by inc.number, inc.client.name, inc.id,inc.date"
    )
    Integer countInvoiceNotaCreditoProductReturnPendingTotalByDates(Date startDate, Date endDate, Integer bodegaId);


    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('DEV-NC', ncd.id, " +
            "ncd.inventario.producto.codigo ||' - '|| ncd.inventario.producto.descripcionEspanol, " +
            "ncd.inventario.bodega.name, ncd.description, ncd.quantity, ncd.price, ncd.total," +
            "ncd.costCenter.code || ' - ' || ncd.costCenter.name) " +
            "from InvoiceNotaCredito nc, InvoiceNotaCreditoDetail ncd " +
            "where nc.id = ncd.invoiceNotaCredito.id "  +
            "and nc.status = 'Pendiente' "    +
            "and ncd.type = 'Producto' " +
            "and ncd.ingresadoBodega = false " +
            "and nc.id   = ?1 " )
    List<DetailsDTO> getNotaCreditoDetailsPendings(Integer id);


    @Query(value = "select incd from InvoiceNotaCreditoDetail incd  where incd.id = ?1")
    public InvoiceNotaCreditoDetail findInvoiceNotaCreditoDetalleById(Integer id);

    @Query(value = "select coalesce(max(ivc.number), null, 0, max(ivc.number)) from InvoiceNotaCredito ivc")
    Integer getMaxConsecutive();

    @Query(value = "select c from InvoiceNotaCredito c where c.invoice.id in (?1)")
    List<InvoiceNotaCredito> getNotaCreditosByInvoicesIds(List<Integer> ids);

    @Query(value = "select sum(i.total) " +
            " from   InvoiceNotaCredito i " +
            "where i.invoice.id in (?1) "
    )
    Double sumSessionNCTotal(List<Integer> ids);
}
