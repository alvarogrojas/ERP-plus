package com.ndl.erp.repository;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.BillPayDetail;
import com.ndl.erp.domain.PurchaseOrderClient;
import com.ndl.erp.dto.*;
import org.springframework.data.domain.Page;
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
public interface BillPayRepository extends JpaRepository<BillPay, Integer> {


    @Query(value = "select c.parentCabysId from BillPayDetail c where c.billPay.id=?1 and type='Servicio'")
    List<Integer> getServiceBillPayDetail(Integer parentId);

    @Query(value = "select c.parentCabysId from BillPayDetail c where c.billPay.id=?1 and type='Producto'")
    List<Integer> getProductoBillPayDetail(Integer parentId);

    @Transactional
    @Modifying
    @Query(value = "delete from BillPay bp where bp.typeId=?1 and bp.type=?2 ")
    void deleteByTypeIdAndType(Integer typeId, String type);

    // 1 1 1
    @Query(value = "select c from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3  " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% " +
            "and  c.payDate>=?8 and c.payDate<=?9 " +
            "order by c.billDate desc, c.provider.name desc")
    Page<BillPay> getFilterPageableByProviderIdAndCurrencyIdAndExpireDates(Integer providerId,
                                                      Date start, Date end, String consecutive, Integer currencyId, String state, String type,
                                                      Date expireStart, Date expireEnd,
                                                      Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% " +
            "and  c.payDate>=?8 and c.payDate<=?9 "
    )
    Integer countAllByProviderAndCurrencyIdAndExpireDates(
            Integer providerId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type, Date expireStart, Date expireEnd);



