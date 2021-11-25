package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;

import java.util.Date;

public class SaleDetailInfoDTO {

    private Integer docId;
    private Currency currency;
    private Integer taxesId;
    private Date date;
    private String cliente;
    private String cedula;
    private String consecutivo;
    private String tipoDocumento;
    private Double gravado;
    private Double montoExcento;
    private Double descuento;
    private Double porcentajeIva;
    private Double iva;
    private Double total;

    private String estado;
    private String estadoHacienda;
    public SaleDetailInfoDTO() {
    }


    public SaleDetailInfoDTO(Integer docId,
                             Currency currency,
                             Integer taxesId,
                             Date date,
                             String cliente,
                             String cedula,
                             String consecutivo,
                             String tipoDocumento,
                             Double gravado,
                             Double montoExcento,
                             Double descuento,
                             Double porcentajeIva,
                             Double iva,
                             Double total, String estado, String estadoHacienda) {
        this.docId = docId;
        this.taxesId = taxesId;
        this.date = date;
        this.cliente = cliente;
        this.cedula = cedula;
        this.consecutivo = consecutivo;
        this.tipoDocumento = tipoDocumento;
        this.gravado = gravado;
        this.montoExcento = montoExcento;
        this.descuento = descuento;
        this.porcentajeIva = porcentajeIva;
        this.iva = iva;
        this.total = total;
        this.currency = currency;
        this.estado = estado;
        this.estadoHacienda = estadoHacienda;

    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getTaxesId() {
        return taxesId;
    }

    public void setTaxesId(Integer taxesId) {
        this.taxesId = taxesId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Double getGravado() {
        return gravado;
    }

    public void setGravado(Double gravado) {
        this.gravado = gravado;
    }

    public Double getMontoExcento() {
        return montoExcento;
    }

    public void setMontoExcento(Double montoExcento) {
        this.montoExcento = montoExcento;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Double porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(String estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }
}
