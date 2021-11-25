package com.ndl.erp.repository;


import com.ndl.erp.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface TipoDocumentoRepository  extends JpaRepository<TipoDocumento, Integer> {

    @Query(value = "select td from TipoDocumento td where td.tipo = ?1")
    TipoDocumento findbyTipo(String tipo);


}
