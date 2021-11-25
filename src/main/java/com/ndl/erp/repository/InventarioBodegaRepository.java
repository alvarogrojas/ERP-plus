package com.ndl.erp.repository;

import com.ndl.erp.domain.InventarioBodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InventarioBodegaRepository extends JpaRepository<InventarioBodega, Integer> {

    @Query(value = "select inv from InventarioBodega inv where inv.bodega.id = ?1 and inv.producto.id=?2")
    public InventarioBodega findByBodegaAndProducto(Integer bodegaId , Integer productoId);

    @Query(value = "select c from InventarioBodega c where (c.producto.codigo like %?1% or c.producto.codigoCabys like %?1%) and c.bodega.id=?2")
    List<InventarioBodega> getProductsByFilter(String filter, Integer bodegaId);

    @Query(value = "select ib from InventarioBodega ib where ib.producto.codigo like %?1% or " +
            "ib.producto.codigoCabys like %?1% " +
            "or ib.producto.codigoCabys like %?1% " +
            "or ib.producto.descripcionEspanol like %?1% " +
            "or ib.producto.descripcionIngles like %?1% "
    )
    List<InventarioBodega> getInventarios(String filter);

    @Query(value = "select ib from InventarioBodega ib where ib.bodega.id =?2 and" +
            " (ib.producto.codigo like %?1% or " +
            "ib.producto.codigoCabys like %?1% " +
            "or ib.producto.codigoCabys like %?1% " +
            "or ib.producto.descripcionEspanol like %?1% " +
            "or ib.producto.descripcionIngles like %?1%) "
    )
    List<InventarioBodega> getInventariosByBodega(String filter, Integer bodegaId);


}
