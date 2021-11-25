package com.ndl.erp.dto;


import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDTO {


    private List<String> statuses;

    private List<Currency> currencies = new ArrayList<>();

    private ExchangeRate current = new ExchangeRate();

    public List<String> getStatuses() {
        return statuses;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public ExchangeRate getCurrent() {
        return current;
    }

    public void setCurrent(ExchangeRate current) {
        this.current = current;
    }
}