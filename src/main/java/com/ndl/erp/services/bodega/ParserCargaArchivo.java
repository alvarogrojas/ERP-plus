package com.ndl.erp.services.bodega;

import com.ndl.erp.dto.ProductoInventarioDTO;

public interface ParserCargaArchivo {

      public ProductoInventarioDTO parserCargaArchivoCSV(String path) throws Exception;

}
