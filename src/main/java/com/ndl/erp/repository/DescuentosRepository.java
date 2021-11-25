package com.ndl.erp.repository;

import com.ndl.erp.domain.CategoriaDescuentos;
import com.ndl.erp.domain.Cotizacion;
import com.ndl.erp.domain.Descuentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface DescuentosRepository extends JpaRepository<Descuentos, Integer> {

    @Query(value = "select c from Descuentos c " +
            "where  c.tipoDescuento = ?1 " +
            "and c.estado='Activo' ")
    List<Descuentos> getDescuentosActivosByTipo(String tipo);


    @Query(value = "select c from Descuentos c " +
            "where  c.tipoDescuento = ?1 " +
            "and  c.referenciaId= ?2")
    List<Descuentos> getDescuentosByTipoAndReferenciaId(String tipo, Integer referenciaId);


    @Query(value = "select c from Descuentos c " +
            "where  c.tipoDescuento = ?1 " )
    List<Descuentos> getDescuentosByTipo(String tipo);


    @Query(value = "select c from Descuentos c " +
            "where c.estado='Activo' and c.porcentajeDescuento = " +
            " (select min(d.porcentajeDescuento) from Descuentos d " +
            "            where  d.estado='Activo' and d.tipoDescuento='Global')")
    List<Descuentos> getSinDescuento();

}
