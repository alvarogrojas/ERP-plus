package com.ndl.erp.repository;

import com.ndl.erp.domain.Refundable;
import com.ndl.erp.domain.RefundableDetail;
import com.ndl.erp.dto.BalanceRefundableDTO;
import com.ndl.erp.dto.BillPayProductTotalDTO;
import com.ndl.erp.dto.DetailsDTO;
import com.ndl.erp.dto.TotalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public interface RefundableRepository extends JpaRepository<Refundable, Integer> {

    @Query(value = "select count(c.id) from Refundable c where c.codeInvoice=?1 " +
            "and c.collaborator.id=?2")
    Integer getCount(String code,Integer collaboratorId);

//    @Query(value = "select c.parentCabysId from RefundableDetail c where c.refundable.id=?1 and c.typeExpend='Servicio'")
//    List<Integer> getServiceDetail(Integer parentId);
//
//    @Query(value = "select c.parentCabysId from RefundableDetail c where c.refundable.id=?1 and c.typeExpend='Producto'")
//    List<Integer> getProductoDetail(Integer parentId);

    // 1 1 0

    @Query(value = "select c from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3  " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5% and c.currency.id=?6 "
    )
    Page<Refundable> getFilterPageableByCollaboratorIdAndCurrencyId(Integer collaboratorId,
                                                       Date start, Date end, String consecutive, String state,
                                                       Integer currencyId,
                                                       Pageable pageable);

    @Query(value = "select count(c.id) from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5%  and c.currency.id=?6")
    Integer countAllByCollaboratorAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status,Integer currencyId);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) " +
            "from Refundable c " +
            "where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5%  and c.currency.id=?6 ")
    ArrayList<TotalDTO>  getSumTotalCollaboratorAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive,  String status, Integer currencyId);


    // 1 0 0

    @Query(value = "select c from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3  " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5% "
            )
    Page<Refundable> getFilterPageableByCollaboratorId(Integer collaboratorId,
                                                      Date start, Date end, String consecutive, String state,
                                                      Pageable pageable);

    @Query(value = "select count(c.id) from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5%")
    Integer countAllByCollaboratorAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status);


//    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
//            "and c.codeInvoice like %?4% and c.status like %?5%")
    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.status like %?5% group by c.currency.id")
    ArrayList<TotalDTO>  getSumTotalCollaboratorAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive,  String status);

    @Query(value = "select k from RefundableDetail c, Refundable k  where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and k.currency.id=?5 and k.status like %?6% " +
            "group by c.costCenter.id")
    Page<Refundable> getFilterPageableByCostCenterAndDates(Integer costCenterId,
                                                          Date start, Date end, String consecutive,
                                                          Integer currencyId, String status,
                                                          Pageable pageable);

    @Query(value = "select count(c.id) from RefundableDetail c, Refundable k where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 and" +
            " (?4='' or ?4=null or k.codeInvoice=?4) and k.currency.id=?5 and k.status like %?6%")
    Integer countAllByCostCenterAndDates(Integer costCenterId,
                                         Date start, Date end, String consecutive, Integer currencyId, String status);


