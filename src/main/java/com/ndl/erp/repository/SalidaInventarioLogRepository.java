package com.ndl.erp.repository;

import com.ndl.erp.domain.SalidaInventarioLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
@Component
public interface SalidaInventarioLogRepository extends JpaRepository<SalidaInventarioLog, Integer> {

}
