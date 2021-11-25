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

import static com.ndl.erp.constants.ParseCargaArchivoConstants.IMPORT_ACUMULAR_INVENTARIO;
import static com.ndl.erp.constants.ParseCargaArchivoConstants.IMPORT_REEMPLAZAR_INVENTARIO;


@Component
public class EstrategiaEntradaConcretaSimple implements EstrategiaEntradaInventario {
    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    ContextoEntradaPrecioProductoService contextoEntradaPrecioProductoService;


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
        inventario.setBarcode(barcode);
        inventario.setDisponible(1);

        return this.inventarioRepository.save(inventario);
    }


    @Transactional(rollbackFor = {Exception.class})
    public Inventario entradaInventario(Tienda tienda, Bodega bodega, Producto producto,String barcode, Double cantidad, Double costo, Boolean isDevolucion, String modoCargaInventario) throws Exception{

        Inventario inventario;
        inventario = this.inventarioRepository.findByBodegaAndBarcodeAndProducto(bodega.getId(), producto.getId(), barcode);

        //En el algoritmo simple reutiliza el inventario de un producto si existe
        if (inventario == null) {
           inventario = this.crearInventario(tienda, bodega, producto, barcode, cantidad, costo);
        } else {
           if (!isDevolucion) {
               if (modoCargaInventario.equals(IMPORT_REEMPLAZAR_INVENTARIO))  {
                   if (cantidad >= inventario.getCantidadEntrada()) {
                       inventario.setCantidadEntrada(cantidad);
                   }
                   //Felix Saborio Matamoros: 5/10/2021 Correccion a issue  #548 reemplazo de precio costo entrada inventario
                   inventario.setCostoEntrada(costo);

               } else if (modoCargaInventario.equals("") || modoCargaInventario.equals(IMPORT_ACUMULAR_INVENTARIO)){
                   inventario.setCantidadEntrada(inventario.getCantidadEntrada() + cantidad);
               }
           } else {
               inventario.setCantidadSalida(inventario.getCantidadSalida() - cantidad);
           }

           //Solo usa algoritmo de precios si la bandera de carga de inventario es nula o si esta em modo acumular inventario
           if (modoCargaInventario.equals("") || modoCargaInventario.equals(IMPORT_ACUMULAR_INVENTARIO)) {
               this.contextoEntradaPrecioProductoService.aplicarEntradaPrecioProducto(inventario, costo);
           }

           inventario.setTotalEntrada(inventario.getCantidadEntrada() * inventario.getCostoEntrada());
           inventario.setCantidadSaldo(inventario.getCantidadEntrada() - inventario.getCantidadSalida());
           inventario.setCostoSaldo(inventario.getCostoEntrada());
           inventario.setTotalSaldo(inventario.getCantidadSaldo() * inventario.getCostoSaldo());
           inventario.setUsuarioUltimoCambio(userService.getCurrentLoggedUser());
           inventario.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
           inventario.setFecha(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));

           this.inventarioRepository.save(inventario);
        }

        if (inventario == null){
            throw new GeneralInventoryException("Ocurrió un error, no se pudo crear el inventario simple para el producto: " + producto.getDescripcionEspanol());
        }

        return inventario;
    }
}


