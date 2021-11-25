package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ProductoDescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @OneToOne
    @JoinColumn(name="ingresado_por_id", referencedColumnName="id")
    private User userIngresadoPor;

    @OneToOne
    @JoinColumn(name="ultima_actualizacion_por_id", referencedColumnName="id")
    private User userUltimaActualizacionPor;


    private Double cantidadMinimaInclusive;
    private Double cantidadMaximaInclusive;
    private String estado;
    private Date fechaIngreso;
    private Date fechaUltimaActualizacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public User getUserIngresadoPor() {
        return userIngresadoPor;
    }

    public void setUserIngresadoPor(User userIngresadoPor) {
        this.userIngresadoPor = userIngresadoPor;
    }

    public User getUserUltimaActualizacionPor() {
        return userUltimaActualizacionPor;
    }

    public void setUserUltimaActualizacionPor(User userUltimaActualizacionPor) {
        this.userUltimaActualizacionPor = userUltimaActualizacionPor;
    }

    public Double getCantidadMinimaInclusive() {
        return cantidadMinimaInclusive;
    }

    public void setCantidadMinimaInclusive(Double cantidadMinimaInclusive) {
        this.cantidadMinimaInclusive = cantidadMinimaInclusive;
    }

    public Double getCantidadMaximaInclusive() {
        return cantidadMaximaInclusive;
    }

    public void setCantidadMaximaInclusive(Double cantidadMaximaInclusive) {
        this.cantidadMaximaInclusive = cantidadMaximaInclusive;
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

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }
}
