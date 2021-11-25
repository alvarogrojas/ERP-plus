package com.ndl.erp.repository;


import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollDetail;
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
public interface PayRollRepository extends JpaRepository<PayRoll, Integer> {

    @Query(value = "select p from  PayRoll p, PayRollDetail c where p.id=c.payRoll.id and c.collaborator.id=?1 " +
            "and p.start>=?2 and p.end<=?3" +
            " group by  p.id")
    Page<PayRoll> getFilterPageableByCollaboratorIdAndDates(Integer collaboratorId,
                                                            Date start, Date end,
                                                            Pageable pageable);

    @Query(value = "select count(p.id) from  PayRoll p, PayRollDetail c where p.id=c.payRoll.id and c.collaborator.id=?1  " +
            "and p.start>=?2 and p.end<=?3  " )
    Integer countAllByCollaboratorIdAndDates(
            Integer collaboratorId,
            java.util.Date start, java.util.Date end);



    @Query(value = "select c from PayRoll c where c.start>=?1 and c.end<=?2 " +
            "order by c.start desc")
    Page<PayRoll> getFilterPageableByDates(
                                                    Date start, Date end,
                                                    Pageable pageable);

    @Query(value = "select c from PayRoll c where c.start>=?1 and c.end<=?2")
    List<PayRoll> getByDates(
            Date start, Date end);

    @Query(value = "select c from PayRoll c where ((c.start between ?1 and ?2) or (c.end  between ?1 and ?2)) and c.status=?3 and c.inClosing=?4")
    List<PayRoll> getByDatesBetween(
            Date start, Date end, String status, Boolean inClosure);

    @Query(value = "select c from PayRoll c where ((c.start BETWEEN ?1 AND ?2) or (c.end  BETWEEN ?1 and ?2)) and c.status=?3 and c.inClosing=?4")
    List<PayRoll> getByDatesBetween1(
            Date start, Date end, String status, Boolean inClosure);


    @Query(value = "select count(c.id) from PayRoll c where  c.start>=?1 and c.end<=?2 " )
    Integer countAllByDates(
            java.util.Date start, java.util.Date end);


    @Query(value = "select c from PayRollDetail c where c.payRoll.id=?1 "
           )
    List<PayRollDetail> getPayRollDetailByParent(
            Integer payRollId);


    @Query(value = "select c from PayRoll c where  c.id=(select max(p.id) from PayRoll p) " )
    PayRoll getLastPayroll(
            );


    @Transactional
    @Modifying
    @Query(value = "update PayRoll p set p.inClosing=false  where p.id in (?1)")
    void updatePayRollsByIds(List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "update PayRoll p set p.statusClosing='CONGELADO'  where p.id in (?1)")
    void updatePayRollsToFrozenByIds(List<Integer> ids);




}