package com.ndl.erp.repository;


import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.MonthlyClosureBillCollect;
import com.ndl.erp.domain.MonthlyClosureBillPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public interface MonthlyClosureBillPayRepository extends JpaRepository<MonthlyClosureBillPay, Integer> {


//    @Transactional
//    @Modifying
//    @Query(value = "delete from MonthlyClosureBillPay bp where bp.billPay.id=?1")
//    void deleteByBillPayId(Integer bpId);

    @Query(value = "select bp from MonthlyClosureBillPay bp where bp.billPay.id=?1")
    MonthlyClosureBillPay getBy(Integer id);


    MonthlyClosureBillPay findByBillPay(BillPay billPay);
}