package com.ndl.erp.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SessionPos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @JsonBackReference
//    @ManyToOne
    @OneToOne
    @JoinColumn(name = "terminal_usuario_id", referencedColumnName = "id")
    private TerminalUsuario terminalUsuario;

    private String estado;
    private Date fechaApertura;
    private Date fechaCierre;
    private Double montoEfectivoApertura = 0d;
    private Double montoEfectivoCierre = 0d;
    private Double montoTarjetaCierre = 0d;

    private Double montoSinpeCierre = 0d;
    private Double montoNeto = 0d;
    @OneToOne
    @JoinColumn(name= "ingresado_por", referencedColumnName =  "id")
    private User userIngresadoPor;

    @OneToOne
    @JoinColumn(name= "modificado_por", referencedColumnName =  "id")
    private User userModificadoPor;

    private Date fechaModificacion;
    private Date fechaCreacion;

    private Double montoTransferenciaBancaria = 0d;


    @OneToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id")
    private ExchangeRate exchangeRate;

    public Double getMontoTransferenciaBancaria() {
        return montoTransferenciaBancaria;
    }

    public void setMontoTransferenciaBancaria(Double montoTransferenciaBancaria) {
        this.montoTransferenciaBancaria = montoTransferenciaBancaria;
    }

    public User getUserIngresadoPor() {
        return userIngresadoPor;
    }

    public void setUserIngresadoPor(User userIngresadoPor) {
        this.userIngresadoPor = userIngresadoPor;
    }

    public User getUserModificadoPor() {
        return userModificadoPor;
    }

    public void setUserModificadoPor(User userModificadoPor) {
        this.userModificadoPor = userModificadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TerminalUsuario getTerminalUsuario() {
        return terminalUsuario;
    }

    public void setTerminalUsuario(TerminalUsuario terminalUsuario) {
        this.terminalUsuario = terminalUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Double getMontoEfectivoApertura() {
        return montoEfectivoApertura;
    }

    public void setMontoEfectivoApertura(Double montoEfectivoApertura) {
        this.montoEfectivoApertura = montoEfectivoApertura;
    }

    public Double getMontoEfectivoCierre() {
        return montoEfectivoCierre;
    }

    public void setMontoEfectivoCierre(Double montoEfectivoCierre) {
        this.montoEfectivoCierre = montoEfectivoCierre;
    }

    public Double getMontoTarjetaCierre() {
        return montoTarjetaCierre;
    }

    public void setMontoTarjetaCierre(Double montoTarjetaCierre) {
        this.montoTarjetaCierre = montoTarjetaCierre;
    }

    public Double getMontoSinpeCierre() {
        return montoSinpeCierre;
    }

    public void setMontoSinpeCierre(Double montoSinpeCierre) {
        this.montoSinpeCierre = montoSinpeCierre;
    }

    public Double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}


