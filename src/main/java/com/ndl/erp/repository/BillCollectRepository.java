package com.ndl.erp.repository;

import com.ndl.erp.domain.BillCollect;
import com.ndl.erp.domain.BillCollectDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Component
public interface BillCollectRepository extends JpaRepository<BillCollect, Integer> {

    @Query(value = "select c from BillCollectDetail c where c.billCollect.id in (?1)")
    List<BillCollectDetail> getDetailsByIds(List<Integer> ids);

    @Query(value = "select c from BillCollect c where c.id in (?1)")
    public List<BillCollect> findByIdIn(List<Integer> ids);

    @Query(value = "select c from BillCollect c where c.billDate>=?1 and c.billDate<=?2 and c.inClosing=?3 order by c.billNumber asc")
    public List<BillCollect> getByBillDates(Date start, Date end, Boolean inClosure);

    @Transactional
    @Modifying
    @Query(value = "update BillCollect p set p.inClosing=false  where p.id in (?1)")
    void updateBillCollectByIds(List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "update BillCollect p set p.statusClosing='CONGELADO'  where p.id in (?1)")
    void updateBillCollectToFrozenByIds(List<Integer> ids);

}
