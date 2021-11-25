package com.ndl.erp.dto;


import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;

public class FacturaElectronicaDTO {

    private String fullPathFileName;

    private String mensaje;

    private Boolean error= false;

    private FacturaElectronica facturaElectronica;
    private NotaCreditoElectronica notaCreditpElectronica;

    public String getFullPathFileName() {
        return fullPathFileName;
    }

    public void setFullPathFileName(String fullPathFileName) {
        this.fullPathFileName = fullPathFileName;
    }

    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    public NotaCreditoElectronica getNotaCreditpElectronica() {
        return notaCreditpElectronica;
    }

    public void setNotaCreditpElectronica(NotaCreditoElectronica notaCreditpElectronica) {
        this.notaCreditpElectronica = notaCreditpElectronica;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean errror) {
        error = errror;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
