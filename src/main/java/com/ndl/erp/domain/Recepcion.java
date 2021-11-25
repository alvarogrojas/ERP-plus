package com.ndl.erp.domain;

import com.ndl.erp.fe.dtos.Emisor;
import com.ndl.erp.fe.dtos.Receptor;

public class Recepcion {

    private String clave;

    private String fecha;
    private String consecutivoReceptor;

    private Emisor emisor = new Emisor();

    private Receptor receptor = new Receptor();

    private String comprobanteXml;

    public void setConsecutivoReceptor(String consecutivoReceptor) {
        this.consecutivoReceptor = consecutivoReceptor;
    }


    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public void setReceptor(Receptor receptor) {
        this.receptor = receptor;
    }

    public String getComprobanteXml() {
        return comprobanteXml;
    }

    public void setComprobanteXml(String comprobanteXml) {
        this.comprobanteXml = comprobanteXml;
    }


}
