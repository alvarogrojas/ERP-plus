package com.ndl.erp.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TransaccionPendiente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "invoice_id",  referencedColumnName = "id")
    private Invoice invoice;

    private String status;
    private String tipo;
    private Date fechaIngreso;
    private Date fechaUltimaActualizacion;


    @OneToOne
    @JoinColumn(name = "ingresada_por", referencedColumnName = "id")
    private User ingresadaPor;


    @OneToOne
    @JoinColumn(name = "terminal_id", referencedColumnName = "id")
    Terminal terminal;

    private String tipoEnvio;


    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public User getIngresadaPor() {
        return ingresadaPor;
    }

    public void setIngresadaPor(User ingresadaPor) {
        this.ingresadaPor = ingresadaPor;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }
}
