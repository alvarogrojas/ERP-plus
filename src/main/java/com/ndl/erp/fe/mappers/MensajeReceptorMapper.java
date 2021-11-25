package com.ndl.erp.fe.mappers;

import com.ndl.erp.domain.Invoice;
import com.ndl.erp.fe.MensajeReceptor;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;


import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MensajeReceptorMapper implements ComprobanteElectronicoMapper<Invoice, MensajeReceptor> {

    @Override
    public MensajeReceptor mapFacturaElectronica(Invoice invoice) throws DatatypeConfigurationException {
        MensajeReceptor ret = new MensajeReceptor();
        ret.setClave(this.getClave());
        ret.setNumeroCedulaEmisor(this.getNumeroCedulaEmisor());

        ret.setFechaEmisionDoc(null); // Todo:: No tengo una fecha de emision.

        ret.setMensaje(this.getMensaje());

        ret.setDetalleMensaje(this.getDetalleMensaje());
        ret.setMontoTotalImpuesto(this.getMontoTotalImpuesto());
        ret.setTotalFactura(this.getTotalFactura());

        ret.setNumeroCedulaReceptor(this.getNumeroCedulaReceptor());
        ret.setNumeroConsecutivoReceptor(this.getNumeroConsecutivoReceptor());

        return ret;
    }



    private String getClave() {
        return "";
    }

    private String getNumeroCedulaEmisor() {
        return "";
    }
    private String getNumeroConsecutivoReceptor() {
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
