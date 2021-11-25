package com.ndl.erp.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Recurso {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String tipoReferencia;
    private Integer referenciaId;
    private String uriRecurso;
    private String nombreRecurso;
    private Date fechaAgregado;
    private String tipoRecurso;

    @OneToOne
    @JoinColumn(name="agregado_por", referencedColumnName="id")
    private User userAgregadoPor;

    private Date fechaUltimaActualizacion;

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getUriRecurso() {
        return uriRecurso;
    }

    public void setUriRecurso(String uriRecurso) {
        this.uriRecurso = uriRecurso;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public User getUserAgregadoPor() {
        return userAgregadoPor;
    }

    public void setUserAgregadoPor(User userAgregadoPor) {
        this.userAgregadoPor = userAgregadoPor;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }
}
