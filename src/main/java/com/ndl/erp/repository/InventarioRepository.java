package com.ndl.erp.repository;

import com.ndl.erp.domain.CategoriaDescuentos;
import com.ndl.erp.domain.Inventario;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.dto.InventarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Query(value = "select inv from Inventario inv where inv.disponible = 1 " +
                   "and (inv.bodega.id = null or inv.bodega.id = 0 or inv.bodega.id = ?1) " +
                   "and  (inv.producto.codigo like %?2%  " +
                   "or inv.producto.codigoCabys like %?2%  " +
                  "or inv.producto.descripcionEspanol like %?2% " +
                   "or inv.barcode like %?2%) " +
                   "and inv.cantidadSaldo > 0 " +
                   "order by inv.fecha asc")
    public List<Inventario> findByBodegaAndProductoSalida(Integer bodegaId , String filter);

    @Query(value = "select p from Producto p where p.estado ='Activo' " +
              "and (p.codigo like %?1% " +
            "or p.codigoCabys like %?1% " +
            "or p.descripcionEspanol like %?1% )")
    public List<Producto> findByProductoAndCodigoEntrada(String filter);




    @Query(value = "select inv from Inventario inv where tienda.id=?1 and  inv.bodega.id = ?2 and inv.producto.id=?3 and inv.disponible =1 order by inv.fecha desc")
    public List<Inventario> findByTiendaAndBodegaAndProductoDesc(Integer tiendaId, Integer bodegaId , Integer productoId);


    @Query(value = "select inv from Inventario inv where inv.disponible = 1 " +
            "and (inv.bodega.id = null or inv.bodega.id = 0 or inv.bodega.id = ?1) " +
            "and inv.producto.id=?2 " +
            "and inv.barcode = ?3 " +
            "and inv.cantidadSaldo > 0 ")

    public Inventario findByBodegaAndBarcodeAndProductoAndSaldoAndDisponible(Integer bodegaId , Integer productoId, String Barcode);

    @Query(value = "select inv from Inventario inv where inv.disponible = 1 " +
            "and (inv.bodega.id = null or inv.bodega.id = 0 or inv.bodega.id = ?1) " +
            "and inv.producto.id=?2 " +
            "and inv.barcode =?3 "
            )
    public Inventario findByBodegaAndBarcodeAndProducto(Integer bodegaId , Integer productoId, String Barcode);



//    @Query(value = "select inv from Inventario inv where (inv.barcode like %?1%  or  inv.producto.codigo like %?1%) " +
//            "and (inv.bodega.id = null or inv.bodega.id = 0 or inv.bodega.id = ?2) " +
//            "and (inv.producto.id = null or inv.producto.id = 0 or inv.producto.id = ?3) ")
//    public List<Inventario> findByBarcodeAndBodegaAndProducto(String filter, Integer bodegaId , Integer productoId);

    @Query(value = "select inv from Inventario inv where (inv.barcode like %?1%  or  inv.producto.codigo like %?1%) ")
    public List<Inventario> findByBarcode(String filter);

    @Query(value = "select inv from Inventario inv where (inv.barcode like %?1%  or  inv.producto.codigo like %?1%) " +
            " and inv.bodega.id=?2")
    public List<Inventario> findByBarcodeAndBodega(String filter, Integer bodegaId);


    @Query(value = "select inv from Inventario inv where inv.barcode=?1 " +
            " and inv.bodega.id=?2")
    public Inventario findByCodigobarrasAndBodega(String barcode, Integer bodegaId);


//    @Query(value = "select new com.ndl.erp.dto.InventarioDTO(inv) from Inventario inv, CategoriaDescuentos d, Categoria c " +
//            "   where (inv.barcode like %?1%  or  inv.producto.codigo like %?1% or inv.producto.descripcionEspanol like %?1%) " +
//            " and inv.bodega.id=?2   ")
//    public List<InventarioDTO> getByFilterAndBodega(String filter, Integer bodegaId);
//
//
//    @Query(value = "select d from CategoriaDescuentos d, Categoria c " +
//            "where c.estado = 'Activo' " +
//            "and d.categoria.id = c.id " +
//            "and d.categoria.id in (select pc.categoria.id  from ProductoCategoria pc " +
//            "                   where pc.categoria.id = d.categoria.id " +
//            "                   and pc.estado = 'Activo' and  pc.producto.id = ?1) "
//    )
//
//    List<CategoriaDescuentos> getProductoCategoriaDescuentosByProductoAndCliente(Integer productoId, Integer clienteId);




    @Query(value = "select inv from Inventario inv, Producto p where inv.producto.id=p.id and" +
            " (?1 = null or ?1= '' or inv.barcode like %?1%  or  " +
            "p.codigo like %?1% or p.descripcionEspanol  like %?1%) " +
            "and (?2= null or ?2 = 0 or inv.bodega.id = ?2) " +
            "and (?3 = null or  ?3= 0 or (?3 = 1 and inv.cantidadSaldo > 0)) " +
            "and (?4= null or  ?4 =0  or inv.disponible=?4)")

    public Page<Inventario> findInventoryByBarcodeAndProductCodeAndAvailability(String filter, Integer bodegaId ,
                                                                                Integer existentes,
                                                                                Integer disponible, Pageable pageable);
//    @Query(value = "select inv from Inventario inv where (?1 = null or ?1= '' or inv.barcode like %?1%  or  inv.producto.codigo like %?1% or inv.producto.descripcionEspanol  like %?1%) " +
//            "and (?2= null or ?2 = 0 or inv.bodega.id = ?2) " +
//            "and (?3 = null or  ?3= 0 or (?3 = 1 and inv.cantidadSaldo > 0)) " +
//            "and (?4= null or  ?4 =0  or inv.disponible=?4)")
//    public Page<Inventario> findInventoryByBarcodeAndProductCodeAndAvailability(String filter, Integer bodegaId , Integer existentes, Integer disponible, Pageable pageable);
//
    @Query(value = "select count(inv.id) from Inventario inv, Producto p where inv.producto.id=p.id and " +
            " (?1 = null or ?1= '' or inv.barcode like %?1%  or  " +
            "p.codigo like %?1% or p.descripcionEspanol  like %?1%) " +
            "and (?2= null or ?2 = 0 or inv.bodega.id = ?2) " +
            "and (?3 = null or  ?3= 0 or (?3 = 1 and inv.cantidadSaldo > 0)) " +
            "and (?4= null or  ?4 =0  or inv.disponible=?4)")
    public Integer countInventoryByBarcodeAndProductCodeAndAvailability(String filter, Integer bodegaId , Integer existentes, Integer disponible);

//    @Query(value = "select count(inv.id) from Inventario inv where (?1 = null or ?1= '' or inv.barcode like %?1%  or  inv.producto.codigo like %?1% or inv.producto.descripcionEspanol  like %?1%) " +
//            "and (?2= null or ?2 = 0 or inv.bodega.id = ?2) " +
//            "and (?3 = null or  ?3= 0 or (?3 = 1 and inv.cantidadSaldo > 0)) " +
//            "and (?4= null or  ?4 =0  or inv.disponible=?4)")
//
//
//    public Integer countInventoryByBarcodeAndProductCodeAndAvailability(String filter, Integer bodegaId , Integer existentes, Integer disponible);

    Inventario getById(Integer id);
}
