package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
public class MonthlyClosure {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Date start;
    private Date end;
    private String status;


    private Date createDate;
    private Integer idUser;
    private Date updateDate;
    private Double changeTypeBuy;
    private Double changeTypeSale;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "parent", orphanRemoval = true)
    private List<MonthlyClosureBillCollect> billCollects = new ArrayList<MonthlyClosureBillCollect>(0);

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,
            mappedBy = "parent", orphanRemoval = true)
    private List<MonthlyClosureBillPay> billPays = new ArrayList<MonthlyClosureBillPay>(0);

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "parent", orphanRemoval = true)
    private List<MonthlyClosurePayRoll> payRolls = new ArrayList<MonthlyClosurePayRoll>(0);

    public MonthlyClosure() {
        this.status = "Ingresado";
    }

    public MonthlyClosure(Integer id, String name, Date start, Date end, String status) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.status = status;

    }

    public Double getChangeTypeSale() {
        return changeTypeSale;
    }

    public void setChangeTypeSale(Double changeTypeSale) {
        this.changeTypeSale = changeTypeSale;
    }

    public Double getChangeTypeBuy() {
        return changeTypeBuy;
    }

    public void setChangeTypeBuy(Double changeTypeBuy) {
        this.changeTypeBuy = changeTypeBuy;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MonthlyClosureBillCollect> getBillCollects() {
        return billCollects;
    }

    public void setBillCollects(List<MonthlyClosureBillCollect> billCollects) {
        this.billCollects = billCollects;
    }

    public List<MonthlyClosureBillPay> getBillPays() {
        return billPays;
    }

    public void setBillPays(List<MonthlyClosureBillPay> billPays) {
        this.billPays = billPays;
    }

    public List<MonthlyClosurePayRoll> getPayRolls() {
        return payRolls;
    }

    public void setPayRolls(List<MonthlyClosurePayRoll> payRolls) {
        this.payRolls = payRolls;
    }
}
