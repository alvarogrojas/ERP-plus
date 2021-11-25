package com.ndl.erp.repository;

import com.ndl.erp.domain.ProductoDescuento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoDescuentoRepository extends JpaRepository<ProductoDescuento, Integer> {


    @Query(value = "select pd from ProductoDescuento pd " +
     "where pd.producto.id = ?1  and pd.estado = 'ACTIVA' " )
    List<ProductoDescuento> getProductoDescuentoByProductoAndEstado(Integer productoId);

    @Query(value = "select pd from ProductoDescuento pd " +
            "where pd.id = ?1  and pd.estado = 'ACTIVA' " )
    ProductoDescuento getProductoDescuentoByIdAndEstado(Integer productoDescuentoId);

    @Query(value = "select pd from ProductoDescuento pd ")
    List<ProductoDescuento> getAllProductoDescuento();

    @Query(value = "select pd from ProductoDescuento pd ")
    Page<ProductoDescuento> getAllPageableProductoDescuento(Pageable pageable);

    @Query(value = "select pd.id from ProductoDescuento pd ")
    Integer countAllPageableProductoDescuento();




}
