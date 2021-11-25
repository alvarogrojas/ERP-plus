package com.ndl.erp.fe.dtos;


public class FacturaDetalleDTO {

    private Integer id;

    private String detalle;

    private Double monto;

    private Double cantidad;

    private TipoCambioDTO tipoCambio;

    private Double montoColones;
    private Double montoNeto;

    private Double impuestos;
    private Double impuestosMonto;
    private String tipoImpuesto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer detalleId) {
        this.id = detalleId;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public TipoCambioDTO getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambioDTO tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Double getMontoColones() {
        return montoColones;
    }

    public void setMontoColones(Double totalColones) {
        this.montoColones = totalColones;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public Double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public Double getImpuestosMonto() {
        return impuestosMonto;
    }

    public void setImpuestosMonto(Double impuestosMonto) {
        this.impuestosMonto = impuestosMonto;
    }

    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }
}
