package com.ndl.erp.repository;

import com.ndl.erp.domain.FixedCost;
import com.ndl.erp.domain.Various;
import com.ndl.erp.dto.ScheduleVariosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface VariousRepository extends JpaRepository<Various, Integer> {


    @Query(value = "select c from Various c where c.name like %?1% " +
            "or c.status like %?1% or c.mount like %?1%  order by c.name desc "
    )
    List<Various> getByFilter(String filter);


    @Query(value = "select " +
            "new com.ndl.erp.dto.ScheduleVariosDTO(i)" +
            "FROM Various i WHERE  " +
            " i.status='Activo' "
    )
    List<ScheduleVariosDTO> getAllVariosByActive();
}
