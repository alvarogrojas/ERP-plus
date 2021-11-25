package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.PurchaseOrderProvider;

import java.util.List;
//import org.springframework.data.domain.Page;


public class PurchaseOrderProviderDTO {


    private List<String> estados;
    private List<Currency> currencies;
    private Iterable<CostCenter> costCenters;


    private PurchaseOrderProvider current = new PurchaseOrderProvider();

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }


    public PurchaseOrderProvider getCurrent() {
        return current;
    }

    public void setCurrent(PurchaseOrderProvider current) {
        this.current = current;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Iterable<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(Iterable<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }
}
