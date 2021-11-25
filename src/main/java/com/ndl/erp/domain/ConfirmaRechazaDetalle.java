package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ConfirmaRechazaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "crd_id", referencedColumnName = "id")
    private ConfirmaRechazaDocumento confirmaRechazaDocumento;

    private Integer index_line;
    private String detalle;
    private Double cantidad;
    private Double precioUnitario;
    private Double tax;
    private Double montoTotal;
    private Double subTotal;
    private Double porcentajeImpuesto;
    private Double impuesto;
    private Double montoTotalLinea;
    private Date  createAt;
    private Date updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConfirmaRechazaDocumento getConfirmaRechazaDocumento() {
        return confirmaRechazaDocumento;
    }

    public void setConfirmaRechazaDocumento(ConfirmaRechazaDocumento confirmaRechazaDocumento) {
        this.confirmaRechazaDocumento = confirmaRechazaDocumento;
    }

    public Integer getIndex_line() {
        return index_line;
    }

    public void setIndex_line(Integer index_line) {
        this.index_line = index_line;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getPorcentajeImpuesto() {
        return porcentajeImpuesto;
    }

    public void setPorcentajeImpuesto(Double porcentajeImpuesto) {
        this.porcentajeImpuesto = porcentajeImpuesto;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getMontoTotalLinea() {
        return montoTotalLinea;
    }

    public void setMontoTotalLinea(Double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
