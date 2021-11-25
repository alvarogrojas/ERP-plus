package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Inventario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EstrategiaConcretaEntradaPrecioActualizado implements EstrategiaEntradaPrecioProducto {


        @Transactional(rollbackFor = {Exception.class})
        public Inventario ingresoPrecioProducto(Inventario inventario, Double precioIngreso) throws Exception {

            //Solo reemplaza por el nuevo precio que viene
            if (precioIngreso==null) {
                return inventario;
            }

            inventario.setCostoEntrada(precioIngreso);
            return inventario;
        }
    }
