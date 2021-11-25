package com.ndl.erp.repository;

import com.ndl.erp.domain.PayRollCollaboratorDetail;

import com.ndl.erp.dto.BalanceLaborCostDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;


@Component
public interface PayRollCollaboratorDetailRepository extends JpaRepository<PayRollCollaboratorDetail, Integer> {


    @Query(value = "select c from PayRollCollaboratorDetail c where c.collaborator.id=?1 and c.payRoll.id=?2 ")
    List<PayRollCollaboratorDetail> getPayRollDetailPayRollIdAndCollaboratorId(Integer colaboratorId, Integer payrollId);

    @Query(value = "select c from PayRollCollaboratorDetail c where c.payRoll.id=?1 ")
    List<PayRollCollaboratorDetail> getPayRollDetailPayRollIdAndCollaboratorId(Integer payrollId);


//    @Query(value = "select " +
//            "lcd.id, " +
//            "lcd.costsCenter.id, " +
//            "lcd.currency.id, " +
//            "lcd.costHour," +
//            " lcd.hoursSimple, " +
//            "lcd.hoursDouble," +
//            " lcd.hoursMedia," +
//            " lcd.laborDate," +
//            " lcd.costsCenter " +
//            "from PayRollCollaboratorDetail prcd  " +
//            "INNER JOIN LaborCostDetail lcd on prcd.collaborator.id = lcd.collaborator.id " +
//            "and lcd.laborDate BETWEEN prcd.startDate and prcd.endDate")

    @Query(value = "select " +
            "DISTINCT new com.ndl.erp.dto.BalanceLaborCostDetailDTO(" +
               "lcd.id, " +
            "lcd.costCenter.id, " +
            "lcd.currency.id, " +
            "lcd.costHour," +
            " lcd.hoursSimple, " +
            "lcd.hoursDouble," +
            " lcd.hoursMedia," +
            " lcd.laborDate," +
            " lcd.costCenter " +
            ") " +
            "from PayRollCollaboratorDetail prcd  " +
            "INNER JOIN LaborCostDetail lcd on prcd.collaborator.id = lcd.collaborator.id " +
            "and lcd.laborDate BETWEEN prcd.startDate and prcd.endDate " +
            "where prcd.payRoll.id in (?1)")
//    HashSet<BalanceLaborCostDetailDTO> getBalanceLaborCostDetailDTO(List<Integer> idsPayRoll);
    List<BalanceLaborCostDetailDTO> getBalanceLaborCostDetailDTO(List<Integer> idsPayRoll);




}
