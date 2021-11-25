package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;

import java.util.List;

public class BodegaDTO {

    private Bodega current;
    private List<String> estados;
    private List<String> manejoPrecios;
    private List<String> manejosBodega;
    private List<String> facturableFlags;

    public List<String> getManejosBodega() {
        return manejosBodega;
    }

    public void setManejosBodega(List<String> manejosBodega) {
        this.manejosBodega = manejosBodega;
    }

    public List<String> getFacturableFlags() {
        return facturableFlags;
    }

    public void setFacturableFlags(List<String> facturableFlags) {
        this.facturableFlags = facturableFlags;
    }

    public List<String> getManejoPrecios() {
        return manejoPrecios;
    }

    public void setManejoPrecios(List<String> manejoPrecios) {
        this.manejoPrecios = manejoPrecios;
    }

    public Bodega getCurrent() {
        return current;
    }

    public void setCurrent(Bodega current) {
        this.current = current;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }
}

