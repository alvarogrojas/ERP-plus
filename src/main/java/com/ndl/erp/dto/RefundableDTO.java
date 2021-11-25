package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;



public class RefundableDTO {


    private List<String> estados;
    private List<String> types;
    private List<String> parentTypes;
    private List<String> typeExpends;
    private List<Department> departments;
    private List<Currency> currency;
    private List<CostCenter> costCenters;
    private List<Collaborator> collaborators;
    private List<GeneralParameter> taxes;
    private List<ServiceCabys> serviceCabys = new ArrayList<>();
    private List<InventarioItem> items = new ArrayList<>();
    private List<Bodega> bodegas;
    private Refundable current;
    private List<ExchangeRate> exchangeRates = new ArrayList<>();


    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

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

    public Refundable getCurrent() {
        return current;
    }

    public void setCurrent(Refundable current) {
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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<GeneralParameter> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<GeneralParameter> taxes) {
        this.taxes = taxes;
    }

    public List<String> getParentTypes() {
        return parentTypes;
    }

    public void setParentTypes(List<String> parentTypes) {
        this.parentTypes = parentTypes;
    }

    public List<ServiceCabys> getServiceCabys() {
        return serviceCabys;
    }

    public void setServiceCabys(List<ServiceCabys> serviceCabys) {
        if (serviceCabys==null) {
            return;
        }
        this.serviceCabys = serviceCabys;
    }

    public List<InventarioItem> getItems() {
        return items;
    }

    public void setItems(List<InventarioItem> items) {
        if (items==null) {
            return;
        }
        this.items = items;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<String> getTypeExpends() {
        return typeExpends;
    }

    public void setTypeExpends(List<String> typeExpends) {
        this.typeExpends = typeExpends;
    }
}
