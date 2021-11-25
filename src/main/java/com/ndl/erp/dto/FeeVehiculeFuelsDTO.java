package com.ndl.erp.dto;

import com.ndl.erp.domain.FeeVehiculeFuel;
import org.springframework.data.domain.Page;


public class FeeVehiculeFuelsDTO {

    private Page<FeeVehiculeFuel> page;

    private Integer total;

    private Integer pagesTotal;

    public Page<FeeVehiculeFuel> getPage() {
        return page;
    }

    public void setPage(Page<FeeVehiculeFuel> p) {
        this.page = p;
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
