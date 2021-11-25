package com.ndl.erp.repository;

import com.ndl.erp.domain.Deductions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeductionRepository extends JpaRepository<Deductions, Integer> {


    @Query(value = "select c from Deductions c  where c.name like %?1% ")
    Page<Deductions> getFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Deductions c where  c.name like %?1% ")
    Integer countAllByFilter(String filter);


    @Query(value = "select c from Deductions c  where c.name like %?1% ")
    List<Deductions> getDeductionsByCollaborator(Integer id);


}
