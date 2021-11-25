package com.ndl.erp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by alvaro on 10/17/17.
 */
@Entity
@Table(name="pendiente_mensaje_hacienda")
public class PendienteMensajeHacienda {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer comprobanteId;

    private Date fechaIngreso;

    private Date fechaUltimoRequest;

    private Integer intentos;

    private String tipoComprobante;

    private String observaciones;

    private String estado = "ESPERA";

    private Integer billSenderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Integer comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaUltimoRequest() {
        return fechaUltimoRequest;
    }

    public void setFechaUltimoRequest(Date fechaUltimoRequest) {
        this.fechaUltimoRequest = fechaUltimoRequest;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getBillSenderId() {
        return billSenderId;
    }

    public void setBillSenderId(Integer billSenderId) {
        this.billSenderId = billSenderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PendienteMensajeHacienda p = (PendienteMensajeHacienda) o;

        return id != null ? id.equals(p.id) : p.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
