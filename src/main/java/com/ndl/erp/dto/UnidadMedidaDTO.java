package com.ndl.erp.dto;


import com.ndl.erp.domain.UnidadMedida;

import java.util.List;

public class UnidadMedidaDTO {

    private UnidadMedida current;
    private List<String> estados;

    public UnidadMedida getCurrent() {
        return current;
    }

    public void setCurrent(UnidadMedida current) {
        this.current = current;
    }

    public void setEstados(List<String> estadoList) {
        this.estados = estadoList;
    }

    public List<String> getEstados() {
        return estados;
    }
}