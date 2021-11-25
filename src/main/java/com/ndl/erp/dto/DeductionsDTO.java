package com.ndl.erp.dto;

import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.MotivoDevolucion;
import org.springframework.data.domain.Page;


public class DeductionsDTO {


    private Page<Deductions> clientsPage;

    private Integer total;

    private Integer pagesTotal;

    public Page<Deductions> getDeductionsPage() {
        return clientsPage;
    }

    public void setDeductionsPage(Page<Deductions> clientsPage) {
        this.clientsPage = clientsPage;
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
