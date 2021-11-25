package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Inventario;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class InventariosDTO {
    private Page<Inventario> page;

    private List<Bodega> bodegas;

    private List<String> filtroExistencias = new ArrayList<>();

    private Integer pagesTotal = 0;
    private Integer total = 0;

    public List<String> getFiltroExistencias() {
        return filtroExistencias;
    }

    public void setFiltroExistencias(List<String> filtroExistencias) {
        this.filtroExistencias = filtroExistencias;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public Page<Inventario> getPage() {
        return page;
    }

    public void setPage(Page<Inventario> page) {
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
}
