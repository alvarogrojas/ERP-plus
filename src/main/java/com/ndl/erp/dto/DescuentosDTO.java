package com.ndl.erp.dto;

import com.ndl.erp.domain.CategoriaDescuentos;
import com.ndl.erp.domain.Descuentos;

import java.util.ArrayList;
import java.util.List;

public class DescuentosDTO {

    private List<Descuentos> descuentos;

    private Descuentos current;

    private ArrayList<String> estados;

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }


    public Descuentos getCurrent() {
        return current;
    }

    public void setCurrent(Descuentos current) {
        this.current = current;
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }
}