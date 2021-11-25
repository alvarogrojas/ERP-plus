package com.ndl.erp.repository;

import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public interface PayRollDetailRepository extends JpaRepository<PayRollDetail, Integer> {

        @Query(value = "select c from PayRollDetail c where c.collaborator.id=?1 and c.payRoll.id=?2 ")
        List<PayRollDetail> getPayRollDetailPayRollIdAndCollaboratorId(Integer colaboratorId, Integer payrollId);


//    @Query(value = "select p from PayRollDetail c, PayRoll p where c.collaborator.id=?1 and c.startDate>=?2 and c.endDate<=?3 and p.id=c.payRoll.id  " +
//            " group by  p.id")
//    Page<PayRoll> getFilterPageableByCollaboratorIdAndDates(Integer collaboratorId,
//                                                            Date start, Date end,
//                                                            Pageable pageable);
//
//    @Query(value = "select count(p.id) from PayRollDetail c, PayRoll p where  c.collaborator.id=?1 and c.start>=?2 and c.end<=?3  and p.id=c.payRoll.id  " )
//    Integer countAllByCollaboratorIdAndDates(
//            Integer collaboratorId,
//            java.util.Date start, java.util.Date end);



}
