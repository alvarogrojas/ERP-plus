package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.InventarioBodegaRepository;
import com.ndl.erp.repository.InventarioRepository;
import com.ndl.erp.repository.SalidaInventarioLogRepository;
import com.ndl.erp.services.UserService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class EstrategiaSalidaConcretaSimple implements EstrategiaSalidaInventario {
    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    SalidaInventarioLogRepository salidaInventarioLogRepository;

    @Autowired
    UserService userService;


    public void actualizaInventarioSalidaLog(Integer bodegaId, Integer ventaId, Integer ventaDetalleId, Inventario inventario, Double cantidadRebajadaItem){
        //incluir movimiento en el log de salidas en cada iteracion
        SalidaInventarioLog salidaInventarioLog;

        salidaInventarioLog = new SalidaInventarioLog();
        salidaInventarioLog.setBodegaId(bodegaId);
        salidaInventarioLog.setInventarioId(inventario.getId());
        salidaInventarioLog.setBarcode(inventario.getBarcode());
        salidaInventarioLog.setCantidad(cantidadRebajadaItem);
        salidaInventarioLog.setTipoSalida(inventario.getProducto().getManejoBodega());
        salidaInventarioLog.setVentaId(ventaId);
        salidaInventarioLog.setFechaHoraSalida(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        salidaInventarioLog.setUser(this.userService.getCurrentLoggedUser());
        salidaInventarioLog.setVentaDetalleId(ventaDetalleId);
        this.salidaInventarioLogRepository.save(salidaInventarioLog);
    }


    @Transactional(rollbackFor = {Exception.class})
    public void salidaInventario(Tienda tienda, Integer ventaId, Integer ventaDetalleId, Bodega bodega, Producto producto, String barcode, Double cantidad, Double precioUnitario, Boolean isTraslado, Boolean isDevolucionProveedor, Boolean isVentaPos) throws Exception{

        InventarioBodega inventarioBodega;
        Inventario articuloInventario;
        Double cantidadFaltante = cantidad;
        articuloInventario =  this.inventarioRepository.findByBodegaAndBarcodeAndProductoAndSaldoAndDisponible(bodega.getId(), producto.getId(), barcode);

        if (articuloInventario == null){
            throw new GeneralInventoryException("El producto "  + producto.getDescripcionEspanol() +  " no se encuentra disponible en el inventario!");
        }

        BigDecimal bdCantidadSaldo = BigDecimal.valueOf(articuloInventario.getCantidadSaldo());
        bdCantidadSaldo = bdCantidadSaldo.setScale(5,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal bdCantidadFaltante = BigDecimal.valueOf(cantidadFaltante);
        bdCantidadFaltante = bdCantidadFaltante.setScale(5,BigDecimal.ROUND_HALF_EVEN);


        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(articuloInventario.getBodega().getId(), articuloInventario.getProducto().getId());

        if ( inventarioBodega != null) {

                if (bdCantidadSaldo.doubleValue() >= bdCantidadFaltante.doubleValue()){
                    articuloInventario.setCostoSalida(precioUnitario);
                    articuloInventario.setCantidadSalida(articuloInventario.getCantidadSalida() + cantidad );
                    articuloInventario.setTotalSalida(articuloInventario.getCostoSalida() * articuloInventario.getCantidadSalida());
                    articuloInventario.setCantidadSaldo(articuloInventario.getCantidadEntrada() - articuloInventario.getCantidadSalida());
                    articuloInventario.setTotalSaldo(articuloInventario.getCantidadSaldo() * articuloInventario.getCostoSaldo());

                } else {
                    throw new GeneralInventoryException("El producto "  + producto.getDescripcionEspanol() +  " no cuenta con suficientes existencias: " + articuloInventario.getCantidadSaldo() );
                }

                //incluir movimiento en el log de salidas en cada iteracion
                this.actualizaInventarioSalidaLog(bodega.getId(), ventaId, ventaDetalleId, articuloInventario, cantidad);

                //actualizar auditoria
                articuloInventario.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
                articuloInventario.setUsuarioUltimoCambio(userService.getCurrentLoggedUser());
                //actaulizar el item de inventario
                this.inventarioRepository.save(articuloInventario);

                //actualizar la cantidad disponible y la cantidad congelada
                if (inventarioBodega != null) {
                    if (!isTraslado  &&  !isDevolucionProveedor && !isVentaPos) {
                        inventarioBodega.setCantidadCongelada(inventarioBodega.getCantidadCongelada() - cantidad);
                    }
                    inventarioBodega.setCantidadDisponible(inventarioBodega.getCantidadDisponible() - cantidad);
                    this.inventarioBodegaRepository.save(inventarioBodega);
                } else {
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el consolidado de la bodega!");
                }


        } else {
            throw new GeneralInventoryException("El producto "  + producto.getDescripcionEspanol() +  " no se encuentra en la bodega " + bodega.getName());
        }

    }
}