//    @Query(value = "select sum(k.total) from RefundableDetail c, Refundable k where c.refundable.id=k.id and c.costCenter.id=?1 " +
//            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 and k.codeInvoice like %?4% and k.currency.id=?5 and k.status like %?6%")

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(k.total), k.currency.name,k.currency.simbol) from RefundableDetail c, Refundable k where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and k.currency.id=?5 and k.status like %?6%")
    ArrayList<TotalDTO> getSumTotalCostCenterAndDates(Integer costCenterId,
                                         Date start, Date end, String consecutive, Integer currencyId, String status);





    @Query(value = "select c from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 and k.dateInvoice<=?4 " +
            "and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% " +
            "group by c.costCenter.id")
    Page<Refundable> getPageableByCostCenterIdAndCollaboratorAndLaborDate(Integer costCenterId,
                                                                         Integer collaboratorId,
                                                                         Date start, Date end, String consecutive,
                                                                         Integer currencyId, String status,
                                                                         Pageable pageable);

    @Query(value = "select count(c.id) from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 and k.dateInvoice<=?4 " +
            "and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% ")
    Integer countAllByCostCenterIdAndCollaboratorAndLaborDates(Integer costCenterId,
                                                               Integer collaboratorId,
                                                               Date start, Date end, String consecutive,
                                                               Integer currencyId, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(k.total), k.currency.name,k.currency.simbol) from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 " +
            "and k.dateInvoice<=?4 " +
            "and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% group by k.currency.id")
   ArrayList<TotalDTO> getSumTotalCostCenterIdAndCollaboratorAndLaborDates(Integer costCenterId,
                                                                           Integer collaboratorId,
                                                                           Date start, Date end, String consecutive,
                                                                           Integer currencyId, String status);

    @Query(value = "select c from Refundable c  where c.dateInvoice>=?1 and " +
            "c.dateInvoice<=?2 and (?3='' or ?3=null or c.codeInvoice=?3) and c.currency.id=?4 and c.status like %?5% " +
            "order by c.dateInvoice desc, c.collaborator.lastName desc")
    Page<Refundable> getPageableByCurrencyIdAndDates(Date start, Date end, String consecutive,
                                       Integer currencyId,
                                       String status,
                                       Pageable pageable);

    @Query(value = "select count(c.id) from Refundable c where c.dateInvoice>=?1 and  c.dateInvoice<=?2 " +
            "and (?3='' or ?3=null or c.codeInvoice=?3) " +
            "and c.currency.id=?4 and c.status like %?5% ")
    Integer countAllByCurrencyAndDates(Date start, Date end, String consecutive, Integer currencyId, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) " +
            "from Refundable c where c.dateInvoice>=?1 and  c.dateInvoice<=?2 " +
            "and (?3='' or ?3=null or c.codeInvoice=?3) " +
            "and c.currency.id=?4 and c.status like %?5%  group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalByCurrencyAndDates(Date start, Date end, String consecutive, Integer currencyId, String status);


    //0 0 0
    @Query(value = "select c from Refundable c  where c.dateInvoice>=?1 and " +
            "c.dateInvoice<=?2 and (?3='' or ?3=null or c.codeInvoice=?3) and c.status like %?4% " +
            "")
    Page<Refundable> getPageableByDateOnly(Date start, Date end, String consecutive,
                                       String status,
                                       Pageable pageable);

    @Query(value = "select count(c.id) from Refundable c where c.dateInvoice>=?1 and  c.dateInvoice<=?2 " +
            "and (?3='' or ?3=null or c.codeInvoice=?3) " +
            "and c.status like %?4% ")
    Integer countAllByDatesOnly(Date start, Date end, String consecutive, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from Refundable c where c.dateInvoice>=?1 and  c.dateInvoice<=?2 " +
            "and (?3='' or ?3=null or c.codeInvoice=?3) " +
            "and c.status like %?4%  group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalByDatesOnly(Date start, Date end, String consecutive, String status);

    //select sum(total) , bp.currency.id, bp.currency.name,bp.currency.simbol from Refundable bp group by bp.currency.id

//    @Query(value = "select sum(c.total) from Refundable c where c.dateInvoice>=?1 and  c.dateInvoice<=?2 and c.codeInvoice like %?3% " +
//            "and c.currency.id=?4 and c.status like %?5% ")
//    Double getSumTotalByDates(Date start, Date end, String consecutive, Integer currencyId, String status);

    @Query(value = "select c from RefundableDetail c where c.refundable.id in (?1)")
    List<RefundableDetail> getDetailsByIds(List<Integer> ids);

//    int, java.lang.String, double, double, int, java.util.Date, com.ndl.erp.domain.Refundable, com.ndl.erp.domain.RefundableDetail
//BalanceRefundableDTO(mc.id, mc.status, mc.changeTypeBuy, mc.changeTypeSale, bp.id , bp.billDate , r, rd)
    @Query(value = "select " +
            "new com.ndl.erp.dto.BalanceRefundableDTO(" +
            "mc.id, " +
            "mc.status, " +
            "mc.changeTypeBuy, " +
            "mc.changeTypeSale, " +
            "bp, " +
            "r, rd" +
            ") " +
            "FROM MonthlyClosure mc INNER JOIN MonthlyClosureBillPay mccxp on mc.id = mccxp.parent.id  " +
            "INNER JOIN BillPay bp on mccxp.billPay.id = bp.id and bp.type = 'REM'  " +
            "INNER JOIN Refundable r on bp.typeId = r.id " +
            "INNER JOIN RefundableDetail rd on r.id = rd.refundable.id " +
            "where mc.start >= ?1 and mc.end<= ?2 order by mccxp.billPay.id, r.id,rd.id "
    )
    List<BalanceRefundableDTO> getBalanceRefundableDTO(java.sql.Date start, java.sql.Date end);


    // 1 1 1

    @Query(value = "select c from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 " +
            "and k.dateInvoice<=?4 and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% " +
            "group by c.costCenter.id")
    Page<Refundable> getPageableByCostCenterIdAndCollaboratorAndCurrencyAndLaborDate(Integer costCenterId,
                                                                          Integer collaboratorId,
                                                                          Date start, Date end, String consecutive,
                                                                          Integer currencyId, String status,
                                                                          Pageable pageable);

    @Query(value = "select count(c.id) from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 and k.dateInvoice<=?4 " +
            "and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% ")
    Integer countAllByCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(Integer costCenterId,
                                                               Integer collaboratorId,
                                                               Date start, Date end, String consecutive,
                                                               Integer currencyId, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(k.total), k.currency.name,k.currency.simbol) " +
            "from RefundableDetail c, Refundable k where k.collaborator.id=?2 and c.refundable.id=k.id " +
            "and c.costCenter.id=?1 and k.dateInvoice>=?3 and k.dateInvoice<=?4 " +
            "and (?5='' or ?5=null or k.codeInvoice=?5) " +
            "and k.currency.id=?6 and k.status like %?7% group by k.currency.id")
    ArrayList<TotalDTO> getSumTotalCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(Integer costCenterId,
                                                                            Integer collaboratorId,
                                                                            Date start, Date end, String consecutive,
                                                                            Integer currencyId, String status);


    //0 1 0
    @Query(value = "select k from RefundableDetail c, Refundable k  where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and " +
            "k.status like %?5% " )
    Page<Refundable> getFilterPageableByCostCenterAndDates(Integer costCenterId, Date startDate, Date endDate,
                                                                      String consecutive,
                                                                      String state, PageRequest pageable);

    @Query(value = "select count(c.id) from RefundableDetail c, Refundable k where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and  " +
            "k.status like %?5% ")
    Integer countAllByCostCenterAndDates(Integer costCenterId,
                                                    Date start, Date end, String consecutive,String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(k.total), k.currency.name,k.currency.simbol)from " +
            "RefundableDetail c, Refundable k  where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and k.status like %?5%")
    ArrayList<TotalDTO> getSumTotalCostCenterAndDates(Integer costCenterId,
                                                    Date start, Date end, String consecutive, String status);

    //0 1 1
    @Query(value = "select k from RefundableDetail c, Refundable k  where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) " +
            "and k.currency.id=?5 and " +
            "k.status like %?6% " )
    Page<Refundable> getFilterPageableByCostCenterAndDatesAndCurrency(Integer costCenterId, Date startDate, Date endDate,
                                                                      String consecutive, Integer currencyId,
                                                                      String state, PageRequest pageable);

    @Query(value = "select count(c.id) from RefundableDetail c, Refundable k where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and k.currency.id=?5 and " +
            "k.status like %?6% ")
    Integer countAllByCostCenterAndDatesAndCurrency(Integer costCenterId,
                                                    Date start, Date end, String consecutive, Integer currencyId, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(k.total), k.currency.name,k.currency.simbol)from RefundableDetail c, Refundable k  where c.refundable.id=k.id and c.costCenter.id=?1 " +
            "and k.dateInvoice>=?2 and k.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or k.codeInvoice=?4) and k.currency.id=?5 and k.status like %?6%")
    ArrayList<TotalDTO> getSumTotalCostCenterAndDatesAndCurrency(Integer costCenterId,
                                                    Date start, Date end, String consecutive, Integer currencyId, String status);


    // 1 0 1

    @Query(value = "select c from Refundable c where c.collaborator.id=?1 " +
            "and c.dateInvoice>=?2 and c.dateInvoice<=?3  " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.currency.id=?5 and c.status like %?6% " +
            "order by c.dateInvoice desc, c.collaborator.lastName desc")
    Page<Refundable> getFilterPageableByCollaboratorIdAndCurrency(Integer collaboratorId,
                                                       Date start, Date end, String consecutive, Integer currencyId, String state,
                                                       Pageable pageable);

    @Query(value = "select count(c.id) from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.currency.id=?5 and c.status like %?6%")
    Integer countAllByCollaboratorAndCurrencyAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status);


    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) " +
            "from Refundable c where c.collaborator.id=?1 and c.dateInvoice>=?2 and c.dateInvoice<=?3 " +
            "and (?4='' or ?4=null or c.codeInvoice=?4) and c.currency.id=?5 and c.status like %?6%")
    ArrayList<TotalDTO>  getSumTotalCollaboratorAndCurrencyAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status);


    @Transactional
    @Modifying
    @Query(value = "update Refundable p set p.statusClosing='CONGELADO'  where p.id in (?1)")
    void updateRefundableToFrozenByIds(List<Integer> remIds);


    @Query(value = "select new com.ndl.erp.dto.BillPayProductTotalDTO('REM',r.codeInvoice, r.collaborator.name, r.collaborator.lastName, r.id, r.dateInvoice, sum(rf.subTotal)) " +
            "from Refundable r, RefundableDetail rf " +
            "where r.id = rf.refundable.id "  +
            "and r.status <> 'Edicion' "    +
            "and rf.typeLine = 'Producto' " +
            "and rf.ingresadoBodega = false " +
            "and (r.dateInvoice   >= ?1 and r.dateInvoice  <= ?2) " +
            "and  (?3=null or  ?3=0 or rf.bodega.id = ?3 ) " +
            " group by r.codeInvoice, r.id, r.dateInvoice")
    List<BillPayProductTotalDTO> getRefundableTotalByPayDates(Date startDateInvoice, Date endDateInvoice, Integer bodegaId);


    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('REM', rf.id, " +
                                                                  "rf.producto.codigo ||' - '|| rf.producto.descripcionEspanol, " +
                                                                  "rf.bodega.name, rf.description, rf.quantity, rf.price, rf.subTotal," +
                                                                  "rf.costCenter.code || ' - ' || rf.costCenter.name) " +
            "from Refundable r, RefundableDetail rf " +
            "where r.id = rf.refundable.id "  +
            "and r.status <> 'Edicion' "    +
            "and rf.typeLine = 'Producto' " +
            "and rf.ingresadoBodega = false " +
            "and r.id   = ?1 " )
            List<DetailsDTO> getRefundableDetailPendings(Integer id);

}
