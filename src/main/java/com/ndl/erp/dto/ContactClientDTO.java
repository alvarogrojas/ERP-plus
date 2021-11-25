package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.ContactClient;

import java.util.List;
//import org.springframework.data.domain.Page;


public class ContactClientDTO {




    private ContactClient current;

    private Client client;

    public ContactClientDTO() {
        this.current = new ContactClient();
    }

    public ContactClient getCurrent() {
        return current;
    }

    public void setCurrent(ContactClient current) {
        this.current = current;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
