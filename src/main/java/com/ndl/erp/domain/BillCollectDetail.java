package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class BillCollectDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String detail;
    private Double quantity;
    private Double price;
    private Double tax;
    private Double exonerated;
    private Double discount;
    private String groceryCode;
    private String status;
    private Double subTotal;
    private Double total;
    private Date createDate;
    private Integer userId;
    private Date updateDate;
//    private Integer billCollectId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bill_collect_id", referencedColumnName = "id")
    private BillCollect billCollect;



    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costsCenter;

    private Double taxPorcent;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double taxPorcent) {
        this.taxPorcent = taxPorcent;
    }

    public CostCenter getCostCenter() {
        return costsCenter;
    }

    public void setCostCenter(CostCenter costsCenter) {
        this.costsCenter = costsCenter;
    }

//    public Integer getBillCollectId() {
//        return billCollectId;
//    }
//
//    public void setBillCollectId(Integer billCollectId) {
//        this.billCollectId = billCollectId;
//    }


    public BillCollect getBillCollect() {
        return billCollect;
    }

    public void setBillCollect(BillCollect billCollect) {
        this.billCollect = billCollect;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroceryCode() {
        return groceryCode;
    }

    public void setGroceryCode(String groceryCode) {
        this.groceryCode = groceryCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }
}
