package com.ndl.erp.repository;


import com.ndl.erp.domain.BodegaSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BodegaSalidaRepository extends JpaRepository<BodegaSalida, Integer> {



    @Query(value = "select c from BodegaSalida c where c.invoiceId=?1"
    )
    List<BodegaSalida> getByInvoiceId(Integer id);


    @Query(value = "select c from BodegaSalida c where c.invoiceId=?1 and c.invoiceDetailId=?2 and status<>'Borrado'")
    BodegaSalida getBodegaSalida(Integer invoiceId, Integer invoiceDetailId);


}
