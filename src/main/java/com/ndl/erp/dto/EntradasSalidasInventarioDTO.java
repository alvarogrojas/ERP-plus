package com.ndl.erp.dto;


import java.util.List;

public class EntradasSalidasInventarioDTO {


    private List<EntradasSalidasDTO>  entradasSalidasInventario;

    public List<EntradasSalidasDTO> getEntradasSalidasInventario() {
        return entradasSalidasInventario;
    }

    public void setEntradasSalidasInventario(List<EntradasSalidasDTO> entradasSalidasInventario) {
        this.entradasSalidasInventario = entradasSalidasInventario;
    }
}