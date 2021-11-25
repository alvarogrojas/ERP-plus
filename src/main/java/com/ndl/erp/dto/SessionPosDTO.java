package com.ndl.erp.dto;

import com.ndl.erp.domain.SessionPos;

import java.util.List;

public class SessionPosDTO {

    List<String> estados;
    SessionPos current;

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public SessionPos getCurrent() {
        return current;
    }

    public void setCurrent(SessionPos current) {
        this.current = current;
    }
}
