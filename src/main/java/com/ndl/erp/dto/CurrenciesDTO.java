package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import org.springframework.data.domain.Page;

import java.util.List;


public class CurrenciesDTO {

    private Page<Currency> currencysPage;

    private Integer total;

    private Integer pagesTotal;

    public Page<Currency> getCurrencysPage() {
        return currencysPage;
    }

    public void setCurrencysPage(Page<Currency> currencysPage) {
        this.currencysPage = currencysPage;
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
