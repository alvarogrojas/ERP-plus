package com.ndl.erp.repository;


import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ProductCabys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProductCabysRepository extends JpaRepository<ProductCabys, Integer> {

    @Query(value = "select c from ProductCabys c where c.codigoCabys=?1 ")
    ProductCabys getByCodigoCabys(String codigoCabys);


    @Query(value = "select c from ProductCabys c where c.cabys.id=?1")
    ProductCabys getByCabysId(Integer cabysId);

    @Query(value = "select c from ProductCabys c where c.descripcion like %?1% or c.codigoCabys like %?1% ")
    List<ProductCabys> getByFilter(String filter);

    @Query(value = "select c from ProductCabys c where c.id=?1")
    ProductCabys getById(Integer id);

    @Query(value = "select c from ProductCabys c where c.id in (?1)")
    public List<ProductCabys> findByIdIn(List<Integer> ids);
}
