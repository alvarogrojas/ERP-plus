package com.ndl.erp.domain;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class BitacoraBodegaIngreso {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="bodega_ingreso", referencedColumnName="id")
    private BodegaIngreso bodegaIngreso;

    private Double price;

    private Double quantity;
    private String codigoCabys;

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

    public BodegaIngreso getBodegaIngreso() {
        return bodegaIngreso;
    }

    public void setBodegaIngreso(BodegaIngreso bodegaIngreso) {
        this.bodegaIngreso = bodegaIngreso;
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

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
