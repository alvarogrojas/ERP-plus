package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.ArrayList;
import java.util.List;

public class ProductoDTO {

    private Producto current;
    private List<ProductoCategoria>  productoCategoriaList = new ArrayList<>(0);
    private List<Multiplicador> multiplicadorList;
    private List<Fabricante> fabricanteList;
    private List<String> tipos;
    private List<Familia> familias;
    private List<UnidadMedida> unidadMedidaList;
    private List<User> responsableList;
    private List<String> manejoBodegaList;
    private List<String> estadoList;



    public List<User> getResponsableList() {
        return responsableList;
    }

    public void setResponsableList(List<User> responsableList) {
        this.responsableList = responsableList;
    }

    public List<String> getManejoBodegaList() {
        return manejoBodegaList;
    }

    public void setManejoBodegaList(List<String> manejoBodegaList) {
        this.manejoBodegaList = manejoBodegaList;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Multiplicador> getMultiplicadorList() {
        return multiplicadorList;
    }

    public void setMultiplicadorList(List<Multiplicador> multiplicadorList) {
        this.multiplicadorList = multiplicadorList;
    }

    public List<UnidadMedida> getUnidadMedidaList() {
        return unidadMedidaList;
    }

    public void setUnidadMedidaList(List<UnidadMedida> unidadMedidaList) {
        this.unidadMedidaList = unidadMedidaList;
    }

    public Producto getCurrent() {
        return current;
    }

    public void setCurrent(Producto current) {
        this.current = current;
    }

    public List<ProductoCategoria> getProductoCategoriaList() {
        return productoCategoriaList;
    }

    public void setProductoCategoriaList(List<ProductoCategoria> productoCategoriaList) {
        this.productoCategoriaList = productoCategoriaList;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public List<Familia> getFamilias() {
        return familias;
    }

    public void setFamilias(List<Familia> familias) {
        this.familias = familias;
    }

    public List<Fabricante> getFabricanteList() {
        return fabricanteList;
    }

    public void setFabricanteList(List<Fabricante> fabricanteList) {
        this.fabricanteList = fabricanteList;
    }
}
