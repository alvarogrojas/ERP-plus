package com.ndl.erp.repository;


import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ServiceCabys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface InventarioItemRepository extends JpaRepository<InventarioItem, Integer> {

    @Query(value = "select c from InventarioItem c where c.codigoCabys=?1 ")
    InventarioItem getByCodigoCabys(String codigoCabys);

    @Query(value = "select c from InventarioItem c where c.productId=?1 and c.bodega.id=?2 ")
    InventarioItem getByProductId(Integer productId, Integer bodegaId);

//
//    @Query(value = "select c from InventarioItem c where c.cabys.id=?1")
//    InventarioItem getByCabysId(Integer cabysId);

    @Query(value = "select c from InventarioItem c where c.descripcion like %?1% or c.codigoCabys like %?1% ")
    List<InventarioItem> getByFilter(String filter);

    @Query(value = "select c from InventarioItem c where c.id=?1")
    InventarioItem getById(Integer id);

    @Query(value = "select c from InventarioItem c where c.id in (?1)")
    public List<InventarioItem> findByIdIn(List<Integer> ids);

    @Query(value = "select c from InventarioItem c where c.codigoIngpro=?1 and c.bodega.id=?2 ")
    InventarioItem getByCodigoIngproAndBodegaId(String codigoIngpro, Integer id);

    @Query(value = "select c from InventarioItem c where c.bodega.id=?1 and (c.codigoIngpro like %?2% or c.codigoCabys like %?2% ) ")
    Page<InventarioItem> getInventarioItemByBodegaIdAndFilter(Integer id, String filter, Pageable pageable);

    @Query(value = "select count(c.id) from InventarioItem c where c.bodega.id=?1 and (c.codigoIngpro like %?2% or c.codigoCabys like %?2% ) ")
    Integer countByBodegaIdAndFilter(
            Integer id, String filter);

    @Query(value = "select c from InventarioItem c where (c.codigoIngpro like %?1% or c.codigoCabys like %?1%) ")
    Page<InventarioItem> getInventarioItemByFilter(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from InventarioItem c where (c.codigoIngpro like %?1% or c.codigoCabys like %?1% ) ")
    Integer countByFilter(
            String filter);
}
