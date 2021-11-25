package com.ndl.erp.repository;

import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.ProductoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

   public interface ProductoCategoriaRepository  extends JpaRepository<ProductoCategoria, Integer> {
      List<ProductoCategoria> findAll();

      @Query(value = "select c from ProductoCategoria c where (c.producto.id=?1) " +
              " and (c.categoria.id = ?2) ")
      ProductoCategoria getProductoCategoria(Integer productoId, Integer categoriaId);

      @Query(value = "select c.producto from ProductoCategoria c where  " +
              " (c.categoria.id = ?1) ")
      List<Producto> getProductosCategorias(Integer categoriaId);

      ProductoCategoria getById(Integer id);


   }
