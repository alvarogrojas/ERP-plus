package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;

public class BillPayNotaCreditoDTO {

    private BillPayNotaCredito current;
    private BillPay billPay;
    List<String> status;
    List<Provider> providers;
    List<Currency> currencies;
    List<EconomicActivity> economicActivities;

    public BillPayNotaCredito getCurrent() {
        return current;
    }

    public void setCurrent(BillPayNotaCredito current) {
        this.current = current;
    }

    public BillPay getBillPay() {
        return billPay;
    }

    public void setBillPay(BillPay billPay) {
        this.billPay = billPay;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<EconomicActivity> getEconomicActivities() {
        return economicActivities;
    }

    public void setEconomicActivities(List<EconomicActivity> economicActivities) {
        this.economicActivities = economicActivities;
    }
}