package com.ndl.erp.repository;


import com.ndl.erp.domain.BodegaIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BodegaIngresoRepository extends JpaRepository<BodegaIngreso, Integer> {

//    @Query(value = "select c from Bank c where c.name like %?1% "
//    )
//    List<Bodega> getByFilter(String filter);


    @Query(value = "select c from BodegaIngreso c where c.billId=?1"
    )
    List<BodegaIngreso> getByBillPayId(Integer billPayId);


    @Query(value = "select c from BodegaIngreso c where c.billId=?1 and c.billDetailId=?2 and status<>'Borrado' and c.parentType=?3")
    BodegaIngreso getBodegaIngreso(Integer billPayId, Integer billPayDetailId, String parentType);


}
