package com.ndl.erp.dto;

import com.ndl.erp.domain.Provider;

import java.util.List;
//import org.springframework.data.domain.Page;


public class ProviderDTO {


    private List<String> estados;
//    private List<String> exonedoStates;

//    private List<IdentificationType> typesId;

    private Provider current = new Provider();

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

//    public List<IdentificationType> getTypesId() {
//        return typesId;
//    }
//
//    public void setTypesId(List<IdentificationType> typesId) {
//        this.typesId = typesId;
//    }

    public Provider getCurrent() {
        return current;
    }

    public void setCurrent(Provider current) {
        this.current = current;
    }


}
