package com.ndl.erp.services;


import com.ndl.erp.domain.FacturaConsecutivo;
import com.ndl.erp.repository.FacturaConsecutivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GlobalDataManager {


    private static Integer currentFacturaId = 0;


    @Autowired
    private FacturaConsecutivoRepository facturaConsecutivoRepository;




    public synchronized Integer getFacturaElectronicaExportacionNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("FEE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getTiqueteElectronicoNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("TQ");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getFacturaElectronicaNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("FE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getNotaCreditoElectronicaNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("NCE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getConfirmacionNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("MCE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();

    }

    public synchronized Integer getConfirmacionRechazoNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("MRE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getConfirmacionLocalNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("MCELocal");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    public synchronized Integer getConfirmacionRechazoLocalNext() {
        FacturaConsecutivo fc = facturaConsecutivoRepository.findByDocumento("MRELocal");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        return fc.getActualId();
    }

    protected static void setGlobalFactura(Integer currentFacturaId) {
        GlobalDataManager.currentFacturaId = currentFacturaId;
    }


}
