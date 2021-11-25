package com.ndl.erp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="bill_sender_detail")
public class BillSenderDetail {

    @Id
    @GeneratedValue
    private Integer id;

    private Date dateSent;

    private String status;//aceptada, rechazada, pendiente

    private Integer billId;// id original Comprobante electronica

    private Integer enviadaHacienda;// Sobra: 0(no enviado), 1 si enviado, -1 (error) -> manejarlo con estado

    private Integer validada; // Sobra: ya lo ocupamos

    private Integer enviadaCliente; // Sobra

    private Integer ingresadoPor;

    private Date fechaIngreso;

    private Integer ultimoCambioId;

    private Date fechaUltimoCambio;

    private String path;

    private String clave;

    private String consecutivo;

    private String observaciones;

    private String tipo; // FE(factura),NCF(Nota de Credito), CFN

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIngresadoPor() {
        return ingresadoPor;
    }

    public void setIngresadoPor(Integer ingresadoPor) {
        this.ingresadoPor = ingresadoPor;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getUltimoCambioId() {
        return ultimoCambioId;
    }

    public void setUltimoCambioId(Integer ultimoCambioId) {
        this.ultimoCambioId = ultimoCambioId;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }



    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public Integer getValidada() {
        return validada;
    }

    public void setValidada(Integer validada) {
        this.validada = validada;
    }

    public Integer getEnviadaCliente() {
        return enviadaCliente;
    }

    public void setEnviadaCliente(Integer enviadaCliente) {
        this.enviadaCliente = enviadaCliente;
    }


    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
