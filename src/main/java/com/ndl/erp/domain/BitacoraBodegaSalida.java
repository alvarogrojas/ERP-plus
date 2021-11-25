package com.ndl.erp.domain;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class BitacoraBodegaSalida {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="bodega_salida", referencedColumnName="id")
    private BodegaSalida bodegaSalida;

    private Double price;

    private Double quantity;
//    private String codigoCabys;

    private Date date;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BodegaSalida getBodegaSalida() {
        return bodegaSalida;
    }

    public void setBodegaSalida(BodegaSalida bodegaSalida) {
        this.bodegaSalida = bodegaSalida;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getCodigoCabys() {
//        return codigoCabys;
//    }
//
//    public void setCodigoCabys(String codigoCabys) {
//        this.codigoCabys = codigoCabys;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
