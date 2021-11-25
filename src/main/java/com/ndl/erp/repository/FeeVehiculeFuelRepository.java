package com.ndl.erp.repository;

import com.ndl.erp.domain.FeeVehiculeFuel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface FeeVehiculeFuelRepository extends JpaRepository<FeeVehiculeFuel, Integer> {

    @Query(value = "select vff from FeeVehiculeFuel vff where vff.vehicule.id=?1 and vff.fuel.id=?2 order by old desc")
    List<FeeVehiculeFuel> getFeeVehiculeFuel(Integer vehiculeId, Integer fuelId);

    @Query(value = "select vff from FeeVehiculeFuel vff where vff.vehicule.id=?1 and vff.fuel.id=?2 and vff.old=?3")
    List<FeeVehiculeFuel> getFeeVehiculeFuel(Integer vehiculeId, Integer fuelId, Integer old);

    @Query(value = "select c from FeeVehiculeFuel c where c.vehicule.name like %?1%  or c.fuel.name like %?1% ")
    Page<FeeVehiculeFuel> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from FeeVehiculeFuel c where c.vehicule.name like %?1%  or c.fuel.name like %?1% ")
    public Integer countAllByFilter(String filter);

}
