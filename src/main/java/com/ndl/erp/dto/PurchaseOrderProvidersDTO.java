package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.PurchaseOrderProvider;
import org.springframework.data.domain.Page;

import java.util.List;


public class PurchaseOrderProvidersDTO {
    private List<PurchaseOrderProvider> purchaseOrderProviders;

    private Page<PurchaseOrderProvider> purchaseOrderProvidersPage;
    private Page<Client> clientPage;

    private Integer total;

    private Integer pagesTotal;

    public List<PurchaseOrderProvider> getPurchaseOrderProviders() {
        return purchaseOrderProviders;
    }

    public void setPurchaseOrderProviders(List<PurchaseOrderProvider> purchaseOrderProviders) {
        this.purchaseOrderProviders = purchaseOrderProviders;
    }

    public Page<PurchaseOrderProvider> getPurchaseOrderProvidersPage() {
        return purchaseOrderProvidersPage;
    }

    public void setPurchaseOrderProvidersPage(Page<PurchaseOrderProvider> purchaseOrderProvidersPage) {
        this.purchaseOrderProvidersPage = purchaseOrderProvidersPage;
    }

    public void setClientPage(Page<Client> purchaseOrderProvidersPage) {
        this.clientPage = purchaseOrderProvidersPage;
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
