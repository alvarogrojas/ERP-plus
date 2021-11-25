package com.ndl.erp.repository;


import com.ndl.erp.domain.BillPayNotaCredito;
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
public interface BillPayNotaCreditoRepository extends JpaRepository<BillPayNotaCredito, Integer> {



    @Query(value = "select bpnc from BillPayNotaCredito bpnc where bpnc.id =?1 ")
    BillPayNotaCredito findBillPayNotaCreditoById(Integer id);

    @Query(value = "select max(bpnc.number) from BillPayNotaCredito bpnc")
    Integer getMaxConsecutive();

    @Query(value = "select bpnc from BillPayNotaCredito bpnc where bpnc.date>=?1 and bpnc.date<=?2  " +
            "and (?3=null or  ?3='' or bpnc.status like %?3%)  " +
            "order by bpnc.date  desc")
    Page<BillPayNotaCredito> getPageableBillPayNotaCreditoByDateAndStatus(
            Date start, Date end, String status, Pageable pageable);

    @Query(value = "select count(bpnc.id) from BillPayNotaCredito bpnc where bpnc.date>=?1 and bpnc.date<=?2  " +
            "and (?3=null or  ?3='' or bpnc.status like %?3%)  " +
            "order by bpnc.date  desc")
    Integer  countAllBillPayNotaCreditoByDateAndStatus(
            Date start, Date end, String status);

    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('NC-CXC', " +
            "ncd.id, ncd.inventario.producto.codigo || ' - ' || ncd.inventario.producto.descripcionEspanol, " +
            "ncd.inventario.bodega.name, ncd.description, ncd.quantity, ncd.price, ncd.total," +
            "ncd.costCenter.code || ' - '|| ncd.costCenter.name) " +
            " from   BillPayNotaCredito nc, BillPayNotaCreditoDetail ncd " +
            "where  ncd.billPayNotaCredito.id = ?1 " +
            "and nc.id =  ncd.billPayNotaCredito.id  and nc.salidaBodega = false " +
            " and ncd.billPayDetail.type = 'Producto' " +
            " and nc.status = 'Emitida' ")

    List<DetailsDTO> getBillPayNotaCreditoDetailsPendings(Integer id);

    @Query(value = "select new com.ndl.erp.dto.InvoiceProductTotalDTO(bpnc.consecutivo,'NC-CXC', '', bpnc.id, bpnc.date, sum(bpncd.subTotal)) " +
            "from BillPayNotaCredito bpnc, BillPayNotaCreditoDetail bpncd " +
            "where bpnc.id = bpncd.billPayNotaCredito.id " +
            "and bpncd.billPayDetail.type = 'Producto' " +
            "and (?1= null or bpnc.date >=?1) " +
            "and (?2= null or bpnc.date <=?2) " +
            "and ((?3 = null or ?3=0) or (bpncd.billPayDetail.inventario.bodega.id = ?3)) " +
            "and bpnc.status = 'Emitida' and bpncd.salidaBodega = false " +
            "group by bpnc.consecutivo, bpnc.id, bpnc.date" )
    List<InvoiceProductTotalDTO> getBillPayNotaCreditoProductTotalByPayDates(Date startInvoiceDate, Date endInvoiceDate, Integer bodegaId);


}