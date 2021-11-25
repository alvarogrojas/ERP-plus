package com.ndl.erp.dto;

import com.ndl.erp.domain.Bank;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.FixedCost;

import java.util.List;
//import org.springframework.data.domain.Page;


public class FixedCostDTO {

    private List<Currency> currencies;

    private List<String> periodicidad;
    private List<String> estados;


    private FixedCost current = new FixedCost();

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public FixedCost getCurrent() {
        return current;
    }

    public void setCurrent(FixedCost current) {
        this.current = current;
    }

    public List<String> getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(List<String> periodicidad) {
        this.periodicidad = periodicidad;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

}
