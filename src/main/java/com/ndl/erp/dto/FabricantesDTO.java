package com.ndl.erp.dto;

import com.ndl.erp.domain.Fabricante;
import org.springframework.data.domain.Page;

import java.util.List;

public class FabricantesDTO {

    private Page<Fabricante> page;

    private Integer pagesTotal = 0;
    private Integer total = 0;

    private List<String> estados;

    public Page<Fabricante> getPage() {
        return page;
    }

    public void setPage(Page<Fabricante> page) {
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
}