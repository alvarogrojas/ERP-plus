package com.ndl.erp.repository;

import com.ndl.erp.domain.EntradasInventario;
import com.ndl.erp.dto.EntradasSalidasDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface EntradasInventarioRepository extends JpaRepository<EntradasInventario, Integer>  {



    /*select   inv.id,
    coalesce(salidas.costs_center_id, null, entradas.costs_center_id, 0) costs_center_id,
    entradas.cantidad_entrada,
    salidas.cantidad_salida,
            (entradas.cantidad_entrada  - salidas.cantidad_salida) cantidad_total,
    entradas.costo_entrada,
    salidas.costo_salida,
            (entradas.costo_entrada - salidas.costo_salida) costo_total,
            (entradas.cantidad_entrada * entradas.costo_entrada) total_entrada,
            (salidas.cantidad_salida * salidas.costo_salida) total_salida,
            ((entradas.cantidad_entrada * entradas.costo_entrada) - (salidas.cantidad_salida * salidas.costo_salida))  total


    from inventario inv
    left outer join (Select  ei.costs_center_id, ei.inventario_id id,
                     sum(ei.cantidad) cantidad_entrada,
    sum(ei.costo) costo_entrada
    from entradas_inventario ei
    group by ei.inventario_id, ei.costs_center_id) entradas
    on inv.id = entradas.id

    left outer join (Select  si.costs_center_id,
                     si.inventario_id id,
                     sum(si.cantidad) cantidad_salida ,
    sum(si.costo) costo_salida
    from salidas_inventario si
    group by si.inventario_id, si.costs_center_id) salidas
    on inv.id = salidas.id

    group by inv.id,
    coalesce(salidas.costs_center_id, null, entradas.costs_center_id, 0);*/

    //Consulta para movimientos de entrada y salida de inventario

    /*where (si.costs_center_id = 2164  and si.fecha >= current_date() and si.fecha <= current_date())
    or  (ei.costs_center_id = 2164  and  ei.fecha >= current_date() and  ei.fecha <= current_date())*/


    @Query(value = "select   new com.ndl.erp.dto.EntradasSalidasDTO(inv, \n" +
            "        coalesce(si.costsCenterId, null, ei.costsCenterId, 0),\n" +
            "        sum(ei.cantidad),  \n" +
            "        sum(si.cantidad), \n" +
            "        (sum(ei.cantidad)  - sum(si.cantidad)), \n" +
            "        avg(ei.costo),\n" +
            "        avg(si.costo),\n" +
            "        (avg(si.costo) - avg(ei.costo)), \n" +
            "        (sum(ei.cantidad) * avg(ei.costo)),\n" +
            "        (sum(si.cantidad) * avg(si.costo)),\n" +
            "        (sum(ei.cantidad) * avg(ei.costo)) - (sum(si.cantidad) * avg(si.costo)) )\n" +
            "         \n" +
            "\n" +
            "from Inventario inv\n" +
            " left outer join EntradasInventario ei\n" +
            "                 on inv.id = ei.inventario.id \n" +
            "               \n" +
            " left outer join SalidasInventario si\n" +
            "                 on inv.id = si.inventario.id\n" +
            " where ((?1 = null or ?1 = 0 or si.costsCenterId = ?1)  and (?2 = null  or si.fecha >= ?2) and (?3 = null  or si.fecha <= ?3) )               \n" +
            " or ((?1 = null or ?1 = 0 or ei.costsCenterId = ?1) and (?2 = null  or ei.fecha >= ?2) and (?3 = null  or ei.fecha <= ?3) )               \n" +
          /*  " and (?2 = null  or ei.fecha >= ?2)               \n" +
            " and (?3 = null  or ei.fecha <= ?3)               \n" +
            " and (?2 = null  or si.fecha >= ?2)               \n" +
            " and (?3 = null  or si.fecha <= ?3)               \n" +*/
            " group by inv.id, \n" +
            "        coalesce(si.costsCenterId, null,ei.costsCenterId, 0)  "
    )

    List<EntradasSalidasDTO> getEntradasSalidasByDatesAndCostCenter(Integer costCenterId,
                                                                    Date start,
                                                                    Date end);



}
