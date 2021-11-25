package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.CostCenter;

import java.util.List;
//import org.springframework.data.domain.Page;


public class ClientDTO {


    private List<String> estados;
    private List<String> exonedoStates;

    private List<IdentificationType> typesId;

    private Client current;

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<IdentificationType> getTypesId() {
        return typesId;
    }

    public void setTypesId(List<IdentificationType> typesId) {
        this.typesId = typesId;
    }

    public Client getCurrent() {
        return current;
    }

    public void setCurrent(Client current) {
        this.current = current;
    }

    public List<String> getExonedoStates() {
        return exonedoStates;
    }

    public void setExonedoStates(List<String> exonedoStates) {
        this.exonedoStates = exonedoStates;
    }
}
