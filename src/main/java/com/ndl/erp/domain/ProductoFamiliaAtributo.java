package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class ProductoFamiliaAtributo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "atributo_id", referencedColumnName = "id")
    Atributo atributo;

//    @OneToOne
//    @JoinColumn(name = "producto_id", referencedColumnName = "id")
//    Producto producto;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    Producto producto;

    @OneToOne
    @JoinColumn(name = "atributo_detalle_selected_id", referencedColumnName = "id")
    AtributoDetalle atributoDetalle;

    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public AtributoDetalle getAtributoDetalle() {
        return atributoDetalle;
    }

    public void setAtributoDetalle(AtributoDetalle atributoDetalle) {
        this.atributoDetalle = atributoDetalle;
    }
}
