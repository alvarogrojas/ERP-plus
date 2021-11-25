package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class InvoicesDTO {

    private Page<Invoice> page;


    private List<Currency> currencies;
    private List<Client> clients;

    private List<String> states;
    private List<String> types;
    private List<String> statesHacieda;

    private Integer total = 0;

    private Integer pagesTotal = 0;

    private String currency;
    private Double totalAmount;
    private Double totalKilometers;

    private ArrayList<TotalDTO> totals;

    public Page<Invoice> getPage() {
        return page;
    }

    public void setPage(Page<Invoice> page) {
        this.page = page;

    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<String> getStatesHacieda() {
        return statesHacieda;
    }

    public void setStatesHacieda(List<String> statesHacieda) {
        this.statesHacieda = statesHacieda;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }


    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(Double totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public ArrayList<TotalDTO> getTotals() {
        return totals;
    }

    public void setTotals(ArrayList<TotalDTO> totals) {
        this.totals = totals;
    }
}
