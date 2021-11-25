package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;


public class LaborCostDTO {


    private List<CostCenter> costCenters;


    private LaborCost current;

    private List<Collaborator> collaborators;
    private Currency currency;

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public LaborCost getCurrent() {
        return current;
    }

    public void setCurrent(LaborCost current) {
        this.current = current;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> colaborators) {
        this.collaborators = colaborators;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;

    }

    public Currency getCurrency() {
        return currency;
    }
}
