package com.ndl.erp.dto;

import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.domain.MotivoDevolucion;
import com.ndl.erp.domain.RequisicionBodega;

import java.util.List;

public class DevolucionDTO {

    private Devolucion current;
    private RequisicionBodega requisicionBodega;
    private InvoiceNotaCredito invoiceNotaCredito;
    private List<String> estadoList;
    private List<MotivoDevolucion> motivoDevolucionList;

    public InvoiceNotaCredito getInvoiceNotaCredito() {
        return invoiceNotaCredito;
    }

    public void setInvoiceNotaCredito(InvoiceNotaCredito invoiceNotaCredito) {
        this.invoiceNotaCredito = invoiceNotaCredito;
    }

    public Devolucion getCurrent() {
        return current;
    }

    public void setCurrent(Devolucion current) {
        this.current = current;
    }

    public RequisicionBodega getRequisicionBodega() {
        return requisicionBodega;
    }

    public void setRequisicionBodega(RequisicionBodega requisicionBodega) {
        this.requisicionBodega = requisicionBodega;
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
}
