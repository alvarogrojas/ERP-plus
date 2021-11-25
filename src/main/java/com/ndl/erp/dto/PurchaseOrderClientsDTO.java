package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.PurchaseOrderClient;
import org.springframework.data.domain.Page;

import java.util.List;


public class PurchaseOrderClientsDTO {
    private List<PurchaseOrderClient> purchaseOrderClients;

    private Page<PurchaseOrderClient> purchaseOrderClientsPage;
    private Page<Client> clientPage;

    private Integer total;

    private Integer pagesTotal;

    public List<PurchaseOrderClient> getPurchaseOrderClients() {
        return purchaseOrderClients;
    }

    public void setPurchaseOrderClients(List<PurchaseOrderClient> purchaseOrderClients) {
        this.purchaseOrderClients = purchaseOrderClients;
    }

    public Page<PurchaseOrderClient> getPurchaseOrderClientsPage() {
        return purchaseOrderClientsPage;
    }

    public void setPurchaseOrderClientsPage(Page<PurchaseOrderClient> purchaseOrderClientsPage) {
        this.purchaseOrderClientsPage = purchaseOrderClientsPage;
    }

    public void setClientPage(Page<Client> purchaseOrderClientsPage) {
        this.clientPage = purchaseOrderClientsPage;
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
