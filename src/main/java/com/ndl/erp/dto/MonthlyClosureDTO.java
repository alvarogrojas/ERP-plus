package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonthlyClosureDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000007L;

    private MonthlyClosure current;

    private List<PayRollDetailDTO> details;


    private Double totalGrossSalary = 0d,
            totaDeducctions = 0d,
            totalNetSalary = 0d,
            totalDevolutions = 0d,
            totalRefunds = 0d,
            totalSalary = 0d;


    public MonthlyClosureDTO() {

    }


    public MonthlyClosure getCurrent() {
        return current;
    }

    public void setCurrent(MonthlyClosure current) {
        this.current = current;
    }

    public List<PayRollDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<PayRollDetailDTO> details) {
        this.details = details;
    }

    public Double getTotalGrossSalary() {
        return totalGrossSalary;
    }

    public void setTotalGrossSalary(Double totalGrossSalary) {
        this.totalGrossSalary = totalGrossSalary;
    }

    public Double getTotaDeducctions() {
        return totaDeducctions;
    }

    public void setTotaDeducctions(Double totaDeducctions) {
        this.totaDeducctions = totaDeducctions;
    }

    public Double getTotalNetSalary() {
        return totalNetSalary;
    }

    public void setTotalNetSalary(Double totalNetSalary) {
        this.totalNetSalary = totalNetSalary;
    }

    public Double getTotalDevolutions() {
        return totalDevolutions;
    }

    public void setTotalDevolutions(Double totalDevolutions) {
        this.totalDevolutions = totalDevolutions;
    }

    public Double getTotalRefunds() {
        return totalRefunds;
    }

    public void setTotalRefunds(Double totalRefunds) {
        this.totalRefunds = totalRefunds;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }
}
