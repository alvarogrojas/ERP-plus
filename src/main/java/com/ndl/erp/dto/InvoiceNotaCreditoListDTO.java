package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class InvoiceNotaCreditoListDTO {

    private Page<InvoiceNotaCredito> page;
    List<Client> clientList;
    List<Currency> currencyList;
    List<String> estadoHacienda;
    List<String> status;
    private Integer pagesTotal = 0;
    private Integer total = 0;


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

    public List<String> getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(List<String> estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Page<InvoiceNotaCredito> getPage() {
        return page;
    }

    public void setPage(Page<InvoiceNotaCredito> page) {
        this.page = page;
    }


    public Integer getPagesTotal() {
            return pagesTotal;
        }

        public void setPagesTotal(Integer pagesTotal) {
            this.pagesTotal = pagesTotal;
        }
    }