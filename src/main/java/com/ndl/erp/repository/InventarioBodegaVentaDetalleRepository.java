package com.ndl.erp.repository;


import com.ndl.erp.domain.InventarioBodegaVentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface InventarioBodegaVentaDetalleRepository extends JpaRepository<InventarioBodegaVentaDetalle, Integer> {
    @Query(value = "select inv from InventarioBodegaVentaDetalle inv where  inv.inventarioBodega.bodega.id = ?1 and inv.year =?2 and inv.mes = ?3 and inv.inventarioBodega.producto.id= ?4")
    public InventarioBodegaVentaDetalle findByBodegaAndYearAndMesAndProducto(Integer inventarioBodegaId, Integer year, String mes , Integer productoId);
}
