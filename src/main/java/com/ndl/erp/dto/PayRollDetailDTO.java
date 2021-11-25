package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollDetail;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;
import java.util.List;

public class PayRollDetailDTO implements Serializable, Comparable<PayRollDetailDTO> {

    private static final long serialVersionUID = 1777000000000000007L;

    private Integer id;
    private String collaborator;

    private Double grossSalary;
    private Double deducctions;
    private Double netSalary;

    private Double devolutions;
    private Double refunds;

    private Double totalSalary;
    private Integer collaboratorId;



    public PayRollDetailDTO(PayRollDetail d) {
        this.collaborator = d.getCollaborator().getName() + " " + d.getCollaborator().getLastName();
        this.grossSalary = d.getCrudeSalary();
        this.deducctions = d.getDeducctions() +  d.getCcss() + d.getTax1() + d.getTax2() + d.getTax3();
        this.netSalary =  this.grossSalary - this.deducctions;
        this.devolutions =  d.getDevolutions();
        this.refunds =  d.getRefunds();
        this.totalSalary =  d.getNetSalary();
        this.id =  d.getId();
        this.collaboratorId =  d.getCollaborator().getId();

    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getDeducctions() {
        return deducctions;
    }

    public void setDeducctions(Double deducctions) {
        this.deducctions = deducctions;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public Double getDevolutions() {
        return devolutions;
    }

    public void setDevolutions(Double devolutions) {
        this.devolutions = devolutions;
    }

    public Double getRefunds() {
        return refunds;
    }

    public void setRefunds(Double refunds) {
        this.refunds = refunds;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    @Override
    public int compareTo(PayRollDetailDTO o) {
        return this.collaborator.compareTo(o.collaborator);
    }

    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
