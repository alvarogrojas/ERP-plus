package com.ndl.erp.repository;



import com.ndl.erp.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query(value = "select c from Bank c where c.name like %?1% "
    )
    List<Bank> getByFilter(String filter);


}
