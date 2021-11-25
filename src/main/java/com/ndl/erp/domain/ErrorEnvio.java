package com.ndl.erp.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="error_envio")
public class ErrorEnvio {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer facturaId;

    private String mensaje;

    private String proceso;

    private Integer ingresadaPor;

    private java.util.Date fechaIngreso;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Integer getIngresadaPor() {
        return ingresadaPor;
    }

    public void setIngresadaPor(Integer ingresadaPor) {
        this.ingresadaPor = ingresadaPor;
    }

    public java.util.Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(java.util.Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
