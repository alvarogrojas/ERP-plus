package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;

public class InvoiceNotaCreditoDTO {

    private InvoiceNotaCredito current;
    private Invoice invoice;
    List<Client> clientList;
    List<Currency> currencyList;
    List<EconomicActivity> economicActivityList;
    List<String> estadoHacienda;
    List<String> status;

    public InvoiceNotaCreditoDTO() {
        this.current = new InvoiceNotaCredito();
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public InvoiceNotaCredito getCurrent() {
        return current;
    }

    public void setCurrent(InvoiceNotaCredito current) {
        this.current = current;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<EconomicActivity> getEconomicActivityList() {
        return economicActivityList;
    }

    public void setEconomicActivityList(List<EconomicActivity> economicActivityList) {
        this.economicActivityList = economicActivityList;
    }

    public List<String> getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(List<String> estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }
}
