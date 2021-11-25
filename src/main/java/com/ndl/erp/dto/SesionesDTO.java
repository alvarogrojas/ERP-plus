package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.SessionPos;
import org.springframework.data.domain.Page;

import java.util.List;

public class SesionesDTO {

    private Page<SessionPos> page;
    private List<String> estados;
    private Integer pagesTotal = 0;
    private Integer total = 0;

    private SessionPos sessionAbierta;

    private String currency;
    private Double totalAmount;
    private List<Bodega> bodegas;


    public Page<SessionPos> getPage() {
        return page;
    }

    public void setPage(Page<SessionPos> page) {
        this.page = page;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SessionPos getSessionAbierta() {
        return sessionAbierta;
    }

    public void setSessionAbierta(SessionPos sessionAbierta) {
        this.sessionAbierta = sessionAbierta;
    }

    public void setBodegas(List<Bodega> b) {
        this.bodegas = b;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }
}