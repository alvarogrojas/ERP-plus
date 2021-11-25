package com.ndl.erp.repository;

import com.ndl.erp.domain.PayRollCollaboratorDeductionDetail;
import com.ndl.erp.domain.PayRollCollaboratorRefundDevolutionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface PayRollCollaboratorRefundDevolutionDetailRepository extends JpaRepository<PayRollCollaboratorRefundDevolutionDetail, Integer> {


    @Query(value = "select c from PayRollCollaboratorRefundDevolutionDetail c where c.payRoll.id=?1 and c.collaborator.id=?2 ")
    List<PayRollCollaboratorRefundDevolutionDetail> getByIdPayroolAndCollaboratorId(Integer idPayRoll,
                                                                                  Integer idCollaborator);

    @Query(value = "select c from PayRollCollaboratorRefundDevolutionDetail c where c.payRoll.id=?1  ")
    List<PayRollCollaboratorRefundDevolutionDetail> getByIdPayroolAndCollaboratorId(Integer idPayRoll);




}