    @Query(value = "select sum(c.total) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% " +
            "and  c.payDate>=?8 and c.payDate<=?9 "
    )
    Double getSumTotalProviderAndCurrencyIdAndExpireDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type,
            Date expireStart, Date expireEnd);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol)  from BillPay c " +
            "where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% " +
            "and  c.payDate>=?8 and c.payDate<=?9 " +
            "and c.type like %?7% group by c.currency.id "
    )
    ArrayList<TotalDTO> getSumTotalsProviderAndCurrencyIdAndExpireDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type, Date expireStart, Date expireEnd);

    // 1 1 0
    @Query(value = "select c from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3  " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% " +
            "order by c.billDate desc, c.provider.name desc")
    Page<BillPay> getFilterPageableByProviderIdAndCurrencyId(Integer providerId,
                                                             Date start, Date end, String consecutive, Integer currencyId, String state, String type,
                                                             Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% "
    )
    Integer countAllByProviderAndCurrencyIdAndDates(
            Integer providerId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type);



    @Query(value = "select sum(c.total) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% and c.type like %?7% "
    )
    Double getSumTotalProviderAndCurrencyIdAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type
            );

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol)  from BillPay c " +
            "where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.currency.id=?5 and c.status like %?6% " +
            "and c.type like %?7% group by c.currency.id "
    )
    ArrayList<TotalDTO> getSumTotalsProviderAndCurrencyIdAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status, String type);

    // 1 0 0
    @Query(value = "select c from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3  " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% and c.type like %?6% " +
            "order by c.billDate desc, c.provider.name desc")
    Page<BillPay> getFilterPageableByProviderId(Integer providerId,
                                                Date start, Date end, String consecutive, String state, String type,
                                                Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% and c.type like %?6% ")
    Integer countAllByProviderId(
            Integer providerId,
            Date start, Date end, String consecutive, String status, String type);



    @Query(value = "select sum(c.total) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% and c.type like %?6% ")
    Double getSumTotalProviderId(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status, String type);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol)  from BillPay c " +
            "where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% " +
            "and c.type like %?6% group by c.currency.id")
    ArrayList<TotalDTO> getSumTotalsProviderId(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status, String type);

    // 1 0 1
    @Query(value = "select c from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3  " +
            "and (?4='' or ?4=null or c.billNumber=?4) and (?5='' or ?5=null or c.status like %?5%) and (?6='' or ?6=null or c.type like %?6%) " +
            "and  c.payDate>=?7 and c.payDate<=?8 " +
            "order by c.billDate desc, c.provider.name desc")
    Page<BillPay> getFilterPageableByProviderIdAndExpireDates(Integer providerId,
                                                Date start, Date end,
                                                 String consecutive,
                                                 String state,
                                                              String type,
                                                              Date expireStartDate , Date expireEndDate,
                                                Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% and c.type like %?6% " +
            "and  c.payDate>=?7 and c.payDate<=?8 "
    )
    Integer countAllByProviderIdAndExpireDates(
            Integer providerId,
            Date start, Date end, String consecutive, String status, String type,
            Date expireEndDate, Date expireStartDate);



    @Query(value = "select sum(c.total) from BillPay c where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% and c.type like %?6% " +
            "and  c.payDate>=?7 and c.payDate<=?8 "
    )
    Double getSumTotalProviderIdAndExpireDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status, String type,
            Date expireEndDate, Date expireStartDate);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol)  from BillPay c " +
            "where c.provider.id=?1 and c.billDate>=?2 and c.billDate<=?3 " +
            "and (?4='' or ?4=null or c.billNumber=?4) and c.status like %?5% " +
            "and c.type like %?6% " +
            "and  c.payDate>=?7 and c.payDate<=?8 " +
            "group by c.currency.id "

    )
    ArrayList<TotalDTO> getSumTotalsProviderIdAndExpireDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status,
            String type, Date expireEndDate, Date expireStartDate);




    @Query(value = "select c from BillPayDetail c where c.billPay.id in (?1)")
    List<BillPayDetail> getDetailsByIds(List<Integer> ids);

    public List<BillPay> findByIdIn(List<Integer> ids);


    // 0 1 0

    @Query(value = "select c from BillPay c  where c.billDate>=?1 and " +
            "c.billDate<=?2 and (?3='' or ?3=null or c.billNumber=?3) and c.currency.id=?4 and c.status like %?5% and c.type like %?6% " )
    Page<BillPay> getPageableByCurrencyId(Date start, Date end, String consecutive,
                                      Integer currencyId,
                                      String status, String type,
                                      Pageable pageable);



    @Query(value = "select count(c.id) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 and " +
            "(?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% ")
    Integer countAllByCurrencyId(Date start, Date end, String consecutive, Integer currencyId, String status, String type);

    @Query(value = "select sum(c.total) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% ")
    Double getSumTotalByCurrencyId(Date start, Date end, String consecutive, Integer currencyId, String status, String type);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from BillPay c " +
            "where c.billDate>=?1 and  c.billDate<=?2 and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalsByCurrencyId(Date start, Date end, String consecutive, Integer currencyId, String status, String type);


    // 0 1 1

    @Query(value = "select c from BillPay c  where c.billDate>=?1 and " +
            "c.billDate<=?2 and (?3='' or ?3=null or c.billNumber=?3) and c.currency.id=?4 and c.status like %?5% and c.type like %?6% " +
            "and  c.payDate>=?7 and c.payDate<=?8" )
    Page<BillPay> getPageableByCurrencyIdAndExpireDates(Date start, Date end, String consecutive,
                                               Integer currencyId,
                                               String status, String type,
                                                        Date expireStart, Date expireEnd,
                                               Pageable pageable);



    @Query(value = "select count(c.id) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% and  c.payDate>=?7 and c.payDate<=?8")
    Integer countAllByCurrencyIdAndExpireDates(Date start, Date end, String consecutive, Integer currencyId,
                                               String status, String type,
                                               Date expireStart, Date expireEnd);

    @Query(value = "select sum(c.total) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% and  c.payDate>=?7 and c.payDate<=?8 ")
    Double getSumTotalByCurrencyIdAndExpireDates(Date start, Date end, String consecutive, Integer currencyId,
                                                 String status, String type,
                                                 Date expireStart, Date expireEnd);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from BillPay c " +
            "where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.currency.id=?4 and c.status like %?5% and c.type like %?6% and  c.payDate>=?7 and c.payDate<=?8 group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalsByCurrencyIdAndExpireDates(Date start, Date end, String consecutive,
                                                               Integer currencyId, String status, String type,
                                                               Date expireStart, Date expireEnd);


    // 0 0 0

    @Query(value = "select c from BillPay c  where c.billDate>=?1 and " +
            "c.billDate<=?2 and (?3='' or ?3=null or c.billNumber=?3) and c.status like %?4% and c.type like %?5% " )
    Page<BillPay> getPageableByDates(Date start, Date end, String consecutive,
                                              String status, String type,
                                               Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% ")
    Integer countAllByDates(Date start, Date end, String consecutive, String status, String type);

    @Query(value = "select sum(c.total) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% ")
    Double getSumTotalByDates(Date start, Date end, String consecutive, String status, String type);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from BillPay c " +
            "where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalsByDates(Date start, Date end, String consecutive, String status, String type);

    // 0 0 1

    @Query(value = "select c from BillPay c  where c.billDate>=?1 and " +
            "c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) and c.status like %?4% and c.type like %?5% " +
            "and  c.payDate>=?6 and c.payDate<=?7"
    )
    Page<BillPay> getPageableByDatesAndExpireDates(Date start, Date end, String consecutive,
                                     String status, String type, Date expireStart, Date expireEnd,
                                     Pageable pageable);

    @Query(value = "select count(c.id) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% and  c.payDate>=?6 and c.payDate<=?7")
    Integer countAllByDatesAndExpireDates(Date start, Date end, String consecutive, String status, String type,
                                          Date expireStart, Date expireEnd);

    @Query(value = "select sum(c.total) from BillPay c where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% " +
            "and  c.payDate>=?6 and c.payDate<=?7")
    Double getSumTotalByDatesAndExpireDates(Date start, Date end, String consecutive, String status, String type,
                                            Date expireStart, Date expireEnd);

    @Query(value = "select new com.ndl.erp.dto.TotalDTO(sum(c.total), c.currency.name,c.currency.simbol) from BillPay c " +
            "where c.billDate>=?1 and  c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) " +
            "and c.status like %?4% and c.type like %?5% " +
            "and  c.payDate>=?6 and c.payDate<=?7 " +
            "group by c.currency.id ")
    ArrayList<TotalDTO> getSumTotalsByDatesAndExpireDates(Date start, Date end, String consecutive, String status,
                                                          String type, Date expireStart, Date expireEnd);

    @Query(value = "select c from BillPay c  where c.billDate>=?1 and " +
            "c.billDate<=?2 " +
            "and (?3='' or ?3=null or c.billNumber=?3) and c.currency.id=?4 a" +
            "nd c.status like %?5% and c.type like %?6% " )
    List<BillPay> getUsingAllParams(Date start, Date end, String consecutive,
                                    Integer currencyId,
                                    String status, String type);


    @Query(value = "select c from BillPay c  where c.billDate>=?1 and c.billDate<=?2 and c.inClosing=?3 order by c.endDate asc")
    List<BillPay> getByDate(Date start, Date end, Boolean inClosing);

//    @Query(value = "select mc.id  as idMC, mc.status, mc.changeTypeBuy, mc.changeTypeSale, " +
//            "bpd.id as idBillPayDetail, " +
//            "bpd.billPay.id as idBillPay," +
//            "bpd.costCenter.id as idCostCenter, " +
//            "bp.currency.id as idCurrency, " +
//            "bp.billDate , " +
//            "bpd.quantity, " +
//            "bpd.price, " +
//            "bpd.tax, " +
//            "bpd.taxPorcent, " +
//            "bpd.discount, " +
//            "bpd.creditNoteNumber, " +
//            "bpd.subTotal, " +
//            "bpd.total, " +
//            "bpd.groceryCode, " +
//            "bpd.detail, " +
//            "'CXP', " +
//            " bpd.costCenter " +
//            "FROM MonthlyClosure mc INNER JOIN MonthlyClosureBillPay mccxp on mc.id = mccxp.parent.id  " +
//            "INNER JOIN BillPay bp on mccxp.billPay.id = bp.id " +
//            "INNER JOIN BillPayDetail bpd on mccxp.billPay.id  = bpd.billPay.id and bp.id = bpd.billPay.id " +
//            "WHERE mc.start >= ?1 and mc.end <= ?2 order by mccxp.billPay.id"

            @Query(value = "select " +
                    "new com.ndl.erp.dto.BalanceBillPayDTO(mc.id," +
                    " mc.status, mc.changeTypeBuy, " +
                    "mc.changeTypeSale, " +
                    "bp, " +
                    "bpd) " +
            "FROM MonthlyClosure mc INNER JOIN MonthlyClosureBillPay mccxp on mc.id = mccxp.parent.id  " +
            "INNER JOIN BillPay bp on mccxp.billPay.id = bp.id " +
            "INNER JOIN BillPayDetail bpd on mccxp.billPay.id  = bpd.billPay.id and bp.id = bpd.billPay.id " +
            "WHERE mc.start >= ?1 and mc.end <= ?2 order by mccxp.billPay.id"
    )
    List<BalanceBillPayDTO> getBalanceBillPayDTO(java.sql.Date start, java.sql.Date end);

    BillPay findByTypeIdAndType(Integer id, String kms);

    BillPay getByTypeIdAndType(Integer id, String type);

    @Transactional
    @Modifying
    @Query(value = "update BillPay p set p.inClosing=false  where p.id in (?1)")
    void updateBillPayByIds(List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "update BillPay p set p.statusClosing='CONGELADO'  where p.id in (?1)")
    void updateBillPayToFrozenByIds(List<Integer> ids);


    @Query(value = "select p.typeId from BillPay p " +
            "where  p.id in (?1) " +
            "and p.type=?2 " )
    ArrayList<Integer> getTypeIdByType(List<Integer> ids, String type);

    @Query(value = "select " +
            "new com.ndl.erp.dto.ScheduleBillPayWeekDTO(i)" +
            "FROM BillPay i  WHERE i.payDate >= ?1 and i.payDate <= ?2 and (i.status='Pendiente' or i.status ='Vencida')"
    )
    List<ScheduleBillPayWeekDTO> getAllBillPayByDates(java.sql.Date date, java.sql.Date date1);


    @Query(value = "select new com.ndl.erp.dto.BillPayProductTotalDTO('CXP',bp.billNumber, bp.providerName, '', bp.id, bp.billDate, sum(bpd.total)) " +
           " from  BillPay bp, BillPayDetail bpd " +
           "where bpd.type = 'Producto' and bp.id = bpd.billPay.id " +
            "and bp.status <> 'Edicion' "    +
           "and (bp.billDate   >= ?1 and bp.billDate  <= ?2) " +
         //  "and  (?3=null or  ?3=0 or bpd.inventario.bodega.id = ?3 ) " +
           "and bp.ingresadoBodega = false "
            + "group by bp.billNumber, bp.providerName, bp.id, bp.billDate"
    )
    List<BillPayProductTotalDTO> getBillPayTotalByPayDates(Date startBillPayDate, Date endBillPayDate, Integer bodegaId);


    @Query(value = "select new com.ndl.erp.dto.DetailsDTO('CXP', bpd.id, " +
                                                                "bpd.producto.codigo || ' - ' || bpd.producto.descripcionEspanol," +
                                                                "bpd.bodega.name," +
                                                                "bpd.detail, bpd.quantity, bpd.price, bpd.total," +
                                                                "bpd.costCenter.code || ' - ' ||  bpd.costCenter.name) " +
                    " from   BillPayDetail bpd, BillPay bp " +
                    "where bpd.type = 'Producto' and bpd.billPay.id = bp.id " +
                    "and bp.id = ?1 and bp.ingresadoBodega = false")
    List<DetailsDTO> getBillPayDetailsPendings(Integer id);

//    @Query(value = "select c from BillPay c  where (?1='' or ?1=null or c.billNumber like %?1%) " +
//                   " or (?1='' or ?1=null or c.provider.name = null or c.provider.name like %?1%) " +
//                   " and c.ingresadoBodega = true " )
    @Query(value = "select c from BillPay c  where " +
                    " (?1='' or ?1=null or c.provider.name like %?1%  or " +
                    "  c.billNumber like %?1%) and " +
                   " c.ingresadoBodega = true order by billNumber desc " )
    List<BillPay> getBillPaysNcByBillNumberAndProvider(String filter);

    BillPay getById(Integer id);
}
