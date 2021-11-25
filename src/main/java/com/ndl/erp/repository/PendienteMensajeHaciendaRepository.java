package com.ndl.erp.repository;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.PendienteMensajeHacienda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PendienteMensajeHaciendaRepository extends JpaRepository<PendienteMensajeHacienda, Integer> {


    PendienteMensajeHacienda getPendienteMensajeHaciendaByIdAndTipoComprobante(Integer id, String tipoCR);

    List<PendienteMensajeHacienda> findByEstado(String espera);


}
