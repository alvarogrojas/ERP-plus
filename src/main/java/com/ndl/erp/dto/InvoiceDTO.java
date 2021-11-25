package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;

public class InvoiceDTO {

    private List<String> status;
    private List<String> statesHacieda;
    private List<String> types;
    private List<PurchaseOrderClient> pocs;
    private List<Currency> currencies;
    private List<CostCenter> costCenters;
    private List<Client> clients;

    private List<EconomicActivity> activities;

    private Invoice current;
    private InvoiceNotaCredito notaCredito;
    private SessionPos sessionPos;

    private EmpresaTiqueteDTO empresaTiqueteDTO;

    private List<MedioPago> medioPagos;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getStatesHacieda() {
        return statesHacieda;
    }

    public void setStatesHacieda(List<String> statesHacieda) {
        this.statesHacieda = statesHacieda;
    }

    public List<PurchaseOrderClient> getPocs() {
        return pocs;
    }

    public void setPocs(List<PurchaseOrderClient> pocs) {
        this.pocs = pocs;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Invoice getCurrent() {
        return current;
    }

    public void setCurrent(Invoice current) {
        this.current = current;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<EconomicActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<EconomicActivity> activities) {
        this.activities = activities;
    }

    public void setInvoiceNotaCredito(InvoiceNotaCredito notaCreditoData) {
        this.notaCredito = notaCreditoData;
    }

    public InvoiceNotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(InvoiceNotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public void setSessionPos(SessionPos sessionPosAbierta) {
        this.sessionPos = sessionPosAbierta;
    }

    public SessionPos getSessionPos() {
        return sessionPos;
    }

    public void setMedioPagos(List<MedioPago> allMedioPagosActivo) {
        this.medioPagos = allMedioPagosActivo;
    }

    public void setEmpresaTiqueteDTO(EmpresaTiqueteDTO empresaTiqueteInfo) {
        this.empresaTiqueteDTO = empresaTiqueteInfo;
    }

    public EmpresaTiqueteDTO getEmpresaTiqueteDTO() {
        return empresaTiqueteDTO;
    }

    public List<MedioPago> getMedioPagos() {
        return medioPagos;
    }
}
