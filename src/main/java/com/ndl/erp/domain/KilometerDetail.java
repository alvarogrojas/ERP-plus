package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="kilometer_detail")
public class KilometerDetail implements Serializable {


    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "km_id", referencedColumnName = "id")
    private Kilometer kilometer;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    private Date dateKmDetail;
    private String description;
    private Double km;
    private Double payFactor;
    private Double subTotal;
    private String status;
    private Date createDate;
    private Integer idUser;
    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getPayFactor() {
        return payFactor;
    }

    public void setPayFactor(Double payFactor) {
        this.payFactor = payFactor;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateKmDetail() {
        return dateKmDetail;
    }

    public void setDateKmDetail(Date dateKmDetail) {
        this.dateKmDetail = dateKmDetail;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public Kilometer getKilometer() {
        return kilometer;
    }

    public void setKilometer(Kilometer kilometer) {
        this.kilometer = kilometer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
