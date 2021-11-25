package com.ndl.erp.repository;

import com.ndl.erp.domain.PurchaseOrderProvider;
import com.ndl.erp.dto.SchedulePurchaseOrderProviderWeekDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public interface PurchaseOrderProviderRepository extends JpaRepository<PurchaseOrderProvider, Integer> {


	@Query(value = "select c from PurchaseOrderProvider c where c.orderNumber like %?1% or  c.sourceName like %?1% or c.status like %?1%")
    List<PurchaseOrderProvider> findUsingFilter(String filter);

    @Query(value = "select c from PurchaseOrderProvider c  where c.orderNumber like %?1% or  c.sourceName like %?1% " +
            "or c.targetName like %?1% or  c.sourceAttention like %?1% or  c.status like %?1% ")
    Page<PurchaseOrderProvider> getFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from PurchaseOrderProvider c where c.orderNumber like %?1% or  c.sourceName like %?1% " +
            "or c.targetName like %?1% or  c.sourceAttention like %?1% or  c.status like %?1% ")
    Integer countAllByFilter(String filter);


    @Query(value = "select " +
            "new com.ndl.erp.dto.SchedulePurchaseOrderProviderWeekDTO(i)" +
            "FROM PurchaseOrderProvider i  WHERE i.datePay >= ?1 and i.datePay <= ?2 and (i.status='Aprobado')"
    )
    List<SchedulePurchaseOrderProviderWeekDTO> getAllBillPayByDates(Date date, Date date1);
}
