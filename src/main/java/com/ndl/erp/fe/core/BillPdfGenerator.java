package com.ndl.erp.fe.core;

import com.ndl.erp.fe.v43.FacturaElectronica;


public interface BillPdfGenerator {

    void generatePdf(FacturaElectronica fe);

}
