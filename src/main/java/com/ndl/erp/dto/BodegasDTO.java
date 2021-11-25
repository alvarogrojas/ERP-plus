package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import org.springframework.data.domain.Page;

import java.util.List;

public class BodegasDTO {

    private Page<Bodega> bodegas;
    private List<String> estados;
    private List<String> manejoPrecios;
    private List<String> manejosBodega;
    private List<String> facturableFlags;
    private Integer total;
    private Integer pagesTotal;

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

    public Page<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(Page<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public void setTotal(Integer countsBodegasByEstado) {
        this.total = countsBodegasByEstado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setPagesTotal(int i) {
        this.pagesTotal = i;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }
}

