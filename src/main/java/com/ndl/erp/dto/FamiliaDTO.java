package com.ndl.erp.dto;

import com.ndl.erp.domain.Familia;

import java.util.List;

public class FamiliaDTO {


    private Familia current;
    private List<String> estados;

    public Familia getCurrent() {
        return current;
    }

    public void setCurrent(Familia current) {
        this.current = current;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }
}


