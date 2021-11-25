package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.InventarioBodegaRepository;
import com.ndl.erp.repository.InventarioRepository;
import com.ndl.erp.services.UserService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;


@Component
public class EstrategiaEntradaConcretaUEPS implements EstrategiaEntradaInventario {
    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;


    @Autowired
    UserService userService;



    public Inventario crearInventario(Tienda tienda, Bodega bodega, Producto producto, String barcode, Double cantidad, Double costo){

        Inventario inventario = new Inventario();

        inventario.setId(0);
        inventario.setProducto(producto);
        inventario.setCantidadSalida(0d);
        inventario.setCostoSalida(0d);
        inventario.setTotalSalida(0d);
        inventario.setFecha(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        inventario.setTienda(tienda);
        inventario.setCantidadEntrada(cantidad);
        inventario.setCostoEntrada(costo);
        inventario.setTotalEntrada(inventario.getCantidadEntrada() * inventario.getCostoEntrada());
        inventario.setCantidadSaldo(inventario.getCantidadEntrada());
        inventario.setCostoSaldo(inventario.getCostoEntrada());
        inventario.setTotalSaldo(inventario.getCantidadSaldo() * inventario.getCostoSaldo());
        inventario.setUsuarioUltimoCambio(userService.getCurrentLoggedUser());
        inventario.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        inventario.setBodega(bodega);
        inventario.setDisponible(1);

        this.inventarioRepository.save(inventario);
        return inventario;
    }


    @Transactional(rollbackFor = {Exception.class})
    public Inventario entradaInventario(Tienda tienda, Bodega bodega, Producto producto, String barcode,  Double cantidad, Double costo, Boolean isDevolucion, String modoCargaInventario) throws Exception{

        Inventario inventario;

        //En el algoritmo ueps siempre se crea el inventario

        inventario = crearInventario(tienda, bodega, producto, barcode, cantidad, costo);

        if (inventario == null){
            throw new GeneralInventoryException("Ocurrió un error, no se pudo crear el inventario PEPS para el producto!");
        }

        return inventario;
    }
}


