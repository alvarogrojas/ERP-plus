package com.ndl.erp.repository;

import com.ndl.erp.domain.DocumentoConsecutivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface DocumentoConsecutivoRepository extends JpaRepository<DocumentoConsecutivo, Integer> {

    @Query(value= " select dc from DocumentoConsecutivo dc " +
                  " where dc.empresa = ?1 "  +
                  " and dc.tipoDocumento = ?2 " +
                  " and dc.docOrigenId = ?3")
    DocumentoConsecutivo findByEmpresaAndTipoDocumento(String empresa, String tipoDocumento, Integer docOrigenId);

}

