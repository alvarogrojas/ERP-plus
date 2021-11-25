package com.ndl.erp.repository;

import com.ndl.erp.domain.ProductoFamiliaAtributo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoFamiliaAtributoRepository extends JpaRepository<ProductoFamiliaAtributo, Integer> {

    @Query(value = "select pfa from ProductoFamiliaAtributo pfa where pfa.atributo.id = ?1 and pfa.producto.id = ?2 and atributoDetalle.id = ?3 ")
    ProductoFamiliaAtributo findAtributosProductoByAtributoProductoAndDetalle(Integer idAtributo, Integer idProducto, Integer idDetalleAtributo);
}
