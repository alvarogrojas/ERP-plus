package com.ndl.erp.dto;

import com.ndl.erp.domain.CategoriaDescuentos;
import com.ndl.erp.domain.Descuentos;
import com.ndl.erp.domain.Inventario;

import java.util.List;

public class InventarioDTO {
    public List<Inventario>  inventarios;

    public Inventario current;

    List<Descuentos> descuentos;

    public InventarioDTO() {}


    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }


    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }

    public Inventario getCurrent() {
        return current;
    }

    public void setCurrent(Inventario current) {
        this.current = current;
    }
}
