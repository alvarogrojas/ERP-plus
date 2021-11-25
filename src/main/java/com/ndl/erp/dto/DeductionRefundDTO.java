package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.DeductionsRefunds;

import java.util.List;
//import org.springframework.data.domain.Page;


public class DeductionRefundDTO {


    private List<String> types;


    private DeductionsRefunds current;

    private List<Collaborator> collaborators;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public DeductionsRefunds getCurrent() {
        return current;
    }

    public void setCurrent(DeductionsRefunds current) {
        this.current = current;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> colaborators) {
        this.collaborators = colaborators;
    }
}
