package com.ndl.erp.repository;

import com.ndl.erp.domain.PurchaseOrderClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PurchaseOrderClientRepository extends JpaRepository<PurchaseOrderClient, Integer> {


	@Query(value = "select c from PurchaseOrderClient c where c.orderNumber like %?1% or  c.client.name like %?1% or c.status like %?1%")
    List<PurchaseOrderClient> findUsingFilter(String filter);

	@Query(value = "select c from PurchaseOrderClient c where c.client.id=?2 and c.status=?1 order by id desc")
    List<PurchaseOrderClient> findByStatusAndClientId(String status, Integer id);

    @Query(value = "select c from PurchaseOrderClient c  where c.orderNumber like %?1% or  c.client.name like %?1% " +
            "or c.status like %?1% or c.consecutive like %?1% ")
    Page<PurchaseOrderClient> getFilterPageable(String filter,
                                                Pageable pageable);

    @Query(value = "select count(c.id) from PurchaseOrderClient c where c.orderNumber like %?1% or  c.client.name like %?1% " +
            "or c.status like %?1% or c.consecutive like %?1% ")
    Integer countAllByFilter(String filter);

    @Query(value = "select coalesce(max(c.consecutive), null, 0, max(c.consecutive)) from PurchaseOrderClient c")
    Integer getMaxConsecutive();

    PurchaseOrderClient getById(Integer id);


}
