package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RequisicionBodega {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String consecutivo;
    private Date fechaSolicitada;
    private Date fechaAprobada;
    private Date fechaDespachada;
    private String motivoSalida;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User userIngresadoPor;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "requisicionBodega", orphanRemoval = true)
    private List<RequisicionBodegaDetalle> requisicionBodegaDetalle = new ArrayList<RequisicionBodegaDetalle>(0);

    private String estado;
    private double montoSolicitado;
    private double montoAprobado;
    private Date  fechaUltimaModificacion;
    private boolean salidaBodega;

    public boolean isSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Date getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Date fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public Date getFechaAprobada() {
        return fechaAprobada;
    }

    public void setFechaAprobada(Date fechaAprobada) {
        this.fechaAprobada = fechaAprobada;
    }

    public Date getFechaDespachada() {
        return fechaDespachada;
    }

    public void setFechaDespachada(Date fechaDespachada) {
        this.fechaDespachada = fechaDespachada;
    }

    public String getMotivoSalida() {
        return motivoSalida;
    }

    public void setMotivoSalida(String motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public User getUserIngresadoPor() {
        return userIngresadoPor;
    }

    public void setUserIngresadoPor(User userIngresadoPor) {
        this.userIngresadoPor = userIngresadoPor;
    }

    public List<RequisicionBodegaDetalle> getRequisicionBodegaDetalle() {
        return requisicionBodegaDetalle;
    }

    public void setRequisicionBodegaDetalle(List<RequisicionBodegaDetalle> requisicionBodegaDetalle) {
        this.requisicionBodegaDetalle = requisicionBodegaDetalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public double getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(double montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
}
