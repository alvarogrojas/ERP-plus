package com.ndl.erp.repository;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Department;
import com.ndl.erp.domain.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(value = "select c from Department c where c.name like %?1% ")
    Page<Department> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Department c where c.name like %?1% ")
    public Integer countAllByFilter(String filter);


}
