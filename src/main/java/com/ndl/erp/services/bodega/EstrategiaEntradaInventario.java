package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Inventario;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.Tienda;

public  interface EstrategiaEntradaInventario {
    public Inventario entradaInventario(Tienda tienda, Bodega bodega, Producto producto, String barcode, Double cantidad, Double costo, Boolean isDevolucion, String modoCargaInventario) throws Exception;
}
