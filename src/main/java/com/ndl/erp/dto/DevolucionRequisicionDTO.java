package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.RequisicionBodega;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DevolucionRequisicionDTO {

    private Devolucion devolucion;

    private RequisicionBodega requisicion;

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    public RequisicionBodega getRequisicion() {
        return requisicion;
    }

    public void setRequisicion(RequisicionBodega requisicion) {
        this.requisicion = requisicion;
    }
}