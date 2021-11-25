package com.ndl.erp.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class BodegaSalida {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="inventario_item_id", referencedColumnName="id")
    private InventarioItem inventarioItem;

    private Integer invoiceId;

    private Integer invoiceDetailId;

    private Double price;

    private Double quantity;

    private String type;

    private Date createAt;

    private Date updateAt;

    private String status;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventarioItem getInventarioItem() {
        return inventarioItem;
    }

    public void setInventarioItem(InventarioItem inventarioItem) {
        this.inventarioItem = inventarioItem;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BodegaSalida)) return false;
        BodegaSalida that = (BodegaSalida) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
