package com.ndl.erp.dto;

import com.ndl.erp.domain.ClienteDescuento;
import org.springframework.data.domain.Page;
import java.util.List;

public class ClienteDescuentoListDTO {

        private List<ClienteDescuento> clienteDescuentoList;

        private Page<ClienteDescuento> clienteDescuentoListPage;

        private Integer total;

        private Integer pagesTotal;

    public List<ClienteDescuento> getClienteDescuentoList() {
        return clienteDescuentoList;
    }

    public void setClienteDescuentoList(List<ClienteDescuento> clienteDescuentoList) {
        this.clienteDescuentoList = clienteDescuentoList;
    }

    public Page<ClienteDescuento> getClienteDescuentoListPage() {
        return clienteDescuentoListPage;
    }

    public void setClienteDescuentoListPage(Page<ClienteDescuento> clienteDescuentoListPage) {
        this.clienteDescuentoListPage = clienteDescuentoListPage;
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
