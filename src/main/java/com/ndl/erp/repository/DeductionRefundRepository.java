package com.ndl.erp.repository;

import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.DeductionsRefunds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeductionRefundRepository extends JpaRepository<DeductionsRefunds, Integer> {


    @Query(value = "select c from DeductionsRefunds c  where c.name like %?1% ")
    Page<DeductionsRefunds> getFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from DeductionsRefunds c where  c.name like %?1% ")
    Integer countAllByFilter(String filter);


    @Query(value = "select c from DeductionsRefunds c where c.collaborator.id=?1")
    List<DeductionsRefunds> getDeductionsRefundsByCollaboratorId(Integer id);
}
