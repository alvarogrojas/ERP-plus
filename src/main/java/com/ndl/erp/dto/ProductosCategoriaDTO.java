package com.ndl.erp.dto;

import com.ndl.erp.domain.Categoria;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.User;

import java.util.List;

public class ProductosCategoriaDTO {

        private Categoria categoria;
        private List<Producto>  productos;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
