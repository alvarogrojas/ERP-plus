package com.ndl.erp.repository;

import com.ndl.erp.domain.CategoriaDescuentos;
import com.ndl.erp.domain.Descuentos;
import com.ndl.erp.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductoRepository extends JpaRepository<Producto, Integer> {


    @Query(value = "select p from Producto p where p.codigo like %?1% or p.codigoCabys like %?1% or p.codigoCabys like %?1% ")
    List<Producto> getProducto(String filter);

    @Query(value = "select p from Producto p where p.id = ?1")
    Producto findProductoById(Integer id);


    @Query(value = "select p from Producto p where p.codigo = ?1 and p.sku = ?2")
    Producto findProductoByCodigoAndSku(String codigo, String sku);

    @Query(value = "select p from Producto p where p.codigo = ?1")
    Producto findProductoByCodigo(String codigo);

    @Query(value = "select p from Producto p where p.codigo is not null")
    List<Producto> getProductoByEstado();

    @Query(value = "select count(p.id) from Producto p where UPPER(p.codigo) =UPPER(?1)")
    Integer countProductoByCodigo(String codigo);

    @Query(value = "select p from Producto p where (?1 = '' or ?1 = null " +
            "or p.codigo like %?1% or p.descripcionEspanol like %?1% " +
            "or p.descripcionIngles like %?1%)  " +
            "and (?2 = null or ?2 = '' or p.estado  = ?2) "
    )
    Page<Producto> getProductoByFilterAndEstado(String filter, String estado, Pageable pageable);

    @Query(value = "select count(p.id) from Producto p where (?1 = '' or ?1 = null " +
            "or p.codigo like %?1% or p.descripcionEspanol like %?1% " +
            "or p.descripcionIngles like %?1%)  " +
            "and (?2 = null or ?2 = '' or p.estado  = ?2) "
    )
    Integer countProductoByFilterAndEstado(String filter, String estado);



    @Query(value = "select d from CategoriaDescuentos d, Categoria c " +
            "where c.estado = 'Activo' " +
            "and d.categoria.id = c.id " +
            "and d.categoria.id in (select pc.categoria.id  from ProductoCategoria pc " +
            "                   where pc.categoria.id = d.categoria.id " +
            "                   and pc.estado = 'Activo' and  pc.producto.id = ?1) "
    )

    List<CategoriaDescuentos> getProductoCategoriaDescuentosByProductoAndCliente(Integer productoId, Integer clienteId);


    @Query(value = "select d from Descuentos d, Categoria c " +
            "where c.estado = 'Activo' " +
            "and d.referenciaId = c.id  and d.tipoDescuento='Categoria'" +
            "and d.estado = 'Activo'  " +
            "and d.referenciaId in (select pc.categoria.id  from ProductoCategoria pc " +
            "                   where pc.categoria.id = d.referenciaId " +
            "                   and pc.estado = 'Activo' and  pc.producto.id = ?1) "
    )

    List<Descuentos> getDescuentosCategoriaByProducto(Integer productoId);


    @Query(value = "select p from Producto p where (?1 = '' or ?1 = null " +
            "or p.codigo like %?1% or p.descripcionEspanol like %?1% " +
            "or p.descripcionIngles like %?1%)  "
    )
    List<Producto> findProductoByFilter(String filter);
}
