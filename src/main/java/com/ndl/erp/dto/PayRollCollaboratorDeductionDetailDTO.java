package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollCollaboratorDeductionDetail;
import com.ndl.erp.util.StringHelper;


import java.io.Serializable;

public class PayRollCollaboratorDeductionDetailDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000008L;

    private Integer id;

    private PayRoll payRoll;
    private Collaborator collaborator;

    private String description;
    private Double porcent;
    private Double mount;


    private Integer idUserRegister;
    private String startDate;
    private String endDate;


    public PayRollCollaboratorDeductionDetailDTO() {

    }

    public PayRollCollaboratorDeductionDetailDTO(PayRollCollaboratorDeductionDetail detail) {
        this.payRoll = detail.getPayRoll();
        this.collaborator = detail.getCollaborator();
        this.id = detail.getId();
        this.description = detail.getDescription();
        this.porcent = detail.getPorcent();
        this.mount = detail.getMount();
        this.startDate = StringHelper.getDate2String(detail.getStartDate());
        this.endDate = StringHelper.getDate2String(detail.getEndDate());
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

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
