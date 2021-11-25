package com.ndl.erp.repository;



import com.ndl.erp.domain.ProjectionsMaster;
import com.ndl.erp.dto.ScheduleProjectionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProjectionsMasterRepository extends JpaRepository<ProjectionsMaster, Integer> {

    @Query(value = "select " +
            "new com.ndl.erp.dto.ScheduleProjectionsDTO(i)" +
            "FROM ProjectionsMaster i, PurchaseOrderClient poc  WHERE poc.id=i.poc.id and i.date >= ?1 and i.date <= ?2 and (i.status='Pendiente' or i.status ='Vencida')"
    )
    List<ScheduleProjectionsDTO> getAllByDate(java.sql.Date start, java.sql.Date end);


}
