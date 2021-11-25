package com.ndl.erp.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class InventarioBodegaVentaDetalle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name="inventario_bodega_id", referencedColumnName="id")
    private InventarioBodega inventarioBodega;

    private String mes;
    private Integer year;
    private Double cantidad;
    private Date fechaUltimoCambio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventarioBodega getInventarioBodega() {
        return inventarioBodega;
    }

    public void setInventarioBodega(InventarioBodega inventarioBodega) {
        this.inventarioBodega = inventarioBodega;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }
}
