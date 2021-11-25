package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Traslado {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String consecutivo;
    private Date fechaTraslado;

    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User ingresadoPor;

    private  Date fechaUltimaModificacion;

    @OneToOne
    @JoinColumn(name="bodega_origen_id", referencedColumnName = "id")
    private Bodega bodegaOrigen;


    @OneToOne
    @JoinColumn(name="bodega_destino_id", referencedColumnName = "id")
    private Bodega bodegaDestino;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,
            mappedBy = "traslado", orphanRemoval = true)
    private List<TrasladoDetalle> details = new ArrayList<TrasladoDetalle>(0);

    private String estado;

    private Double totalTraslado;

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

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public User getIngresadoPor() {
        return ingresadoPor;
    }

    public void setIngresadoPor(User ingresadoPor) {
        this.ingresadoPor = ingresadoPor;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Bodega getBodegaOrigen() {
        return bodegaOrigen;
    }

    public void setBodegaOrigen(Bodega bodegaOrigen) {
        this.bodegaOrigen = bodegaOrigen;
    }

    public Bodega getBodegaDestino() {
        return bodegaDestino;
    }

    public void setBodegaDestino(Bodega bodegaDestino) {
        this.bodegaDestino = bodegaDestino;
    }

    public List<TrasladoDetalle> getDetails() {
        return details;
    }

    public void setDetails(List<TrasladoDetalle> details) {
        this.details = details;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalTraslado() {
        return totalTraslado;
    }

    public void setTotalTraslado(Double totalTraslado) {
        this.totalTraslado = totalTraslado;
    }
}
