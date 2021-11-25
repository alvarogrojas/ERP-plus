package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class InitInventarioDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "init_inventario_id", referencedColumnName = "id")
    private InitInventario initInventario;

    private String tipo;
    private String sku;
    private String codigo;
    private String nombre;
    private Double cantidadInventario;
    private Double margenUtilidad;
    private String categorias;
    private String unidadMedida;
    private Double precioList;
    private String codigoCabys;
    private String modeloCatalogo;
    private String fabricante;
    private Integer productoId;
    private Double cantidadIncrementoNeto;
    private Double precioCosto;

    public Double getCantidadIncrementoNeto() {
        return cantidadIncrementoNeto;
    }

    public void setCantidadIncrementoNeto(Double cantidadIncrementoNeto) {
        this.cantidadIncrementoNeto = cantidadIncrementoNeto;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InitInventario getInitInventario() {
        return initInventario;
    }

    public void setInitInventario(InitInventario initInventario) {
        this.initInventario = initInventario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(Double cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    public Double getMargenUtilidad() {
        return margenUtilidad;
    }

    public void setMargenUtilidad(Double margenUtilidad) {
        this.margenUtilidad = margenUtilidad;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getPrecioList() {
        return precioList;
    }

    public void setPrecioList(Double precioList) {
        this.precioList = precioList;
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

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
    }
}
