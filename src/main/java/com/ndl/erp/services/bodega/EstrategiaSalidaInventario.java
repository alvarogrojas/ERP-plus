package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.Tienda;

public  interface EstrategiaSalidaInventario {
    public void salidaInventario(Tienda tienda, Integer ventaId, Integer ventaDetalleId, Bodega bodega, Producto producto, String barcode, Double cantidad, Double precioUnitario, Boolean isTraslado,  Boolean isDevolucionProveedor, Boolean isSalidaPos) throws Exception;
}
