package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPayNotaCredito;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Provider;
import org.springframework.data.domain.Page;

import java.util.List;

public class BillPayNotaCreditosDTO {

        private Page<BillPayNotaCredito> page;
        List<Provider> providers;
        List<Currency> currencies;
        List<String> status;
        private Integer pagesTotal = 0;
        private Integer total = 0;

    public Page<BillPayNotaCredito> getPage() {
        return page;
    }

    public void setPage(Page<BillPayNotaCredito> page) {
        this.page = page;
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

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
