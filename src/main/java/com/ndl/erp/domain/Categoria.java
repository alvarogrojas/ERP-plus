package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private String estado;
    private Date fechaIngreso;



    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User userIngresadoPor;

    private Date fechaUltimoCambio;

    @OneToOne
    @JoinColumn(name="ultimo_cambio_por", referencedColumnName="id")
    private User ultimoCambioPor;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "categoria", orphanRemoval = true)
    private List<CategoriaDescuentos> categoriaDescuentos = new ArrayList<>(0);

    public List<CategoriaDescuentos> getCategoriaDescuentos() {
        return categoriaDescuentos;
    }

    public void setCategoriaDescuentos(List<CategoriaDescuentos> categoriaDescuentos) {
        this.categoriaDescuentos = categoriaDescuentos;
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

    public User getUltimoCambioPor() {
        return ultimoCambioPor;
    }

    public void setUltimoCambioPor(User ultimoCambioPor) {
        this.ultimoCambioPor = ultimoCambioPor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
