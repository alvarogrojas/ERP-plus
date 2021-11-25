package com.ndl.erp.repository;



import com.ndl.erp.domain.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface FuelRepository extends JpaRepository<Fuel, Integer> {




}
