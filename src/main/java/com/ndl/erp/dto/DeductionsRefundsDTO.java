package com.ndl.erp.dto;

import com.ndl.erp.domain.DeductionsRefunds;
import org.springframework.data.domain.Page;


public class DeductionsRefundsDTO {


    private Page<DeductionsRefunds> deductionsRefundsPage;

    private Integer total;

    private Integer pagesTotal;

    public Page<DeductionsRefunds> getDeductionsRefundsPage() {
        return deductionsRefundsPage;
    }

    public void setDeductionsRefundsPage(Page<DeductionsRefunds> clientsPage) {
        this.deductionsRefundsPage = clientsPage;
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
