package com.ndl.erp.dto;

import com.ndl.erp.domain.Familia;
import org.springframework.data.domain.Page;

import java.util.List;

public class FamiliasDTO {


    private Page<Familia> page;
    private List<Familia> familias;
    private Integer pagesTotal = 0;
    private Integer total = 0;

    private List<String> estados;

    public Page<Familia> getPage() {
        return page;
    }

    public void setPage(Page<Familia> page) {
        this.page = page;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Familia> getFamilias() {
        return familias;
    }

    public void setFamilias(List<Familia> familias) {
        this.familias = familias;
    }
}
