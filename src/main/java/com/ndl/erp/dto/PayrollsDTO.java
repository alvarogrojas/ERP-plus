package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class PayrollsDTO {

    private Page<PayRoll> page;


    private List<Collaborator> collaborators;


    private Integer total;

    private Integer pagesTotal;




    public Page<PayRoll> getPage() {
        return page;
    }

    public void setPage(Page<PayRoll> page) {
        this.page = page;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
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
