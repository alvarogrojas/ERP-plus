package com.ndl.erp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
public class PayRollCollaboratorDeductionDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="id_pay_roll", referencedColumnName="id")
    private PayRoll payRoll;

    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

//    private Integer idCollaborator;
//    private Integer idPayRoll;

    private String description;
    private Double porcent;
    private Double mount;


    private Integer idUserRegister;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public PayRoll getPayRoll() {
//        return payRoll;
//    }
//
//    public void setPayRoll(PayRoll payRoll) {
//        this.payRoll = payRoll;
//    }
//
//    public Collaborator getCollaborator() {
//        return collaborator;
//    }
//
//    public void setCollaborator(Collaborator collaborator) {
//        this.collaborator = collaborator;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPorcent() {
        return porcent;
    }

    public void setPorcent(Double porcent) {
        this.porcent = porcent;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    //    public Integer getIdCollaborator() {
//        return idCollaborator;
//    }
//
//    public void setIdCollaborator(Integer idCollaborator) {
//        this.idCollaborator = idCollaborator;
//    }
//
//    public Integer getIdPayRoll() {
//        return idPayRoll;
//    }
//
//    public void setIdPayRoll(Integer idPayRoll) {
//        this.idPayRoll = idPayRoll;
//    }
}
