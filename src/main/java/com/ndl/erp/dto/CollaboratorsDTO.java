package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import org.springframework.data.domain.Page;

import java.util.List;


public class CollaboratorsDTO {
    private List<Collaborator> collaborators;

    private Page<Collaborator> collaboratorsPage;

    private Integer total;

    private Integer pagesTotal;

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> c) {
        this.collaborators = c;
    }

    public Page<Collaborator> getCollaboratorsPage() {
        return collaboratorsPage;
    }

    public void setCollaboratorsPage(Page<Collaborator> collaboratorsPage) {
        this.collaboratorsPage = collaboratorsPage;
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
