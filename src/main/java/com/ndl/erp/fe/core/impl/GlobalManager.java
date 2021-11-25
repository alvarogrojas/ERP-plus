package com.ndl.erp.fe.core.impl;


import com.ndl.erp.repository.FacturaConsecutivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

/**
 * Created by alvaro on 11/2/17.
 */
@Service
@Transactional
public class GlobalManager {

    private static Integer currentReciboId = 0;

    private static Integer currentFacturaId = 0;

    private static Boolean isFromHuerfanas = false;


    @Autowired
    private FacturaConsecutivoRepository facturaConsecutivoRepository;

    public synchronized Integer getReciboNext() {
        currentReciboId = currentReciboId + 1;
        return currentReciboId;
    }


//    public synchronized Integer getNotaCreditoNext(Integer tenantId) {
//
//        FacturaConsecutive fc  = facturaConsecutivoRepository.findByTipoDocumentoAndTenantId("NOTACREDITO",tenantId);
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.updateFacturaConsecutive(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }


//    public synchronized Integer getFacturaElectronicaNext(Integer tenantId) {
//        FacturaConsecutive fc  = facturaConsecutivoRepository.findByTipoDocumentoAndTenantId("FE",tenantId);
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.updateFacturaConsecutive(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }
//
//    public synchronized Integer getNotaCreditoElectronicaNext(Integer tenantId) {
//        FacturaConsecutive fc  = facturaConsecutivoRepository.findByTipoDocumentoAndTenantId("NCE",tenantId);
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.updateFacturaConsecutive(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }
//
//    public synchronized Integer getConfirmacionNext(Integer tenantId) {
//        FacturaConsecutive fc  = facturaConsecutivoRepository.findByTipoDocumentoAndTenantId("MRC",tenantId);
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.updateFacturaConsecutive(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }
//
//    public synchronized Integer getConfirmacionRechazoNext(Integer tenantId) {
//        FacturaConsecutive fc  = facturaConsecutivoRepository.findByTipoDocumentoAndTenantId("MRR",tenantId);
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.updateFacturaConsecutive(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }



    protected static void setGlobalFactura(Integer currentFacturaId) {
        GlobalManager.currentFacturaId = currentFacturaId;
    }

    protected static void setGlobalReciboConsecutivo(Integer currentReciboId) {
        GlobalManager.currentReciboId = currentReciboId;
    }
}
