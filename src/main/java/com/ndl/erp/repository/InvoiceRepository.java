package com.ndl.erp.repository;


import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {


    @Query(value = "select coalesce(max(c.number), null, 0, max(c.number)) from Invoice c")
    Integer getMaxConsecutive();

    @Query(value = "select c from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% "
    )
    Page<Invoice> getFilterBySpecial(
            Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda,
            Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% ")
    Integer countBySpecial(
            Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c" +
            " where  c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalBySpecial(Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda);


    @Query(value = "select c from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3  " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% and c.estadoHacienda like %?8%" +
            " and c.total=?9 " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByAll(Integer number,
                                         Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId,
                                         String state, String statusHacienda, Double total,
                                         Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3  " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% and c.estadoHacienda like %?8% " +
            " and c.total=?9 ")
    Integer countByAll(
            Integer number,
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda,
            Double total);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3  " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% and c.estadoHacienda like %?8% " +
            " and c.total=?9 group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByAll(Integer number,
                                         Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda,
                                         Double total);


    @Query(value = "select c from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3  " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% and c.estadoHacienda like %?8% " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByAll(Integer number,
                                         Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda,
                                         Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3  " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% and c.estadoHacienda like %?8% ")
    Integer countByAll(
            Integer number,
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and  c.currency.id=?6 and c.status like %?7% " +
            "and c.estadoHacienda like %?8% group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByAll(Integer number,
                                         Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda);


    @Query(value = "select c from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and c.estadoHacienda like %?7% " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByInvoiceNumber(Integer number,
                                                   Date start, Date end, String client, String purchaseOrderNumber,
                                                   String state, String statusHacienda,
                                                   Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and c.estadoHacienda like %?7% ")
    Integer countByInvoiceNumber(
            Integer number,
            Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c  where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and c.estadoHacienda like %?7%" +
            " group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByInvoiceNumber(Integer number,
                                                   Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda);


    @Query(value = "select c from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and c.estadoHacienda like %?7%" +
            "and c.total=?8  " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByInvoiceNumberAndTotal(Integer number,
                                                           Date start, Date end, String client, String purchaseOrderNumber,
                                                           String state, String statusHacienda, Double total,
                                                           Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and" +
            " c.estadoHacienda like %?7%  and c.total=?8 ")
    Integer countByInvoiceNumberAndTotal(
            Integer number,
            Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda, Double total);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c  where c.number=?1 and c.date>=?2 and c.date<=?3 " +
            "and c.client.name like %?4% and c.poc.orderNumber like %?5% and c.status like %?6% and" +
            " c.estadoHacienda like %?7%  and c.total=?8 group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByInvoiceNumberAndTotal(Integer number,
                                                           Date start, Date end, String client, String purchaseOrderNumber,
                                                           String state, String statusHacienda, Double total);


    @Query(value = "select c from Invoice c where  c.date>=?1 and c.date<=?2 " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% " +
            " and c.total=?7 " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByTotal(
            Date start, Date end, String client, String purchaseOrderNumber,
            String state, String statusHacienda, Double total,
            Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.date>=?1 and c.date<=?2 " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% " +
            " and c.total=?7")
    Integer countByTotal(
            Date start, Date end, String client, String purchaseOrderNumber, String state, String statusHacienda, Double total);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c  where c.date>=?1 and c.date<=?2 " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and c.status like %?5% and c.estadoHacienda like %?6% " +
            " and c.total=?7 group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByTotal(Date start, Date end, String client, String purchaseOrderNumber,
                                           String state, String statusHacienda, Double total);


    @Query(value = "select c from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6% and c.estadoHacienda like %?7% " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByCurrency(
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda,
            Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6% and c.estadoHacienda like %?7% ")
    Integer countByCurrency(
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state, String statusHacienda);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c  where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6% " +
            "and c.estadoHacienda like %?7% group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByCurrency(Date start, Date end, String client, String purchaseOrderNumber,
                                              Integer currencyId, String state, String statusHacienda);


    @Query(value = "select c from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6%" +
            " and c.estadoHacienda like %?7%  and  c.total=?8 " +
            "order by c.date  desc")
    Page<Invoice> getFilterPageableByCurrencyAndTotal(
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state,
            String statusHacienda, Double total,
            Pageable pageable);

    @Query(value = "select count(c.id) from Invoice c where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6% " +
            "and c.estadoHacienda like %?7%  and  c.total=?8 ")
    Integer countByCurrencyAndTotal(
            Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state,
            String statusHacienda, Double total);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Invoice c   where c.date>=?1 and c.date<=?2  " +
            "and c.client.name like %?3% and c.poc.orderNumber like %?4% and  c.currency.id=?5 and c.status like %?6% " +
            "and c.estadoHacienda like %?7%  and  c.total=?8 group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalByCurrencyAndTotal(Date start, Date end, String client, String purchaseOrderNumber, Integer currencyId, String state,
                                                      String statusHacienda, Double total);


    @Query(value = "select " +
            "new com.ndl.erp.dto.ScheduleInvoiceDTO(i)" +
            "FROM Invoice i  WHERE i.datePay >= ?1 and i.datePay <= ?2 and (i.status='Pendiente' or i.status ='Vencida')"
    )
    List<ScheduleInvoiceDTO> getAllByDate(java.sql.Date start, java.sql.Date end);


    @Query(value = "select new com.ndl.erp.dto.InvoiceProductTotalDTO(iv.number,'INV',  iv.client.name, iv.id, iv.date, sum(ivd.total)) " +
            " from  Invoice iv, InvoiceDetail ivd " +
            "where ivd.type = 'Producto' and iv.id = ivd.invoice.id " +
            "and (iv.date   >= ?1 and iv.date  <= ?2) " +
            "and  (?3=null or  ?3=0 or ivd.inventario.bodega.id =?3) " +
            "and iv.salidaBodega = false " +
            "and iv.status != 'Edicion' " +
            "and iv.sessionPos.id is null "
            + "group by iv.number, iv.client.name, iv.id,iv.date"
    )
    List<InvoiceProductTotalDTO> getInvoiceProductTotalByPayDates(Date startInvoiceDate, Date endInvoiceDate, Integer bodegaId);

    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('INV', ivd.id, " +
            "ivd.inventario.producto.codigo  || ' - ' || ivd.inventario.producto.descripcionEspanol," +
            "ivd.inventario.bodega.name, ivd.description, ivd.quantity, ivd.price, ivd.total," +
            " ivd.costCenter.code || ' - ' || ivd.costCenter.name) " +
            " from   InvoiceDetail ivd, Invoice iv " +
            "where ivd.type = 'Producto' and ivd.invoice.id = ?1 " +
            "and iv.id = ivd.invoice.id and iv.salidaBodega = false"
    )
    List<DetailsDTO> getInvoiceDetailsPendings(Integer id);

    @Query(value = "select c from Invoice c where iv.status != 'Edicion' and (c.salidaBodega=1 or " +
            " (c.salidaBodega=0 and 0=(select count(cd.id) from InvoiceDetail cd " +
            "where cd.invoice.id=c.id and cd.type='Producto')))  and" +
            " c.client.name like %?1% or c.poc.orderNumber like %?1% order by c.id desc")
    List<Invoice> getListFilter(String filter);

    @Query(value = "select c from Invoice c where iv.status != 'Edicion' and (c.salidaBodega=1 or " +
            " (c.salidaBodega=0 and 0=(select count(cd.id) from InvoiceDetail cd " +
            "                where cd.invoice.id=c.id and cd.type='Producto'))) and " +
            "c.number=?1 order by c.id desc")
    List<Invoice> getListFilter(Integer filter);

    Invoice getById(Integer id);

    @Query(value = "select new com.ndl.erp.domain.InvoiceNotaCredito(i) " +
            " from   Invoice i " +
            "where i.id = ?1 "
    )
    InvoiceNotaCredito getNotaCreditoFromInvoice(Integer invoiceId);

    @Query(value = "select i " +
            " from   Invoice i " +
            "where i.sessionPos!=null and  i.sessionPos.id=?1 order by i.id desc"
    )
    List<Invoice> getBySessionPosId(Integer id);

//    @Query(value = "select sum(i.total) " +
//    "case when f.tipoTransaccion =  'TIQUETE' then 'TIQ'\n" +
//            "                    when f.tipoTransaccion = 'FE' then 'FE' end,\n" +
//    @Query(value = "select " +
//            " SUM(IF(i.exchangeRate!=null, i.total/i.exchangeRate.buyRate, i.total)) AS total" +
//            " from   Invoice i " +
//            "where i.sessionPos!=null and  i.sessionPos.id=?1 "
//    )
//    Double sumSessionFacturaTotal(Integer id);

    @Query(value = "select i.id " +
            " from   Invoice i " +
            "where i.sessionPos!=null and  i.sessionPos.id=?1 "
    )
    List<Integer> getIdsBySessionPosId(Integer id);

    //Felix Saborio 1/10/2021: Fuente para detalle de ventas para informe contable
    @Query(value = " select new com.ndl.erp.dto.SaleDetailInfoDTO( \n " +
            "            f.id,\n" +
            "            f.currency,\n" +
            "            t.id,\n" +
            "            f.date,\n" +
            "            c.name,\n" +
            "            c.enterpriceId,\n" +
            "            f.consecutivo, " +
            "case when f.tipoTransaccion =  'TIQUETE' then 'TIQ'\n" +
            "                    when f.tipoTransaccion = 'FE' then 'FE' end,\n" +
            "            sum(case when c.exonerated = 'SI' then  0.0\n" +
            "                    when c.exonerated = 'NO' then  fd.subTotal end),\n" +
            "    sum(fd.exonerated),\n" +
            "    sum(fd.discountAmount),\n" +
            "    t.taxPorcent,\n" +
            "    sum(fd.tax),\n" +
            "    sum(fd.total)," +
            "      f.status, f.estadoHacienda) \n" +
            "    from  Invoice f, \n " +
            "    InvoiceDetail fd, " +
            "    Client c \n, TaxesIva t\n" +
            "    where f.id = fd.invoice.id\n" +
            "    and f.client.id = c.id\n" +
            "    and (fd.iva is null or fd.iva.id = t.id )\n" +
            "    and (f.date >= ?1 and  f.date <= ?2 )\n" +
            "    group by f.id, t.id, case when f.tipoTransaccion =  'TIQUETE' then 'TIQ'\n" +
            "    when f.tipoTransaccion = 'FE' then 'FE' end order by f.id\n")
    ArrayList<SaleDetailInfoDTO> getSaleDetailInfo(Date start, Date end);

    //Felix Saborio 1/10/2021: Fuente para detalle de notas de credito para informe contable
    @Query(value = " select new com.ndl.erp.dto.SaleDetailInfoDTO( \n" +
            " nc.id , \n" +
            " nc.currency , \n" +
            " t.id , \n" +
            " nc.date, \n" +
            " c.name, \n" +
            " c.enterpriceId, \n" +
            " nc.consecutivo, \n" +
            " 'NC', \n" +
            " sum(case when c.exonerated = 'SI' then  0.0 \n" +
            " when c.exonerated = 'NO' then  ncd.subTotal end), \n" +
            " sum(ncd.exonerated), \n" +
            " sum(ncd.discountAmount), \n" +
            " t.taxPorcent, \n" +
            " sum(ncd.tax), \n" +
            " sum(ncd.total), \n " +
            " nc.status, \n" +
            " nc.estadoHacienda)\n" +
            "  from  \n" +
            "    InvoiceNotaCredito nc,\n" +
            "    InvoiceNotaCreditoDetail ncd,\n" +
            "    Client c,\n" +
            "    TaxesIva t\n" +
            "    where nc.id = ncd.invoiceNotaCredito.id\n" +
            "    and nc.client.id = c.id\n" +
            "    and (t.id is null or ncd.iva.id = t.id)\n" +
            "    and (nc.date >= ?1 and nc.date <=?2)\n" +
            "    group by nc.id, t.id, 'NC'\n")
    ArrayList<SaleDetailInfoDTO> getSaleNCDetailInfo(Date start, Date end);



}
