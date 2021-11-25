package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.core.BillProcessEmiter;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import org.springframework.beans.factory.annotation.Autowired;

public class BillServiceImpl implements BillService {


    @Autowired
    private BillProcessEmiter billProcessEmiter;
    /**
     *
     * @param facturaElectronica Factura electronic to be sent
     *
     * @return
     */
    @Override
    public String emitFactura(FacturaElectronica facturaElectronica) {
        billProcessEmiter.emitirFactura(facturaElectronica);
        return "OK";
    }

    /**
     *
     * @param notaCreditoElectronica
     * @return
     */
    @Override
    public String emitNotaCredito(NotaCreditoElectronica notaCreditoElectronica) {
        return "OK";
    }
}
