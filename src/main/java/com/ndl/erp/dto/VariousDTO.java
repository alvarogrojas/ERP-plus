package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.FixedCost;
import com.ndl.erp.domain.Various;

import java.util.List;
//import org.springframework.data.domain.Page;


public class VariousDTO {

    private List<Currency> currencies;
    private List<String> periodicidad;
    private List<String> estados;

    private Various current = new Various();

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Various getCurrent() {
        return current;
    }

    public void setCurrent(Various current) {
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
