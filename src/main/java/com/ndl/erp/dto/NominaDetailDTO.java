package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollDetail;

import java.io.Serializable;
import java.util.List;

public class NominaDetailDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000007L;

    private PayRoll payRoll;
    private PayRollDetail  payRollDetail;
    private Collaborator collaborator;
    private List<PayRollCollaboratorDeductionDetailDTO> deductios;
    private List<PayRollCollaboratorRefundDevolutionDetailDTO> refundsDevols;
    private List<PayRollCollaboratorDetailDTO> payRollCollaboratorDetail;
    private Double totalPayrollColaborator = 0d;
    private Double totalRefundsDevols = 0d;
    private Double totalDeductions = 0d;

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public PayRollDetail getPayRollDetail() {
        return payRollDetail;
    }

    public void setPayRollDetail(PayRollDetail payRollDetail) {
        this.payRollDetail = payRollDetail;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public List<PayRollCollaboratorDeductionDetailDTO> getDeductios() {
        return deductios;
    }

    public void setDeductios(List<PayRollCollaboratorDeductionDetailDTO> deductios) {
        this.deductios = deductios;
    }

    public List<PayRollCollaboratorRefundDevolutionDetailDTO> getRefundsDevols() {
        return refundsDevols;
    }

    public void setRefundsDevols(List<PayRollCollaboratorRefundDevolutionDetailDTO> refundsDevols) {
        this.refundsDevols = refundsDevols;
    }

    public List<PayRollCollaboratorDetailDTO> getPayRollCollaboratorDetail() {
        return payRollCollaboratorDetail;
    }

    public void setPayRollCollaboratorDetail(List<PayRollCollaboratorDetailDTO> payRollCollaboratorDetail) {
        this.payRollCollaboratorDetail = payRollCollaboratorDetail;
        this.totalPayrollColaborator = 0d;
        for (PayRollCollaboratorDetailDTO d: payRollCollaboratorDetail) {
            this.totalPayrollColaborator = totalPayrollColaborator + d.getTotal();
        }
    }

    public Double getTotalPayrollColaborator() {
        return totalPayrollColaborator;
    }

    public void setTotalPayrollColaborator(Double totalPayrollColaborator) {
        this.totalPayrollColaborator = totalPayrollColaborator;
    }

    public Double getTotalRefundsDevols() {
        return totalRefundsDevols;
    }

    public void setTotalRefundsDevols(Double total) {
        this.totalRefundsDevols = total;
    }

    public void setTotalDeductions(Double total) {
        this.totalDeductions = total;

    }

    public Double getTotalDeductions() {
        return totalDeductions;
    }
}
