package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;

public class InvoiceFlatDTO {

    private Invoice current;

    private EmpresaTiqueteDTO empresa;

    public Invoice getCurrent() {
        return current;
    }

    public void setCurrent(Invoice current) {
        this.current = current;
    }

    public EmpresaTiqueteDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaTiqueteDTO empresa) {
        this.empresa = empresa;
    }
}
