package com.ndl.erp.repository;

import com.ndl.erp.domain.Kilometer;
import com.ndl.erp.domain.KilometerDetail;
import com.ndl.erp.dto.BalanceKmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public interface KilometerRepository extends JpaRepository<Kilometer, Integer> {


    @Query(value = "select c from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3  " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.status like %?5% "
            )
    Page<Kilometer> getFilterPageableByCollaboratorId(Integer collaboratorId,
                                                      Date start, Date end, String consecutive, String state,
                                                      Pageable pageable);

    @Query(value = "select count(c.id) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.status like %?5%")
    Integer countAllByCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(c.totalKm) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.status like %?5%")
    Double getSumKmCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(c.total) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.status like %?5%")
    Double getSumTotalCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, String status);



    @Query(value = "select c from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3  " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.currency.id=?5 and c.status like %?6% "
           )
    Page<Kilometer> getFilterPageableByCollaboratorId(Integer collaboratorId,
                                                      Date start, Date end, String consecutive, Integer currencyId, String state,
                                                      Pageable pageable);

    @Query(value = "select count(c.id) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.currency.id=?5 and c.status like %?6%")
    Integer countAllByCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status);

    @Query(value = "select sum(c.totalKm) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.currency.id=?5 and c.status like %?6%")
    Double getSumKmCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status);

    @Query(value = "select sum(c.total) from Kilometer c where c.collaborator.id=?1 and c.dateKm>=?2 and c.dateKm<=?3 " +
            "and (?4='' or ?4=null or c.codeKm=?4) and c.currency.id=?5 and c.status like %?6%")
    Double getSumTotalCollaboratorrAndDates(
            Integer collaboratorId,
            Date start, Date end, String consecutive, Integer currencyId, String status);

    @Query(value = "select k from KilometerDetail c, Kilometer k  where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 " +
            "and (?4='' or ?4=null or k.codeKm=?4) and k.currency.id=?5 and k.status like %?6% " )
    Page<Kilometer> getFilterPageableByCostCenterAndDatesAndCurrency(Integer costCenterId,
                                                                Date start, Date end, String consecutive,
                                                          Integer currencyId, String status,
                                                                Pageable pageable);

    @Query(value = "select count(c.id) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 " +
            "and (?4='' or ?4=null or k.codeKm=?4) and k.currency.id=?5 and k.status like %?6%")
    Integer countAllByCostCenterAndDatesAndCurrency(Integer costCenterId,
                                         Date start, Date end, String consecutive, Integer currencyId, String status);


    @Query(value = "select sum(k.totalKm) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4) and k.currency.id=?5 and k.status like %?6%")
    Double getSumKmCostCenterAndDatesAndCurrency(Integer costCenterId,
                                         Date start, Date end, String consecutive, Integer currencyId, String status);

    @Query(value = "select sum(k.total) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4) and k.currency.id=?5 and k.status like %?6%")
    Double getSumTotalCostCenterAndDatesAndCurrency(Integer costCenterId,
                                         Date start, Date end, String consecutive, Integer currencyId, String status);


    @Query(value = "select k from KilometerDetail c, Kilometer k  where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4)  and k.status like %?5% " )
    Page<Kilometer> getFilterPageableByCostCenterAndDates(Integer costCenterId,
                                                                Date start, Date end, String consecutive,
                                                          String status,
                                                                Pageable pageable);

    @Query(value = "select count(c.id) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4) and k.status like %?5%")
    Integer countAllByCostCenterAndDates(Integer costCenterId,
                                         Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(k.totalKm) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4)  and k.status like %?5%")
    Double getSumKmCostCenterAndDates(Integer costCenterId,
                                         Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(k.total) from KilometerDetail c, Kilometer k where c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and (?4='' or ?4=null or k.codeKm=?4) and k.status like %?5%")
    Double getSumTotalCostCenterAndDates(Integer costCenterId,
                                         Date start, Date end, String consecutive, String status);


    @Query(value = "select k from KilometerDetail c, Kilometer k where k.collaborator.id=?2 and " +
            "c.kilometer.id=k.id and c.costCenter.id=?1 " +
            "and k.dateKm>=?3 and k.dateKm<=?4 and (?5='' or ?5=null or k.codeKm=?5) " +
            "and k.currency.id=?6 and k.status like %?7% " )
    Page<Kilometer> getPageableByCostCenterIdAndCollaboratorAndCurrencyAndLaborDate(Integer costCenterId,
                                                                               Integer collaboratorId,
                                                                               Date start, Date end, String consecutive,
                                                                         Integer currencyId, String status,
                                                                               Pageable pageable);

    @Query(value = "select count(c.id) from KilometerDetail c, Kilometer k where k.collaborator.id=?2 and c.kilometer.id=k.id " +
            "and c.costCenter.id=?1 and k.dateKm>=?3 and k.dateKm<=?4 and (?5='' or ?5=null or k.codeKm=?5)" +
            "and k.currency.id=?6 and k.status like %?7% ")
    Integer countAllByCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(Integer costCenterId,
                                                               Integer collaboratorId,
                                                               Date start, Date end , String consecutive,
                                                               Integer currencyId, String status);

    @Query(value = "select sum(k.totalKm) from KilometerDetail c, Kilometer k where k.collaborator.id=?2 and c.kilometer.id=k.id " +
            "and c.costCenter.id=?1 and k.dateKm>=?3 and k.dateKm<=?4 and (?5='' or ?5=null or k.codeKm=?5) " +
            "and k.currency.id=?6 and k.status like %?7% ")
    Double getSumKmCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(Integer costCenterId,
                                                               Integer collaboratorId,
                                                               Date start, Date end , String consecutive,
                                                               Integer currencyId, String status);

    @Query(value = "select sum(k.total) from KilometerDetail c, Kilometer k where k.collaborator.id=?2 and c.kilometer.id=k.id " +
            "and c.costCenter.id=?1 and k.dateKm>=?3 and k.dateKm<=?4 and (?5='' or ?5=null or k.codeKm=?5) " +
            "and k.currency.id=?6 and k.status like %?7% ")
    Double getSumTotalCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(Integer costCenterId,
                                                            Integer collaboratorId,
                                                            Date start, Date end , String consecutive,
                                                            Integer currencyId, String status);





    @Query(value = "select c from Kilometer c  where c.dateKm>=?1 and " +
            "c.dateKm<=?2 and (?3='' or ?3=null or c.codeKm=?3) and c.status like %?4% "
            )
    Page<Kilometer> getPageableByDate( Date start, Date end, String consecutive,

                                             String status,
                                             Pageable pageable);

    @Query(value = "select count(c.id) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% ")
    Integer countAllByDates(Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(c.totalKm) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% ")
    Double getSumKmByDates(Date start, Date end, String consecutive, String status);

    @Query(value = "select sum(c.total) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% ")
    Double getSumTotalByDates(Date start, Date end, String consecutive, String status);


    @Query(value = "select c from Kilometer c  where c.dateKm>=?1 and " +
            "c.dateKm<=?2 and (?3='' or ?3=null or c.codeKm=?3) and c.status like %?4% and c.currency.id=?5 "
            )
    Page<Kilometer> getPageableByDateAndCurrency( Date start, Date end, String consecutive,
                                             String status, Integer currencyId,
                                             Pageable pageable);

    @Query(value = "select count(c.id) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% and c.currency.id=?5")
    Integer countAllByDatesAndCurrency(Date start, Date end, String consecutive, String status,Integer currencyId);

    @Query(value = "select sum(c.totalKm) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% and c.currency.id=?5")
    Double getSumKmByDatesAndCurrency(Date start, Date end, String consecutive, String status, Integer currencyId);

    @Query(value = "select sum(c.total) from Kilometer c where c.dateKm>=?1 and  c.dateKm<=?2 " +
            "and (?3='' or ?3=null or c.codeKm=?3) " +
            " and c.status like %?4% and c.currency.id=?5")
    Double getSumTotalByDatesAndCurrency(Date start, Date end, String consecutive, String status, Integer currencyId);

    @Query(value = "select c from KilometerDetail c where c.kilometer.id in (?1)")
    List<KilometerDetail> getDetailsByIds(List<Integer> ids);

    @Query(value = "select " +
            " new com.ndl.erp.dto.BalanceKmDTO(mc.id, " +
            "mc.status, " +
            "mc.changeTypeBuy, " +
            "mc.changeTypeSale, " +
            "bp, " +
            "km, " +
            "kmd) " +
            "FROM MonthlyClosure mc INNER JOIN MonthlyClosureBillPay mccxp on mc.id = mccxp.parent.id  " +
            "INNER JOIN BillPay bp on mccxp.billPay.id = bp.id and bp.type = 'KMS'  " +
            "INNER JOIN Kilometer km on bp.typeId = km.id " +
            "INNER JOIN KilometerDetail kmd on km.id = kmd.kilometer.id " +
            "where mc.start >= ?1 and mc.end<= ?2 order by mccxp.billPay.id, km.id,kmd.id "
    )
    List<BalanceKmDTO> getBalanceKmDTO(java.sql.Date start, java.sql.Date end);



//    @Query(value = "select count(c.id) from Kilometer c where c.codeKm=?1 and c.collaborator.id=?2")
//    Integer boolean getCount(String codeKm, Integer id);
    @Query(value = "select count(c.id) from Kilometer c where c.codeKm=?1 and c.collaborator.id=?2")
    Integer getCount(String code,Integer collaboratorId);


    @Query(value = "select k from KilometerDetail c, Kilometer k  where k.collaborator.id=?1 and c.kilometer.id=k.id  " +
            "and k.dateKm>=?2 and k.dateKm<=?3 and k.codeKm like %?4% and c.costCenter.id=?5 and k.status like %?6% "
            )
    Page<Kilometer> getFilterPageableByCollaboratorIdAndCoscenterId(Integer collaboratorId,
                                                                     Date start, Date end, String consecutive,
                                                                    Integer costCenterId, String status,
                                                                     Pageable pageable);

    @Query(value = "select count(c.id) from KilometerDetail c, Kilometer k where k.collaborator.id=?1 and c.kilometer.id=k.id  " +
            " and k.dateKm>=?2 and k.dateKm<=?3 and k.codeKm like %?4% and c.costCenter.id=?5 and k.status like %?6% " +
            " group by c.costCenter.id")
    Integer countAllByCollaboratorIdAndCoscenterId(Integer collaboratorId,
                                                    Date start, Date end, String consecutive, Integer costCenterId, String status);

    @Query(value = "select sum(k.totalKm) from KilometerDetail c, Kilometer k where k.collaborator.id=?1 and c.kilometer.id=k.id  "  +
            " and k.dateKm>=?2 and k.dateKm<=?3 and k.codeKm like %?4% and c.costCenter.id=?5 and k.status like %?6%")
    Double getSumKmByCollaboratorIdAndCoscenterId(Integer collaboratorId,
                                                 Date start, Date end, String consecutive, Integer costCenterId, String status);

    @Query(value = "select sum(k.total) from KilometerDetail c, Kilometer k where k.collaborator.id=?1 and c.kilometer.id=k.id  " +
            " and k.dateKm>=?2 and k.dateKm<=?3 and k.codeKm like %?4% and c.costCenter.id=?5 and k.status like %?6% " )
    Double getSumTotalByCollaboratorIdAndCoscenterId(Integer collaboratorId,
                                                    Date start, Date end, String consecutive, Integer costCenterId, String status);

    @Transactional
    @Modifying
    @Query(value = "update Kilometer p set p.statusClosing='CONGELADO'  where p.id in (?1)")
    void updateKilometerToFrozenByIds(List<Integer> ids);

}
