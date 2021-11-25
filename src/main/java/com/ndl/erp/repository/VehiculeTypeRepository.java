package com.ndl.erp.repository;


import com.ndl.erp.domain.Fuel;
import com.ndl.erp.domain.VehiculeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface VehiculeTypeRepository extends JpaRepository<VehiculeType, Integer> {




}
