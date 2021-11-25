package com.ndl.erp.fe.core;

import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;

public interface BillProcessEmiter {

    void emitirFactura(FacturaElectronica fe);

    void emitirNotaCredito(NotaCreditoElectronica nce);

    void emitirConfirmacionRechazo(MensajeReceptor mensajeReceptor);
}
