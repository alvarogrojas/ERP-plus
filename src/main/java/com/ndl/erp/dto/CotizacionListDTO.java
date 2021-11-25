package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.Cotizacion;
import com.ndl.erp.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class CotizacionListDTO {

    private Page<Cotizacion> page;
    private List<Client> clienteList;
    private List<User> vendedorList;
    private List<List> EstadoList;
    private Integer pagesTotal = 0;
    private Integer total = 0;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Page<Cotizacion> getPage() {
        return page;
    }

    public void setPage(Page<Cotizacion> page) {
        this.page = page;
    }

    public List<Client> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Client> clienteList) {
        this.clienteList = clienteList;
    }

    public List<User> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(List<User> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public List<List> getEstadoList() {
        return EstadoList;
    }

    public void setEstadoList(List<List> estadoList) {
        EstadoList = estadoList;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }
}