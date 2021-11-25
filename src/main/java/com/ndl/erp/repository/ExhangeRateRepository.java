package com.ndl.erp.repository;

import com.ndl.erp.domain.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExhangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    @Query(value = "select c from ExchangeRate c where c.status='Activo' ")
    List<ExchangeRate> getActivoExchangeRates();

    @Query(value = "select c from ExchangeRate c where c.status='Activo' and c.currency.id = ?1")
    List<ExchangeRate> getActivoExchangeRatesByCurrencyId(Integer currencyId);


    @Query(value = "select c from ExchangeRate c where c.id =?1 ")
    ExchangeRate getExchangeRateById(Integer id);

    @Query(value = "select c from ExchangeRate c where (c.createdDate >= ?1 and c.createdDate <=?2 )" +
            " and (?3 = null or ?3 = '' or c.status =?3) ")

    Page<ExchangeRate> getFilterPageableExchangeRateByDateAndStatus(Date start, Date end,
                                                                    String status,
                                                                     Pageable pageable);


    @Query(value = "select count(c.id) from ExchangeRate c where (c.createdDate >= ?1 and c.createdDate <=?2 )" +
            " and (?3 = null or ?3 = '' or c.status = ?3) ")

    Integer countFilterPageableExchangeRateByDateAndStatus(Date start,
                                                           Date end,
                                                           String status);


}
