package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.RequisicionBodega;
import com.ndl.erp.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class RequisicionBodegaListDTO {

       private Page<RequisicionBodega> page;
       private Integer total = 0;
       private Integer pagesTotal = 0;
       private List<String> estadosList;
       private List<User> userList;
       private List<Bodega> bodegaList;
       private List<RequisicionBodega> list;

    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<String> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<String> estadosList) {
        this.estadosList = estadosList;
    }

    public Page<RequisicionBodega> getPage() {
        return page;
    }

    public void setPage(Page<RequisicionBodega> page) {
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

    public List<RequisicionBodega> getList() {
        return list;
    }

    public void setList(List<RequisicionBodega> list) {
        this.list = list;
    }
}
