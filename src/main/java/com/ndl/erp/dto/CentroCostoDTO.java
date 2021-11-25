package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.ContactClient;
import com.ndl.erp.domain.CostCenter;


import java.util.ArrayList;
import java.util.List;
//import org.springframework.data.domain.Page;


public class CentroCostoDTO {


    private List<String> estados;
    private List<String> purchaseOrderOptions;
    private List<Client> clientes;
    private List<ContactClient> contactClients;
    private List<String> types;

    private CostCenter current;

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Client> getClientes() {
        return clientes;
    }

    public void setClientes(List<Client> clientes) {
        this.clientes = clientes;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public CostCenter getCurrent() {
        return current;
    }

    public void setCurrent(CostCenter current) {
        this.current = current;
    }

    public List<ContactClient> getContactClients() {
        return contactClients;
    }

    public void setContactClients(List<ContactClient> contactClients) {
        this.contactClients = contactClients;
    }

    public List<String> getPurchaseOrderOptions() {
        return purchaseOrderOptions;
    }

    public void setPurchaseOrderOptions(List<String> purchaseOrderOptions) {
        this.purchaseOrderOptions = purchaseOrderOptions;
    }
}
