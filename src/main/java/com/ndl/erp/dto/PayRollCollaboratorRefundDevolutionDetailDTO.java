package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollCollaboratorRefundDevolutionDetail;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;

public class PayRollCollaboratorRefundDevolutionDetailDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000009L;

    private Integer id;

    private PayRoll payRoll;
    private Collaborator collaborator;

    private String description;
    private String type;
    private Double mount;
    private Integer coutaNumber;


    private Integer idUserRegister;
    private String startDate;
    private String endDate;


    public PayRollCollaboratorRefundDevolutionDetailDTO() {

    }

    public PayRollCollaboratorRefundDevolutionDetailDTO(PayRollCollaboratorRefundDevolutionDetail detail) {
        this.payRoll = detail.getPayRoll();
        this.collaborator = detail.getCollaborator();
        this.id = detail.getId();
        this.description = detail.getDescription();
        this.type = detail.getType();
        this.mount = detail.getMount();
        this.startDate = StringHelper.getDate2String(detail.getStartDate());
        this.endDate = StringHelper.getDate2String(detail.getEndDate());
        this.coutaNumber = detail.getCoutaNumber();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public Integer getCoutaNumber() {
        return coutaNumber;
    }

    public void setCoutaNumber(Integer coutaNumber) {
        this.coutaNumber = coutaNumber;
    }
}
