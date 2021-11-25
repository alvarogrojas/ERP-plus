package com.ndl.erp.repository;

import com.ndl.erp.domain.FacturaConsecutivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FacturaConsecutivoRepository extends JpaRepository<FacturaConsecutivo, Integer> {

    FacturaConsecutivo findByDocumento(String documento);

}
