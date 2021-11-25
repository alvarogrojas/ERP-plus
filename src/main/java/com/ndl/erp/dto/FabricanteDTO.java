package com.ndl.erp.dto;

import com.ndl.erp.domain.Fabricante;

import java.util.List;

public class FabricanteDTO {

    private Fabricante current;
    private List<String> estados;

    public Fabricante getCurrent() {
        return current;
    }

    public void setCurrent(Fabricante current) {
        this.current = current;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }
}
