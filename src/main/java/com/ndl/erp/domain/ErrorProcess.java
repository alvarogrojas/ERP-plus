package com.ndl.erp.domain;

import com.ndl.erp.fe.core.BillError;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="error_process")
public class ErrorProcess implements BillError, Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer tenantId;

    private Integer processId;

    private String clave;

    private String consecutivo;

    private String message;

    private String tareaCode;

    private Date errorDate;

    private String tenantIdFactura;

    @Override
    public void setErrorMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTareaCode() {
        return tareaCode;
    }

    public void setTareaCode(String tareaCode) {
        this.tareaCode = tareaCode;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getTenantIdFactura() {
        return tenantIdFactura;
    }

    public void setTenantIdFactura(String tenantIdFactura) {
        this.tenantIdFactura = tenantIdFactura;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
}
