package com.ndl.erp.fe.helpers;

import javax.xml.datatype.DatatypeConfigurationException;

public interface ComprobanteElectronicoMapper<T1,T2> {

    public static final String FACTURA = "01";
    public T2 mapFacturaElectronica(T1 o) throws DatatypeConfigurationException;
}
