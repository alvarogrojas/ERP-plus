package com.ndl.erp.repository;

import com.ndl.erp.domain.PayRollCollaboratorDeductionDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface PayRollCollaboratorDeductionDetailRepository extends JpaRepository<PayRollCollaboratorDeductionDetail, Integer> {



    @Query(value = "select c from PayRollCollaboratorDeductionDetail c where c.collaborator.id=?1 and c.payRoll.id=?2 ")
    List<PayRollCollaboratorDeductionDetail> getPayRollCollaboratorDeductionDetail
            (Integer colaboratorId, Integer payrollId);

    @Query(value = "select c from PayRollCollaboratorDeductionDetail c where  c.payRoll.id=?1 ")
    List<PayRollCollaboratorDeductionDetail> getPayRollCollaboratorDeductionDetail
            (Integer payrollId);



}
