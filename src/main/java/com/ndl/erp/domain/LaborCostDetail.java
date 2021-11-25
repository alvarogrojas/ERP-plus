package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;


@Entity
public class LaborCostDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private Double costHour;
    private Double hoursSimple;


    private Double hoursDouble;


    private Double hoursMedia;
    private String status;
    private Date laborDate;

    @OneToOne
    @JoinColumn(name = "id_collaborator", referencedColumnName = "id")
    private Collaborator collaborator;


    @OneToOne
    @JoinColumn(name="id_cost_center", referencedColumnName="id")
    private CostCenter costCenter;


    @OneToOne
    @JoinColumn(name="id_currency", referencedColumnName="id")
    private Currency currency;

    private Integer idUserRegister;

    private Date createDate;
    private Date updateDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_labor_cost", referencedColumnName = "id")
    private LaborCost laborCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCostHour() {
        return costHour;
    }

    public void setCostHour(Double costHour) {
        this.costHour = costHour;
    }

    public Double getHoursSimple() {
        return hoursSimple;
    }

    public void setHoursSimple(Double hoursSimple) {
        this.hoursSimple = hoursSimple;
    }

    public Double getHoursDouble() {
        return hoursDouble;
    }

    public void setHoursDouble(Double hoursDouble) {
        this.hoursDouble = hoursDouble;
    }

    public Double getHoursMedia() {
        return hoursMedia;
    }

    public void setHoursMedia(Double hoursMedia) {
        this.hoursMedia = hoursMedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LaborCost getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(LaborCost laborCost) {
        this.laborCost = laborCost;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }


    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaborCostDetail)) return false;
        LaborCostDetail that = (LaborCostDetail) o;
        return Objects.equals(collaborator, that.collaborator) &&
                Objects.equals(costCenter, that.costCenter) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(laborCost, that.laborCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collaborator, costCenter);
    }
}
