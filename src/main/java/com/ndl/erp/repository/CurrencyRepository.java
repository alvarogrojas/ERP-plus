package com.ndl.erp.repository;


import com.ndl.erp.domain.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {


//	@Query(value = "select c from Currency c where c.name like %?1% ")
//    List<Currency> findUsingFilter(String filter);


    @Query(value = "select c from Currency c ")
    List<Currency> findAllCurrency();

    @Query(value = "select c from Currency c where c.name like %?1% ")
    Page<Currency> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Currency c where c.name like %?1% ")
    public Integer countAllByFilter(String filter);


    @Query(value = "select c from Currency c where c.isDefault!=true ")
    List<Currency> getNotSystemCurrencies();


    @Query(value = "select c from Currency c where c.isDefault=true ")
    Currency getSystemCurrency();
}
