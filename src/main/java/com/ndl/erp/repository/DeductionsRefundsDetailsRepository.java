package com.ndl.erp.repository;

import com.ndl.erp.domain.DeductionsRefunds;
import com.ndl.erp.domain.DeductionsRefundsDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeductionsRefundsDetailsRepository extends JpaRepository<DeductionsRefundsDetails, Integer> {


    @Query(value = "select c from DeductionsRefundsDetails c  where c.deductionRefund.id=?1 and  c.status='Activo' ")
    List<DeductionsRefundsDetails> getActiveByParentId(Integer id);

    @Query(value = "select max(c.indice) from DeductionsRefundsDetails c  where c.deductionRefund.id=?1 ")
    Integer getMaxId(Integer id);

    @Query(value = "select c.deductionRefund.id from DeductionsRefundsDetails c  where c.id=?1 ")
    Integer getIdDeductionRefund(Integer id);


}
