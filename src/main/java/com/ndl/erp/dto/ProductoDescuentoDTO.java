package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;

public class ProductoDescuentoDTO {

    private  ProductoDescuento current;
    private List<String> estadoList;
    private List<Producto> productoList;

    public ProductoDescuento getCurrent() {
        return current;
    }

    public void setCurrent(ProductoDescuento current) {
        this.current = current;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }
}
