package com.ndl.erp.repository;


import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Taxes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;


@Component
public interface TaxesRepository extends JpaRepository<Taxes, Integer> {

    @Query(value = "select c from Taxes c where c.name like %?1% ")
    Page<Taxes> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Taxes c where c.name like %?1% ")
    Integer countAllByFilter(String filter);
}
