package com.ndl.erp.repository;

import com.ndl.erp.domain.ConfirmaRechazaDocumento;
import com.ndl.erp.domain.ErrorEnvio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ConfirmaRechazaDocumentoRepository extends JpaRepository<ConfirmaRechazaDocumento, Integer> {


    ConfirmaRechazaDocumento findByClave(String clave);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.emisor like %?1% and c.fechaEmision>=?2 and c.fechaEmision<=?3 ")
    Page<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoEmisor(String emisor, Date fechaInicio, Date fechaFinal, Pageable pageable);

    @Query(value = "select  count(c.id) from ConfirmaRechazaDocumento c where c.emisor like %?1% and c.fechaEmision>=?2 and c.fechaEmision<=?3 ")
    Integer countConfirmaRechazaDocumentoEmisor(String emisor, Date fechaInicio, Date fechaFinal);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.estado like %?1% and c.fechaEmision>=?2 and c.fechaEmision<=?3 ")
    Page<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoEstado(String estado, Date fechaInicio, Date fechaFinal, Pageable pageable);

    @Query(value = "select  count(c.id) from ConfirmaRechazaDocumento c where c.estado like %?1% and c.fechaEmision>=?2 and c.fechaEmision<=?3 ")
    Integer countConfirmaRechazaDocumentoEstado(String estado, Date fechaInicio, Date fechaFinal);


    @Query(value = "select c from ConfirmaRechazaDocumento c where c.emisor like %?1% and c.estado like %?2% and c.fechaEmision>=?3 and c.fechaEmision<=?4 ")
    Page<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoEmisorAndEstado(String emisor, String estado, Date fechaInicio, Date fechaFinal, Pageable pageable);

    @Query(value = "select  count(c.id) from ConfirmaRechazaDocumento c where c.emisor like %?1% and c.estado like %?2% and c.fechaEmision>=?3 and c.fechaEmision<=?4 ")
    Integer countConfirmaRechazaDocumentooEmisorAndEstado(String emisor, String estado, Date fechaInicio, Date fechaFinal);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.fechaEmision>=?1 and c.fechaEmision<=?2 ")
    Page<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoFechas(Date fechaInicio, Date fechaFinal, Pageable pageable);

    @Query(value = "select  count(c.id) from ConfirmaRechazaDocumento c where c.fechaEmision>=?1 and c.fechaEmision<=?2  ")
    Integer countConfirmaRechazaDocumentooFechas(Date fechaInicio, Date fechaFinal);


}
