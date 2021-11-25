package com.ndl.erp.dto;

import com.ndl.erp.domain.Categoria;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.ProductoCategoria;

import java.util.List;

public class ProductoCategoriaListDTO {

    private List<Producto> productoList;

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }


}
