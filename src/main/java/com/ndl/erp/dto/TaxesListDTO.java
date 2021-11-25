package com.ndl.erp.dto;

import com.ndl.erp.domain.Taxes;
import com.ndl.erp.domain.TaxesIva;
import org.springframework.data.domain.Page;

import java.util.List;


public class TaxesListDTO {


    private Page<Taxes> page;

    private Integer total;

    private Integer pagesTotal;

    private List<TaxesIva> ivas;

    public Page<Taxes> getPage() {
        return page;
    }

    public void setPage(Page<Taxes> page) {
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

    public List<TaxesIva> getIvas() {
        return ivas;
    }

    public void setIvas(List<TaxesIva> ivas) {
        this.ivas = ivas;
    }
}
