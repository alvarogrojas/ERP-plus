package com.ndl.erp.dto;

import com.ndl.erp.domain.Department;

import java.util.List;


public class DepartmentDTO {
    
    private Department current;

    private List<String> status;

    public Department getCurrent() {
        return current;
    }

    public void setCurrent(Department current) {
        this.current = current;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
