package com.ndl.erp.dto;

import com.ndl.erp.domain.Categoria;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoriasDTO {

    private Page<Categoria> page;
    private List<String> estados;

    private Integer total;

    private Integer pagesTotal;



    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public Page<Categoria> getPage() {
        return page;
    }

    public void setPage(Page<Categoria> page) {
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

