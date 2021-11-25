package com.ndl.erp.dto;

import com.ndl.erp.domain.Role;
import org.springframework.data.domain.Page;

import java.util.List;


public class RolesDTO {
    private Page<Role> rolesPage;

    private Integer total;

    private Integer pagesTotal;

    public Page<Role> getRolesPage() {
        return rolesPage;
    }

    public void setRolesPage(Page<Role> rolesPage) {
        this.rolesPage = rolesPage;
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
