package com.ndl.erp.dto;

import com.ndl.erp.domain.UnidadMedida;
import org.springframework.data.domain.Page;

import java.util.List;


public class UnidadesMedidaDTO {


    private Page<UnidadMedida> page;

    private Integer total;

    private Integer pagesTotal;

    List<String> estados;

    public Page<UnidadMedida> getPage() {
        return page;
    }

    public void setPage(Page<UnidadMedida> page) {
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

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }
}