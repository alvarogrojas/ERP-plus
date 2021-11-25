package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.CostCenter;
import org.springframework.data.domain.Page;

import java.util.List;


public class ClientsDTO {
    private List<Client> clients;

    private Page<Client> clientsPage;

    private Integer total;

    private Integer pagesTotal;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Page<Client> getClientsPage() {
        return clientsPage;
    }

    public void setClientsPage(Page<Client> clientsPage) {
        this.clientsPage = clientsPage;
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
