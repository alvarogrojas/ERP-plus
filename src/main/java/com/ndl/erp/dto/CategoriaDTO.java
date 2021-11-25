package com.ndl.erp.dto;

import com.ndl.erp.domain.Categoria;
import com.ndl.erp.domain.User;

import java.util.List;

public class CategoriaDTO {

        private Categoria current;
        private List<User>  users;
        private List<String> estadoList;
    private List<String> estados;

    public Categoria getCurrent() {
        return current;
    }

    public void setCurrent(Categoria current) {
        this.current = current;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<String> getEstados() {
        return estados;
    }
}
