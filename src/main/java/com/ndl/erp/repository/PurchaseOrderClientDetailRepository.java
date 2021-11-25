package com.ndl.erp.repository;


import com.ndl.erp.domain.PurchaseOrderClientDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PurchaseOrderClientDetailRepository extends JpaRepository<PurchaseOrderClientDetail, Integer> {


//	@Query(value = "select c from PurchaseOrderClient c where c.orderNumber like %?1% or  c.client.name like %?1% or c.status like %?1%")
//    List<PurchaseOrderClient> findUsingFilter(String filter);
//
//	@Query(value = "select c from PurchaseOrderClient c where c.client.id=?2 and c.status=?1")
//    List<PurchaseOrderClient> findByStatusAndClientId(String status, Integer id);
//
//    @Query(value = "select c from PurchaseOrderClient c  where c.orderNumber like %?1% or  c.client.name like %?1% " +
//            "or c.status like %?1% ")
//    Page<PurchaseOrderClient> getFilterPageable(String filter, Pageable pageable);
//
//    @Query(value = "select count(c.id) from PurchaseOrderClient c where c.orderNumber like %?1% or  c.client.name like %?1% " +
//            "or c.status like %?1% ")
//    Integer countAllByFilter(String filter);

    @Query(value = "select c.serviceCabysId from PurchaseOrderClientDetail c where c.parent.id=?1 and type='Servicio'")
    List<Integer> getServiceDetail(Integer parentId);

    @Query(value = "select c.inventarioItemId from PurchaseOrderClientDetail c where c.parent.id=?1 and type='Producto'")
    List<Integer> getProductoDetail(Integer parentId);

    @Query(value = "select c from PurchaseOrderClientDetail c where c.id=?1")
    PurchaseOrderClientDetail getById(Integer id);


}
