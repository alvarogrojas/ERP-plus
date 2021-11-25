package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class ProjectionsMaster {


    @Id
    @GeneratedValue
    private Integer id;

    private Date date;
    private Double amount;
    private String status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "poc_id", referencedColumnName = "id")
    private PurchaseOrderClient poc;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_id", referencedColumnName = "id")
    private CostCenter costCenter;


    private Integer userId;
    private Date createAt;
    private Date updateAt;

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public PurchaseOrderClient getPoc() {
        return poc;
    }

    public void setPoc(PurchaseOrderClient poc) {
        this.poc = poc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
