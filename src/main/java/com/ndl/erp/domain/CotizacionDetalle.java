package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class CotizacionDetalle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer lineNumber;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cotizacion_id", referencedColumnName = "id")
    private Cotizacion cotizacion;

    @OneToOne
    @JoinColumn(name="iva_id", referencedColumnName="id")
    private TaxesIva iva;


    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;



    private String descripcion;
    private String entrega;
    private Double cantidad;
    private Double precioUnitario;
    private Double total;
    private Double porcentajeDescuento;
    private Double porTax;
    private Double tax;
    private Double exonerated;
    private Double subTotal;
    private Double descuentoMonto;
    private Integer descuentoId;


    public Integer getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }


    public Double getDescuentoMonto() {
        return descuentoMonto;
    }

    public void setDescuentoMonto(Double descuentoMonto) {
        this.descuentoMonto = descuentoMonto;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }


    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Double getPorTax() {
        return porTax;
    }

    public void setPorTax(Double porTax) {
        this.porTax = porTax;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public TaxesIva getIva() {
        return iva;
    }

    public void setIva(TaxesIva iva) {
        this.iva = iva;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
