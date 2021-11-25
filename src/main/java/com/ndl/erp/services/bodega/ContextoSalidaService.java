package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.InventarioBodegaRepository;
import com.ndl.erp.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.ndl.erp.constants.BodegaConstants.*;

@Component
public  class ContextoSalidaService {

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;
    @Autowired
    InventarioRepository inventarioRepository;

    private EstrategiaSalidaInventario strategy;
    @Autowired
    private EstrategiaSalidaConcretaPEPS estrategiaSalidaConcretaPEPS;
    @Autowired
    private EstrategiaSalidaConcretaUEPS estrategiaSalidaConcretaUEPS;
    @Autowired
    private EstrategiaSalidaConcretaSimple estrategiaSalidaConcretaSimple;

    @Autowired
    private ManejoBodegaInOutImpl manejoBodegaIngpro;

    public EstrategiaSalidaInventario getStrategy() {
        return strategy;
    }

    public void setStrategy(EstrategiaSalidaInventario strategy) {
        this.strategy = strategy;
    }

    public void setProductStrategy(Bodega bodega, Producto producto) throws Exception{

        String manejoBodega;

        if (producto != null) {

            manejoBodega = this.manejoBodegaIngpro.getManejoInventarioBodega(bodega, producto);

            //Seleccion del algoritmo de salida
            if (manejoBodega.equals(MANEJO_BODEGA_PEPS)) {

                this.setStrategy(estrategiaSalidaConcretaPEPS);

            } else if (manejoBodega.equals(MANEJO_BODEGA_UEPS)) {

                this.setStrategy(estrategiaSalidaConcretaUEPS);

            } else if (producto.getManejoBodega().equals(MANEJO_BODEGA_SIMPLE)) {

                this.setStrategy(estrategiaSalidaConcretaSimple);

            } else { //El algortimo salida default es simple

                this.setStrategy(estrategiaSalidaConcretaSimple);
            }
        } else {
            throw new GeneralInventoryException("El producto suministrado no es válido");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized void aplicarSalida(Tienda tienda, Integer ventaId, Integer ventaDetalleId, Bodega bodega, Producto producto, String barcode, Double cantidad, Double precioUnitario, Boolean isTraslado, Boolean isDevolucionProveedor, Boolean isSalidaPos) throws Exception{

        Inventario inventario;
        inventario = this.inventarioRepository.findByBodegaAndBarcodeAndProductoAndSaldoAndDisponible(bodega.getId(), producto.getId(), barcode);

        if (inventario == null){
            throw new GeneralInventoryException("Inventario no disponible, revisar existencias! Producto " + producto.getDescripcionEspanol() + " con codigo de barras " + barcode + " no tiene existencias en el inventario" );
        }

        InventarioBodega inventarioBodega= inventarioBodegaRepository.findByBodegaAndProducto(inventario.getBodega().getId(), inventario.getProducto().getId());


        BigDecimal bdCantidadCongelada = BigDecimal.valueOf(inventarioBodega.getCantidadCongelada());
        bdCantidadCongelada = bdCantidadCongelada.setScale(5,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal bdCantidadDisponible = BigDecimal.valueOf(inventarioBodega.getCantidadDisponible());
        bdCantidadDisponible = bdCantidadDisponible.setScale(5,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal bdCantidad = BigDecimal.valueOf(cantidad);
        bdCantidad = bdCantidad.setScale(5,BigDecimal.ROUND_HALF_EVEN);
//        if ((!isTraslado && !isDevolucionProveedor && !isSalidaPos && (inventarioBodega.getCantidadCongelada() >= cantidad)) || ((isTraslado || isDevolucionProveedor || isSalidaPos)  && (inventarioBodega.getCantidadDisponible() >= cantidad))) {
        if ((!isTraslado && !isDevolucionProveedor && !isSalidaPos
                && (bdCantidadCongelada.doubleValue() >= bdCantidad.doubleValue())) ||
                ((isTraslado || isDevolucionProveedor || isSalidaPos)
                        && ( bdCantidadDisponible.doubleValue()>= bdCantidad.doubleValue() )
//                        && (inventarioBodega.getCantidadDisponible() >= cantidad)
                )) {
            setProductStrategy(bodega, producto);
            //efectuar la salida del producto con el algoritmo seleccionado
            this.strategy.salidaInventario(tienda, ventaId, ventaDetalleId, bodega, producto, barcode, cantidad, precioUnitario, isTraslado, isDevolucionProveedor, isSalidaPos);
        } else {
            throw new GeneralInventoryException("No hay reservas suficientes del producto: " + producto.getDescripcionEspanol() + ", en la bodega: " + bodega.getName());
        }
    }

}
