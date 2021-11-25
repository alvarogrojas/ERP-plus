package com.ndl.erp.dto;

import com.ndl.erp.domain.ExchangeRate;
import org.springframework.data.domain.Page;

import java.util.List;

public class ExchangeRatesDTO {
    private List<ExchangeRate> exchangeRates;

    private Page<ExchangeRate> exchangeRatesPage;

    private Integer total;

    private Integer pagesTotal;

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public Page<ExchangeRate> getExchangeRatesPage() {
        return exchangeRatesPage;
    }

    public void setExchangeRatesPage(Page<ExchangeRate> exchangeRatesPage) {
        this.exchangeRatesPage = exchangeRatesPage;
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
}