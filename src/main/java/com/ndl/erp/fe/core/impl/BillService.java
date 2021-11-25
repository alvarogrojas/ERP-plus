package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;

/*
Main entry pointo to send electronic documents to Hacienda
 */
public interface BillService {

    /**
     * Send electronic documento to hacienda
     *
     * @param facturaElectronica Factura electronic to be sent
     *
     * @return
     */
    String emitFactura(FacturaElectronica facturaElectronica);

    /**
     * Send nota de credito to hacienda
     *
     * @param nc Nota de credito a ser enviada
     * @return
     */
    String emitNotaCredito(NotaCreditoElectronica nc);

}
