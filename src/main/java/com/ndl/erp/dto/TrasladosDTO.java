package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Traslado;
import com.ndl.erp.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class TrasladosDTO {
    private Page<Traslado> page;
    private Integer total = 0;
    private Integer pagesTotal = 0;
    private List<String> estados;
    private List<User> users;
    private List<Bodega> bodegas;

    public Page<Traslado> getPage() {
        return page;
    }

    public void setPage(Page<Traslado> page) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }
}
