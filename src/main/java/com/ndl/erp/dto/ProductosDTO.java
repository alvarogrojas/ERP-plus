package com.ndl.erp.dto;

import com.ndl.erp.domain.Inventario;
import com.ndl.erp.domain.Producto;

import java.util.List;



public class ProductosDTO {


    private List<Producto> productos;
    private List<Inventario> inventarios;


    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }
}
