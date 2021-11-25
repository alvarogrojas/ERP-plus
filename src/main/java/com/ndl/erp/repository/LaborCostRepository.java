package com.ndl.erp.repository;


import com.ndl.erp.domain.LaborCost;
import com.ndl.erp.domain.LaborCostDetail;
import com.ndl.erp.dto.HmHdDTO;
import com.ndl.erp.dto.LaborCostTotalHours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface LaborCostRepository extends JpaRepository<LaborCost, Integer> {


//    @Query(value = "select c from LaborCost c  where c.notes like %?1% ")
//    Page<LaborCost> getFilterPageable(String filter, Pageable pageable);

    @Query(value = "select c from LaborCostDetail c where c.laborDate>=?1 and c.laborDate<=?2 and c.collaborator.status='Activo' order by c.collaborator.id asc")
    List<LaborCostDetail> getLaborCostsByStarAndEndOrderByCollaborator(Date start, Date end);


    @Query(value = "select c from LaborCostDetail c where c.laborDate>=?1 and c.laborDate<=?2 and c.collaborator.status='Activo' order by c.laborDate")
    List<LaborCostDetail> getLaborCostsByDates(Date start, Date end);

    @Query(value = "select c from LaborCost c where c.laborDate>=?1 and c.laborDate<=?2 order by c.laborDate")
    List<LaborCost> getLaborCostByStarAndEnd(Date start, Date end);

    @Query(value = "select c from LaborCostDetail c where c.collaborator.id=?1 and c.laborDate>=?2 and c.laborDate<=?3 order by c.laborDate desc, c.collaborator.lastName desc")
    Page<LaborCostDetail> getFilterPageableByCollaboratorId(Integer collaboratorId,
                                                      Date start, Date end,
                                                      Pageable pageable);

    @Query(value = "select count(c.id) from LaborCostDetail c where c.collaborator.id=?1 and c.laborDate>=?2 and c.laborDate<=?3")
    Integer countAllByCCollaboratorrAndDates(
                                         Integer collaboratorId,
                                         Date start, Date end);

    @Query(value = "select new com.ndl.erp.dto.LaborCostTotalHours(sum(c.hoursSimple), sum(c.hoursMedia), sum(c.hoursDouble)) from LaborCostDetail c " +
            " where c.collaborator.id=?1 and c.laborDate>=?2 and c.laborDate<=?3 ")
    LaborCostTotalHours sumHoursByCollaboratorAndDates(Integer collaboratorId,
                                                     Date start, Date end);

    @Query(value = "select c from LaborCostDetail c  where c.costCenter.id=?1 and c.laborDate>=?2 and c.laborDate<=?3 order by c.laborDate desc, c.collaborator.lastName desc")
    Page<LaborCostDetail> getFilterPageableByCostCenterAndDates(Integer costCenterId,
                                                        Date start, Date end,
                                                        Pageable pageable);

    @Query(value = "select count(c.id) from LaborCostDetail c where c.costCenter.id=?1 and c.laborDate>=?2 and c.laborDate<=?3")
    Integer countAllByCostCenterAndDates(Integer costCenterId,
                                                               Date start, Date end);

    @Query(value = "select new com.ndl.erp.dto.LaborCostTotalHours(sum(c.hoursSimple), sum(c.hoursMedia), sum(c.hoursDouble)) from LaborCostDetail c " +
            " where c.costCenter.id=?1 and c.laborDate>=?2 and c.laborDate<=?3 ")
    LaborCostTotalHours sumHoursByCostCenterAndDates(Integer costCenterId,
                                                     Date start, Date end);


    @Query(value = "select c from LaborCostDetail c  where c.costCenter.id=?1 and c.collaborator.id=?2 and c.laborDate>=?3 and c.laborDate<=?4 order by c.laborDate desc, c.collaborator.lastName desc")
    Page<LaborCostDetail> getPageableByCostCenterIdAndCollaboratorAndLaborDate(Integer costCenterId,
                                                                         Integer collaboratorId,
                                                                                     Date start, Date end,
                                                        Pageable pageable);

    @Query(value = "select count(c.id) from LaborCostDetail c where c.costCenter.id=?1 and c.collaborator.id=?2 and c.laborDate>=?3 and c.laborDate<=?4")
    Integer countAllByCostCenterIdAndCollaboratorAndLaborDates(Integer costCenterId,
                                                               Integer collaboratorId,
                                                               Date start, Date end);

    @Query(value = "select new com.ndl.erp.dto.LaborCostTotalHours(sum(c.hoursSimple), sum(c.hoursMedia), sum(c.hoursDouble)) from LaborCostDetail c " +
            " where c.costCenter.id=?1 and c.collaborator.id=?2 and c.laborDate>=?3 and c.laborDate<=?4 ")
    LaborCostTotalHours sumHoursByCostCenterIdAndCollaboratorAndLaborDates(Integer costCenterId,
                                                                           Integer collaboratorId,
                                                                           Date start, Date end);

    @Query(value = "select c from LaborCostDetail c  where c.laborDate>=?1 and " +
            "c.laborDate<=?2 order by c.laborDate desc, c.collaborator.lastName desc")
    Page<LaborCostDetail> getPageableByDate( Date start, Date end,
                                            Pageable pageable);

    @Query(value = "select count(c.id) from LaborCostDetail c where c.laborDate>=?1 and  c.laborDate<=?2")
    Integer countAllByDates(Date start, Date end);

    @Query(value = "select new com.ndl.erp.dto.LaborCostTotalHours(sum(c.hoursSimple), sum(c.hoursMedia), sum(c.hoursDouble)) from LaborCostDetail c " +
            " where c.laborDate>=?1 and  c.laborDate<=?2 ")
    LaborCostTotalHours sumHoursAllByDates(Date start, Date end);

    @Query(value = "SELECT new com.ndl.erp.dto.HmHdDTO(" +
            "cc, " +
            "c," +
            " max(lcd.costHour), " +
            "sum(lcd.hoursSimple), " +
            " sum(lcd.hoursMedia), " +
            " sum(lcd.hoursDouble), " +
            " sum(lcd.hoursDouble), sum(lcd.hoursMedia),  sum(lcd.hoursSimple), cur)  " +
            " from LaborCostDetail lcd, CostCenter cc, Collaborator c, Currency cur " +
            "where " +
            "lcd.laborDate>=?1 and  lcd.laborDate<=?2 " +
            "and cc.id = lcd.costCenter " +
            "and lcd.collaborator.id = c.id " +
            "and lcd.currency.id = cur.id " +
            "and (lcd.hoursDouble > 0 or lcd.hoursMedia > 0 or lcd.hoursSimple > 0) " +
            "GROUP BY c.id, cc.id, lcd.currency.id " +
            "ORDER BY c.name, c.lastName "
//            "ORDER BY cc.type, cc.id "
    )
    List<HmHdDTO> getLaborCostHhMm(Date start, Date end);

    @Query(value = "select c from LaborCostDetail c where c.laborCost.id in (?1)")
    List<LaborCostDetail> getDetailsByIds(List<Integer> ids);


}
