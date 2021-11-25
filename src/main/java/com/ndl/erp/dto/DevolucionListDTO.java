package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class DevolucionListDTO {

    private Page<Devolucion> page;
    private List<String> estadoList;
    private List<MotivoDevolucion> motivoDevolucionList;
    private Integer pagesTotal = 0;
    private Integer total = 0;



    public void setPage(Page<Devolucion> page) {
        this.page = page;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<MotivoDevolucion> getMotivoDevolucionList() {
        return motivoDevolucionList;
    }

    public void setMotivoDevolucionList(List<MotivoDevolucion> motivoDevolucionList) {
        this.motivoDevolucionList = motivoDevolucionList;
    }

    public Page<Devolucion> getPage() {
        return page;
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