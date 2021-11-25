package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.domain.SessionPos;

import java.util.List;

public class TransactionsDTO {


    public List<Invoice> invoiceList;
    public List<InvoiceNotaCredito> invoiceListNc;
    public SessionPos sessionPos;

    public Currency defaultCurrency;

    Double totalFacturas;
    Double totalNcs;

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
        if (this.invoiceList!=null && this.invoiceList.size()>0) {
            this.sessionPos = this.invoiceList.get(0).getSessionPos();
        }
    }

    public SessionPos getSessionPos() {
        return sessionPos;
    }

    public void setSessionPos(SessionPos sessionPos) {
        this.sessionPos = sessionPos;
    }

    public List<InvoiceNotaCredito> getInvoiceListNc() {
        return invoiceListNc;
    }

    public void setInvoiceListNc(List<InvoiceNotaCredito> invoiceListNc) {
        this.invoiceListNc = invoiceListNc;
    }

    public Double getTotalFacturas() {
        return totalFacturas;
    }

    public void setTotalFacturas(Double totalFacturas) {
        this.totalFacturas = totalFacturas;
    }

    public Double getTotalNcs() {
        return totalNcs;
    }

    public void setTotalNcs(Double totalNcs) {
        this.totalNcs = totalNcs;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
