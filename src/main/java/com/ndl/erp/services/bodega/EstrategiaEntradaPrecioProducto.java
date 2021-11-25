package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Inventario;


    public  interface EstrategiaEntradaPrecioProducto {
        public Inventario ingresoPrecioProducto(Inventario inventario, Double precioIngreso ) throws Exception;
    }


