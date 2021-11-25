package com.ndl.erp.repository;

import com.ndl.erp.domain.CotizacionHistoricoVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CotizacionHistoricoVersionRepository extends JpaRepository<CotizacionHistoricoVersion, Integer> {


    CotizacionHistoricoVersion findCotizacionHistoricoVersionById(Integer id);

}