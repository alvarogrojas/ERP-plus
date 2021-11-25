package com.ndl.erp.domain;


import javax.persistence.*;


@Entity
public class Cabys {

    @Id
    @GeneratedValue
    private Integer id;
    private String categoria1;
    private String descripcion1;
    private String categoria2;
    private String descripcion2;
    private String categoria3;
    private String descripcion3;
    private String categoria4;
    private String descripcion4;
    private String categoria5;
    private String descripcion5;
    private String categoria6;
    private String descripcion6;
    private String categoria7;
    private String descripcion7;
    private String categoria8;
    private String descripcion8;

    private String codigoBien;
    private String descripcion;
    private String impuesto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getCategoria3() {
        return categoria3;
    }

    public void setCategoria3(String categoria3) {
        this.categoria3 = categoria3;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public String getCategoria4() {
        return categoria4;
    }

    public void setCategoria4(String categoria4) {
        this.categoria4 = categoria4;
    }

    public String getDescripcion4() {
        return descripcion4;
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4 = descripcion4;
    }

    public String getCategoria5() {
        return categoria5;
    }

    public void setCategoria5(String categoria5) {
        this.categoria5 = categoria5;
    }

    public String getDescripcion5() {
        return descripcion5;
    }

    public void setDescripcion5(String descripcion5) {
        this.descripcion5 = descripcion5;
    }

    public String getCategoria6() {
        return categoria6;
    }

    public void setCategoria6(String categoria6) {
        this.categoria6 = categoria6;
    }

    public String getDescripcion6() {
        return descripcion6;
    }

    public void setDescripcion6(String descripcion6) {
        this.descripcion6 = descripcion6;
    }

    public String getCategoria7() {
        return categoria7;
    }

    public void setCategoria7(String categoria7) {
        this.categoria7 = categoria7;
    }

    public String getDescripcion7() {
        return descripcion7;
    }

    public void setDescripcion7(String descripcion7) {
        this.descripcion7 = descripcion7;
    }

    public String getCategoria8() {
        return categoria8;
    }

    public void setCategoria8(String categoria8) {
        this.categoria8 = categoria8;
    }

    public String getDescripcion8() {
        return descripcion8;
    }

    public void setDescripcion8(String descripcion8) {
        this.descripcion8 = descripcion8;
    }

    public String getCodigoBien() {
        return codigoBien;
    }

    public void setCodigoBien(String codigoBien) {
        this.codigoBien = codigoBien;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }
}
