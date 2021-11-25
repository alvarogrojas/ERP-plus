package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Inventario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EstrategiaConcretaEntradaPrecioMayor implements EstrategiaEntradaPrecioProducto {

    @Transactional(rollbackFor = {Exception.class})
    public Inventario ingresoPrecioProducto(Inventario inventario, Double precioIngreso) throws Exception {

        //Solo reemplaza si es mayor o igual
        if (precioIngreso==null) {
            return inventario;
        }
        if (inventario.getCostoEntrada()==null) {
            inventario.setCostoEntrada(precioIngreso);
        } else if (precioIngreso >=  inventario.getCostoEntrada()) {
            inventario.setCostoEntrada(precioIngreso);
        }
        return inventario;
    }
}
