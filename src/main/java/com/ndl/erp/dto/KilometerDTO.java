package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;
//import org.springframework.data.domain.Page;


public class KilometerDTO {


    private List<String> estados;
    private List<Department> departments;
    private List<Currency> currency;
    private List<CostCenter> costCenters;
    private List<Collaborator> collaborators;

    private FeeVehiculeFuel feeVehiculeFuel;

    private Kilometer current;


    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public Kilometer getCurrent() {
        return current;
    }

    public void setCurrent(Kilometer current) {
        this.current = current;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> colaborators) {
        this.collaborators = colaborators;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public FeeVehiculeFuel getFeeVehiculeFuel() {
        return feeVehiculeFuel;
    }

    public void setFeeVehiculeFuel(FeeVehiculeFuel feeVehiculeFuel) {
        this.feeVehiculeFuel = feeVehiculeFuel;
    }
}
