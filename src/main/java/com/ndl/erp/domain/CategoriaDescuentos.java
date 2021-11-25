package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CategoriaDescuentos {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name="categoria_id", referencedColumnName="id")
    private Categoria categoria;

    private String nombreDescuento;
    private Double porcentajeDescuento;
    private String estado;
    private Date fechaIngreso;

    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User userIngresadoPor;

    private Date fechaUltimoCambio;

    @OneToOne
    @JoinColumn(name="ultimo_cambio_por", referencedColumnName="id")
    private User userUltimoCambioPor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombreDescuento() {
        return nombreDescuento;
    }

    public void setNombreDescuento(String nombreDescuento) {
        this.nombreDescuento = nombreDescuento;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
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

    public User getUserIngresadoPor() {
        return userIngresadoPor;
    }

    public void setUserIngresadoPor(User userIngresadoPor) {
        this.userIngresadoPor = userIngresadoPor;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public User getUserUltimoCambioPor() {
        return userUltimoCambioPor;
    }

    public void setUserUltimoCambioPor(User userUltimoCambioPor) {
        this.userUltimoCambioPor = userUltimoCambioPor;
    }
}
