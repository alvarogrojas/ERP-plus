package com.ndl.erp.domain;

import javax.persistence.*;

@Entity
public class InventarioBodega {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @OneToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    private Producto producto;

    private Double cantidadDisponible;
    private Double cantidadCongelada;
    private Double cantidadTotalAnual;
    private Double cantidadCotizada;
    private Double cantidadApartado;


    @Transient
    private Double cantidadNeta;


    public Double getCantidadNeta() {
        if (this.cantidadDisponible != null)  {
            this.cantidadNeta = this.cantidadDisponible - (this.cantidadApartado != null ? this.cantidadApartado : 0d);
        }else {
            this.cantidadNeta = 0d;
        }
        return this.cantidadNeta;
    }

    public Double getCantidadApartado() {
        return cantidadApartado;
    }

    public void setCantidadApartado(Double cantidadApartado) {
        this.cantidadApartado = cantidadApartado;
    }

    public Double getCantidadCotizada() {
        return cantidadCotizada;
    }

    public void setCantidadCotizada(Double cantidadCotizada) {
        this.cantidadCotizada = cantidadCotizada;
    }

    public Double getCantidadTotalAnual() {
        return cantidadTotalAnual;
    }

    public void setCantidadTotalAnual(Double cantidadTotalAnual) {
        this.cantidadTotalAnual = cantidadTotalAnual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Double getCantidadCongelada() {
        return cantidadCongelada;
    }

    public void setCantidadCongelada(Double cantidadCongelada) {
        this.cantidadCongelada = cantidadCongelada;
    }
}
