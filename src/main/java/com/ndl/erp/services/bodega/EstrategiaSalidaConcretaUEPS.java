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

import java.sql.Date;
import java.util.List;

@Component
public class EstrategiaSalidaConcretaUEPS implements EstrategiaSalidaInventario {
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
        List<Inventario> listaArticulos;
        Double cantidadFaltante = cantidad;
        Double cantidadRebajadaItem = 0d;
        listaArticulos=  this.inventarioRepository.findByBodegaAndProductoSalida(bodega.getId(), producto.getCodigo());
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());


        if (listaArticulos != null) {
            for (Inventario inventario: listaArticulos) {
                if (cantidadFaltante == 0 ) break;
                if (inventario.getCantidadSaldo() <= cantidadFaltante){
                    cantidadFaltante -= inventario.getCantidadSaldo();
                    cantidadRebajadaItem = inventario.getCantidadSaldo();
                    inventario.setCantidadSalida(inventario.getCantidadSalida() + inventario.getCantidadSaldo());
                    inventario.setCostoSalida(precioUnitario);
                    inventario.setTotalSalida(inventario.getCantidadSalida() * inventario.getCostoSalida());
                    inventario.setDisponible(0);
                    inventario.setCantidadSaldo(0d);
                    inventario.setTotalSaldo(0d);

                } else {

                    inventario.setCantidadSalida(inventario.getCantidadSalida() + cantidadFaltante);
                    cantidadRebajadaItem = cantidadFaltante;
                    inventario.setCantidadSaldo(inventario.getCantidadEntrada() - inventario.getCantidadSalida());
                    inventario.setTotalSaldo(inventario.getCantidadSaldo() * inventario.getCostoSaldo());
                    inventario.setCostoSalida(precioUnitario);
                    inventario.setTotalSalida(inventario.getCantidadSalida() * inventario.getCostoSalida());
                    cantidadFaltante = 0d;

                }

                //incluir movimiento en el log de salidas en cada iteracion
                this.actualizaInventarioSalidaLog(bodega.getId(), ventaId, ventaDetalleId,inventario, cantidadRebajadaItem);

                //actualizar auditoria
                inventario.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
                inventario.setUsuarioUltimoCambio(userService.getCurrentLoggedUser());
                //actaulizar el item de inventario
                this.inventarioRepository.save(inventario);
            }

            if (cantidadFaltante > 0) {
                throw new GeneralInventoryException("El producto "  + producto.getDescripcionEspanol() +  " no cuenta con suficientes existencias!");
            } else {
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
            }

        } else {
            throw new GeneralInventoryException("El producto "  + producto.getDescripcionEspanol() +  " no se encuentra en el inventario!");
        }

    }
}
