package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="confirma_rechaza_documento")
public class ConfirmaRechazaDocumento {

    @Id
    @GeneratedValue
    private Integer id;

    private String clave;
    private String consecutivo;
    private String numeroConsecutivoReceptor;
    private String emisor;
    private String identificacionEmisor;
    private Date fechaEmision;
    private Double totalVenta;
    private Double totalImpuesto;
    private Double totalComprobante;

    private String moneda;

    private Double totalServiciosGrabados;
    private Double totalServiciosExentos;
    private Double totalMercanciasGrabadas;
    private Double totalMercanciasExentas;
    private Double totalGrabado;
    private Double totalExento;
    private Double totalDescuentos;
    private Double totalOtrosCargos;


    private String estado;
    private String estadoEnviadoHacienda;
    private String pathOriginalFile;
    private Integer ultimoCambioPor;
    private Date fechaUltimoCambio;
    private String mensaje;
    private String tipo;
    private Integer billSenderId;
    private String  tipoTransaccion;
    private Double descuentoTotal;
    private String tipoActividadEconomiica;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "confirmaRechazaDocumento", orphanRemoval = true)
    @OrderBy("indexLine ASC")
    private List<ConfirmaRechazaDetalle> details = new ArrayList<>();


    public List<ConfirmaRechazaDetalle> getDetails() {
        return details;
    }

    public void setDetails(List<ConfirmaRechazaDetalle> details) {
        this.details = details;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Double getDescuentoTotal() {
        return descuentoTotal;
    }

    public void setDescuentoTotal(Double descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public String getTipoActividadEconomiica() {
        return tipoActividadEconomiica;
    }

    public void setTipoActividadEconomiica(String tipoActividadEconomiica) {
        this.tipoActividadEconomiica = tipoActividadEconomiica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getIdentificacionEmisor() {
        return identificacionEmisor;
    }

    public void setIdentificacionEmisor(String identificacionEmisor) {
        this.identificacionEmisor = identificacionEmisor;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalventa) {
        this.totalVenta = totalventa;
    }

    public Double getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(Double totalimpuesto) {
        this.totalImpuesto = totalimpuesto;
    }

    public Double getTotalComprobante() {
        return totalComprobante;
    }

    public void setTotalComprobante(Double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoEnviadoHacienda() {
        return estadoEnviadoHacienda;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getBillSenderId() {
        return billSenderId;
    }

    public void setBillSenderId(Integer billSenderId) {
        this.billSenderId = billSenderId;
    }

    public String getNumeroConsecutivoReceptor() {
        return numeroConsecutivoReceptor;
    }

    public void setNumeroConsecutivoReceptor(String numeroConsecutivoReceptor) {
        this.numeroConsecutivoReceptor = numeroConsecutivoReceptor;
    }

    public void setEstadoEnviadoHacienda(String estadoEnviadoHacienda) {
        this.estadoEnviadoHacienda = estadoEnviadoHacienda;
    }

    public Integer getUltimoCambioPor() {
        return ultimoCambioPor;
    }

    public void setUltimoCambioPor(Integer ultimoCambioPor) {
        this.ultimoCambioPor = ultimoCambioPor;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public String getPathOriginalFile() {
        return pathOriginalFile;
    }

    public void setPathOriginalFile(String pathOriginalFile) {
        this.pathOriginalFile = pathOriginalFile;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getTotalServiciosGrabados() {
        return totalServiciosGrabados;
    }

    public void setTotalServiciosGrabados(Double totalServiciosGrabados) {
        this.totalServiciosGrabados = totalServiciosGrabados;
    }

    public Double getTotalServiciosExentos() {
        return totalServiciosExentos;
    }

    public void setTotalServiciosExentos(Double totalServiciosExentos) {
        this.totalServiciosExentos = totalServiciosExentos;
    }

    public Double getTotalMercanciasGrabadas() {
        return totalMercanciasGrabadas;
    }

    public void setTotalMercanciasGrabadas(Double totalMercanciasGrabadas) {
        this.totalMercanciasGrabadas = totalMercanciasGrabadas;
    }

    public Double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    public void setTotalMercanciasExentas(Double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    public Double getTotalGrabado() {
        return totalGrabado;
    }

    public void setTotalGrabado(Double totalGrabado) {
        this.totalGrabado = totalGrabado;
    }

    public Double getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(Double totalExento) {
        this.totalExento = totalExento;
    }

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(Double totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }
}
