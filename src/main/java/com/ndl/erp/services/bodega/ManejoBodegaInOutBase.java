package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Producto;

public interface ManejoBodegaInOutBase {

    public String getManejoInventarioBodega(Bodega bodega, Producto producto);

    public String getManejoPrecioBodega(Bodega bodega);
}
