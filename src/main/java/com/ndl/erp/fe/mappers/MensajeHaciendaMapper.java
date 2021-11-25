package com.ndl.erp.fe.mappers;

import com.ndl.erp.domain.Invoice;
import com.ndl.erp.fe.MensajeHacienda;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;


import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MensajeHaciendaMapper implements ComprobanteElectronicoMapper<Invoice, MensajeHacienda> {

    @Override
    public MensajeHacienda mapFacturaElectronica(Invoice invoice) throws DatatypeConfigurationException {
        MensajeHacienda ret = new MensajeHacienda();
        ret.setClave(this.getClave());

        ret.setNombreEmisor(this.getNombreEmisor());
        ret.setTipoIdentificacionEmisor(this.getTipoIdentificacionEmisor());
        ret.setNumeroCedulaEmisor(this.getNumeroCedulaEmisor());

        ret.setNombreReceptor(this.getNombreReceptor());
        ret.setTipoIdentificacionReceptor(this.getTipoIdentificacionReceptor());
        ret.setNumeroCedulaReceptor(this.getNumeroCedulaReceptor());

        ret.setMensaje(this.getMensaje());
        ret.setDetalleMensaje(this.getDetalleMensaje());

        ret.setMontoTotalImpuesto(this.getMontoTotalImpuesto());
        ret.setTotalFactura(this.getTotalFactura());

        return ret;

    }


    private String getClave() {
        return "";
    }

    private String getNombreEmisor() {
        return "";
    }

    private String getTipoIdentificacionEmisor() {
        return "";
    }

    private String getNumeroCedulaEmisor() {
        return "";
    }
    private String getNombreReceptor() {
        return "";
    }
    private String getTipoIdentificacionReceptor() {
        return "";
    }
    private String getNumeroCedulaReceptor() {
        return "";
    }

    private BigInteger getMensaje() {
        return new BigInteger("0");
    }

    private String getDetalleMensaje() {
        return "";
    }

    private BigDecimal getMontoTotalImpuesto() {
        return new BigDecimal(0l);
    }

    private BigDecimal getTotalFactura(){
        return new BigDecimal(0l);
    }
}
