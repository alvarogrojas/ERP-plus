package com.ndl.erp.dto;

import com.ndl.erp.domain.Recurso;

import java.util.List;

public class RecursosDTO {

    private List<Recurso> recursos;


    private String tipoReferencia;


    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public String getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }
}
