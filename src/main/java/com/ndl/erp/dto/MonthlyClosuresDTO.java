package com.ndl.erp.dto;


import com.ndl.erp.domain.MonthlyClosure;

import org.springframework.data.domain.Page;

public class MonthlyClosuresDTO {

//    private Page<MonthlyClosure> page;
    private Page<MonthlyClosureCustomDTO> page;


    private Integer total;

    private Integer pagesTotal;

//    public Page<MonthlyClosure> getPage() {
//        return page;
//    }
//
//    public void setPage(Page<MonthlyClosure> page) {
//        this.page = page;
//    }


    public Page<MonthlyClosureCustomDTO> getPage() {
        return page;
    }

    public void setPage(Page<MonthlyClosureCustomDTO> page) {
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
