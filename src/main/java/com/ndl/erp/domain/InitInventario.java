package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class InitInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date fechaInit;

    @OneToOne
    @JoinColumn(name = "creada_por", referencedColumnName = "id")
    private User creadaPorUser;

    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "actualizada_por", referencedColumnName = "id")
    private User actualizadaPorUser;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "initInventario", orphanRemoval = true)
    private List<InitInventarioDetalle> detalles;


    private String uriFileName;
    private String estado;
    private String observacion;
    private String flagModoImportInventario;


    public String getFlagModoImportInventario() {
        return flagModoImportInventario;
    }

    public void setFlagModoImportInventario(String flagModoImportInventario) {
        this.flagModoImportInventario = flagModoImportInventario;
    }

    public InitInventario() {
        detalles = new ArrayList<InitInventarioDetalle>();
    }

    public List<InitInventarioDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<InitInventarioDetalle> detalles) {
        this.detalles = detalles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInit() {
        return fechaInit;
    }

    public void setFechaInit(Date fechaInit) {
        this.fechaInit = fechaInit;
    }

    public User getCreadaPorUser() {
        return creadaPorUser;
    }

    public void setCreadaPorUser(User creadaPorUser) {
        this.creadaPorUser = creadaPorUser;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public User getActualizadaPorUser() {
        return actualizadaPorUser;
    }

    public void setActualizadaPorUser(User actualizadaPorUser) {
        this.actualizadaPorUser = actualizadaPorUser;
    }

    public String getUriFileName() {
        return uriFileName;
    }

    public void setUriFileName(String uriFileName) {
        this.uriFileName = uriFileName;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
