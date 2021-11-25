package com.ndl.erp.dto;

import com.ndl.erp.domain.ConfirmaRechazaDocumento;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public class ConfirmaRechazaDTO {

    private Date fechaInicio;

    private Date fechaFinal;

    private Page<ConfirmaRechazaDocumento> confirmRechazos;
    private List<String> estados;
    private Integer total;
    private Integer pagesTotal;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Page<ConfirmaRechazaDocumento> getConfirmRechazos() {
        return confirmRechazos;
    }

    public void setConfirmRechazos(Page<ConfirmaRechazaDocumento> confirmRechazos) {
        this.confirmRechazos = confirmRechazos;
    }

    public void setEstados(List<String> states) {
        this.estados = states;
    }

    public List<String> getEstados() {
        return estados;
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
