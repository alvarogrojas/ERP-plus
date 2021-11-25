package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Deductions;

import java.util.List;
//import org.springframework.data.domain.Page;


public class DeductionDTO {


    private List<String> types;


    private Deductions current;

    private List<Collaborator> colaborators;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Deductions getCurrent() {
        return current;
    }

    public void setCurrent(Deductions current) {
        this.current = current;
    }

    public List<Collaborator> getColaborators() {
        return colaborators;
    }

    public void setColaborators(List<Collaborator> colaborators) {
        this.colaborators = colaborators;
    }
}
