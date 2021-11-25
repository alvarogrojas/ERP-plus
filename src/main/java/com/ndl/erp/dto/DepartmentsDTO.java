package com.ndl.erp.dto;

import com.ndl.erp.domain.Department;
import org.springframework.data.domain.Page;

public class DepartmentsDTO {

    private Page<Department> page;

    private Integer total;

    private Integer pagesTotal;

    public Page<Department> getPage() {
        return page;
    }

    public void setPage(Page<Department> page) {
        this.page = page;
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
