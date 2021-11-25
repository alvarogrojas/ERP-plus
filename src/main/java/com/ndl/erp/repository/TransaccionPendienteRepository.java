package com.ndl.erp.repository;

import com.ndl.erp.domain.TransaccionPendiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransaccionPendienteRepository extends JpaRepository<TransaccionPendiente, Integer> {


    @Query(value = "Select t from TransaccionPendiente t where t.status =  'ESPERA' " )
    List<TransaccionPendiente> getAllTransaccionPendienteByEstado();

}
