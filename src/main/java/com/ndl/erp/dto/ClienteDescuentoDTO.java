package com.ndl.erp.dto;

import com.ndl.erp.domain.ClienteDescuento;
import com.ndl.erp.domain.ProductoCategoria;

import java.util.List;

public class ClienteDescuentoDTO {

    private List<String> estadoList;
    private List<ProductoCategoria> productoCategoriaList;
    private ClienteDescuento current;

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<ProductoCategoria> getProductoCategoriaList() {
        return productoCategoriaList;
    }

    public void setProductoCategoriaList(List<ProductoCategoria> productoCategoriaList) {
        this.productoCategoriaList = productoCategoriaList;
    }

    public ClienteDescuento getCurrent() {
        return current;
    }

    public void setCurrent(ClienteDescuento current) {
        this.current = current;
    }
}
