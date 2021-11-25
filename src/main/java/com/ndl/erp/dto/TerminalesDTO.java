package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Terminal;

import java.io.Serializable;
import java.util.List;

public class TerminalesDTO implements Serializable {


    private List<String> estados;
    private List<Bodega> bodegas;

    private List<Terminal> terminales;

    public TerminalesDTO() {

    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }
    public List<Terminal> getTerminales() {
        return terminales;
    }

    public void setTerminales(List<Terminal> terminales) {
        this.terminales = terminales;
    }
}
