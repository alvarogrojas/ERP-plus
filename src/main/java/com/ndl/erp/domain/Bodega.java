package com.ndl.erp.domain;


import javax.persistence.*;
import java.util.Date;


@Entity
public class Bodega {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String adress;
    private String status;
    private String facturable;
    private String manejoBodega;
    private String manejoPrecio;

    private Date fechaUltimoCambio;

    @OneToOne
    @JoinColumn(name="ultimo_cambio_id", referencedColumnName="id")
    private User userUltimoCambio;

    @OneToOne
    @JoinColumn(name="tienda_id", referencedColumnName="id")
    private Tienda tienda;

    public String getManejoPrecio() {
        return manejoPrecio;
    }

    public void setManejoPrecio(String manejoPrecio) {
        this.manejoPrecio = manejoPrecio;
    }

    public String getManejoBodega() {
        return manejoBodega;
    }

    public void setManejoBodega(String manejoBodega) {
        this.manejoBodega = manejoBodega;
    }

    public User getUserUltimoCambio() {
        return userUltimoCambio;
    }

    public void setUserUltimoCambio(User userUltimoCambio) {
        this.userUltimoCambio = userUltimoCambio;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacturable() {
        return facturable;
    }

    public void setFacturable(String facturable) {
        this.facturable = facturable;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }
}
