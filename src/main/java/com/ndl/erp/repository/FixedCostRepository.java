package com.ndl.erp.repository;

import com.ndl.erp.domain.FixedCost;
import com.ndl.erp.dto.ScheduleFixedCostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface FixedCostRepository extends JpaRepository<FixedCost, Integer> {

    @Query(value = "select c from FixedCost c where c.name like %?1% " +
            "or c.status like %?1% or c.mount like %?1%  order by c.name desc "
    )
    List<FixedCost> getByFilter(String filter);


    @Query(value = "select " +
            "new com.ndl.erp.dto.ScheduleFixedCostDTO(i)" +
            "FROM FixedCost i WHERE  " +
            " i.status='Activo' "
    )
    List<ScheduleFixedCostDTO> getAllFixedCostByActive();
}
