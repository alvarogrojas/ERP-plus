package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.ContactClient;
import org.springframework.data.domain.Page;

import java.util.List;


public class ContactClientsDTO {
    private List<ContactClient> contacts;

    private Page<ContactClient> contactsClientPage;

    private Client client;

    private Integer total;

    private Integer pagesTotal;

    public List<ContactClient> getContactClients() {
        return contacts;
    }

    public void setContactClients(List<ContactClient> clients) {
        this.contacts = clients;
    }

    public Page<ContactClient> getContactsClientPage() {
        return contactsClientPage;
    }

    public void setContactsClientPage(Page<ContactClient> contactsClientPage) {
        this.contactsClientPage = contactsClientPage;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
