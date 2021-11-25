package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.exceptions.GeneralInventoryException;
import org.springframework.stereotype.Component;

import static com.ndl.erp.constants.BodegaConstants.MANEJO_BODEGA_PRECIO_MAYOR;
import static com.ndl.erp.constants.BodegaConstants.MANEJO_BODEGA_SIMPLE;

@Component
public class ManejoBodegaInOutImpl implements ManejoBodegaInOutBase {

    public String getManejoInventarioBodega(Bodega bodega, Producto producto) {
        String  manejoBodega;

        if (producto!= null && producto.getManejoBodega() != null) {
            manejoBodega = producto.getManejoBodega();
        } else if (bodega!= null  && bodega.getManejoBodega()!= null) {
            manejoBodega = bodega.getManejoBodega();
        }  else { //El algortimo entrada default es simple
            manejoBodega = MANEJO_BODEGA_SIMPLE;
        }
        return  manejoBodega;
    }

    public String getManejoPrecioBodega(Bodega bodega) {
        String  manejoBodega;

        if (bodega == null) {
           throw  new GeneralInventoryException("No se encontró una estrategia de precios para la bodega");
        } else if (bodega.getManejoPrecio()!= null) {
            manejoBodega = bodega.getManejoPrecio();
        }  else { //El algortimo manejo de precios por defecto es
            manejoBodega = MANEJO_BODEGA_PRECIO_MAYOR;
        }
        return  manejoBodega;
    }

}
