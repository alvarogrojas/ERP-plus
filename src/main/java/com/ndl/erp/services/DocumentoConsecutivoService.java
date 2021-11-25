package com.ndl.erp.services;

import com.ndl.erp.domain.DocumentoConsecutivo;
import com.ndl.erp.repository.DocumentoConsecutivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DocumentoConsecutivoService {

    @Autowired
    DocumentoConsecutivoRepository documentoConsecutivoRepository;

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Integer getCotizacionHistoricoVersionNext(String empresa, String tipoDocumento, Integer docOrigenId ) {

        Integer nextId;

        //validar si existe un consecutivo para el documento
        DocumentoConsecutivo consecutivo = this.documentoConsecutivoRepository.findByEmpresaAndTipoDocumento(empresa, tipoDocumento, docOrigenId);

        //Si no existe hay que crearlo
        if (consecutivo == null){
            consecutivo = new DocumentoConsecutivo();
            consecutivo.setEmpresa(empresa);
            consecutivo.setTipoDocumento(tipoDocumento);
            consecutivo.setDocOrigenId(docOrigenId);
            consecutivo.setActualId(1);
            nextId = consecutivo.getActualId();
            consecutivo.setActualId(nextId+1);
            this.documentoConsecutivoRepository.save(consecutivo);
         } else {

            nextId = consecutivo.getActualId();
            consecutivo.setActualId(consecutivo.getActualId() + 1);
            this.documentoConsecutivoRepository.save(consecutivo);
        }
        return nextId;
    }

}
