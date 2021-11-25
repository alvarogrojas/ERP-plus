package com.ndl.erp.domain;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class BodegaIngreso {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="inventario_item_id", referencedColumnName="id")
    private InventarioItem inventarioItem;

    private Integer billId;

    private Integer billDetailId;

    private Double price;

    private Double quantity;

    private String type;
    private String parentType;

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

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Integer billDetailId) {
        this.billDetailId = billDetailId;
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
        if (!(o instanceof BodegaIngreso)) return false;
        BodegaIngreso that = (BodegaIngreso) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }
}
