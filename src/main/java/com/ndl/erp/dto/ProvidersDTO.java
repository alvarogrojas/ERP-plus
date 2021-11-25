package com.ndl.erp.dto;

import com.ndl.erp.domain.Provider;
import org.springframework.data.domain.Page;

import java.util.List;


public class ProvidersDTO {
    private List<Provider> providers;

    private Page<Provider> providersPage;

    private Integer total;

    private Integer pagesTotal;

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public Page<Provider> getProvidersPage() {
        return providersPage;
    }

    public void setProvidersPage(Page<Provider> providersPage) {
        this.providersPage = providersPage;
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
