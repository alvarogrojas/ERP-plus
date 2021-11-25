package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

//@Table(name="pay_roll_detail")
@Entity
public class PayRollDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_pay_roll", referencedColumnName = "id")
    private PayRoll payRoll;


    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

    private Double netSalary;
    private Double crudeSalary;
    private Double deducctions;
    private Double ccss;
    private Double tax1;
    private Double tax2;
    private Double tax3;

    private Double devolutions;
    private Double refunds;
    private String status;
    private Integer idUserRegister;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRefunds() {
        return refunds;
    }

    public void setRefunds(Double refunds) {
        this.refunds = refunds;
    }

    public Double getDevolutions() {
        return devolutions;
    }

    public void setDevolutions(Double devolutions) {
        this.devolutions = devolutions;
    }

    public Double getDeducctions() {
        return deducctions;
    }

    public void setDeducctions(Double deducctions) {
        this.deducctions = deducctions;
    }

    public Double getCrudeSalary() {
        return crudeSalary;
    }

    public void setCrudeSalary(Double crudeSalary) {
        this.crudeSalary = crudeSalary;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public Double getCcss() {
        return ccss;
    }

    public void setCcss(Double ccss) {
        this.ccss = ccss;
    }

    public Double getTax1() {
        return tax1;
    }

    public void setTax1(Double tax1) {
        this.tax1 = tax1;
    }

    public Double getTax2() {
        return tax2;
    }

    public void setTax2(Double tax2) {
        this.tax2 = tax2;
    }

    public Double getTax3() {
        return tax3;
    }

    public void setTax3(Double tax3) {
        this.tax3 = tax3;
    }
}
