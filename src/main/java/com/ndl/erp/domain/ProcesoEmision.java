package com.ndl.erp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="proceso_emision")
public class ProcesoEmision implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String tarea;

    private String resultado; // FALLO, EXITOSA

    private Integer tenantId;

    private Integer tenantIdFactura;

    private Integer errorId;

    private String tipo; // FACTURA, NOTA_CREDITO, RECHAZO_CONFIRMACION, TIQUETE

    private Date fechaEjecucion;
//    private Integer facturaTenantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Integer getTenantIdFactura() {
        return tenantIdFactura;
    }

    public void setTenantIdFactura(Integer facturaTenantId) {
        this.tenantIdFactura = facturaTenantId;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

//    public void setFacturaTenantId(Integer facturaTenantId) {
//        this.facturaTenantId = facturaTenantId;
//    }

//    public Integer getFacturaTenantId() {
//        return facturaTenantId;
//    }
}
