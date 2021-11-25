package com.ndl.erp.dto;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoInventarioItemDTO {


    public ProductoInventarioItemDTO() {
//        this.atributos = new HashMap<String, String>();
        this.atributos = new ArrayList();
    }

    //campos que se completan para un Variable
    private String tipo; //"Variable, Variant, Simple";
    private String codigo;
    private String sku;
    private String nombre;
    private String unidadMedida;
    private String codigoCabys;
    private String modeloCatalogo;
    private String fabricante;
//    Map<String, String> atributos;

    private List<String> atributos;

    //Campos que se completan solo para el producto
    private Double inventario;
    private Double precioCosto;
    private Double margenUtilidad;
    private Double precioLista;
    private String categoria;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getModeloCatalogo() {
        return modeloCatalogo;
    }

    public void setModeloCatalogo(String modeloCatalogo) {
        this.modeloCatalogo = modeloCatalogo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public List<String> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<String> atributos) {
        this.atributos = atributos;
    }

    public Double getInventario() {
        return inventario;
    }

    public void setInventario(Double inventario) {
        this.inventario = inventario;
    }

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public Double getMargenUtilidad() {
        return margenUtilidad;
    }

    public void setMargenUtilidad(Double margenUtilidad) {
        this.margenUtilidad = margenUtilidad;
    }

    public Double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(Double precioLista) {
        this.precioLista = precioLista;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
