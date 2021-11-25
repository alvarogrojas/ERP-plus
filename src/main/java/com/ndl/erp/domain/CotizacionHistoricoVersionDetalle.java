package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class CotizacionHistoricoVersionDetalle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

   /* @JsonBackReference
    @ManyToOne
    @JoinColumn(name="cotizacion_historico_version_id", referencedColumnName="cotizacion_id")
    private CotizacionHistoricoVersion cotizacionHistoricoVersion;*/

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="cotizacion_historico_version_id", referencedColumnName="id")
    private CotizacionHistoricoVersion cotizacionHistoricoVersion;


    @OneToOne
    @JoinColumn(name="iva_id", referencedColumnName="id")
    private TaxesIva iva;

    /*@OneToOne
    @JoinColumn(name="inventario_bodega_id", referencedColumnName="id")
    private InventarioBodega inventarioBodega;*/

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter centroCosto;



    private Integer lineNumber;
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
    private Integer inventarioBodegaId;


    public Integer getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public CostCenter getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CostCenter centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Double getDescuentoMonto() {
        return descuentoMonto;
    }

    public void setDescuentoMonto(Double descuentoMonto) {
        this.descuentoMonto = descuentoMonto;
    }

    public Integer getInventarioBodegaId() {
        return inventarioBodegaId;
    }

    public void setInventarioBodegaId(Integer inventarioBodegaId) {
        this.inventarioBodegaId = inventarioBodegaId;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public TaxesIva getIva() {
        return iva;
    }

    public void setIva(TaxesIva iva) {
        this.iva = iva;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CotizacionHistoricoVersion getCotizacionHistoricoVersion() {
        return cotizacionHistoricoVersion;
    }

    public void setCotizacionHistoricoVersion(CotizacionHistoricoVersion cotizacionHistoricoVersion) {
        this.cotizacionHistoricoVersion = cotizacionHistoricoVersion;
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

    public void setPorcentajeDescuento(Double porcentajeDescuento)  {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}

