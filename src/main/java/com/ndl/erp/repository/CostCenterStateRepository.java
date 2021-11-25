package com.ndl.erp.repository;

import com.ndl.erp.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CostCenterStateRepository extends JpaRepository<Client, Integer> {


    @Query(value = "select c.state from CostsCenterState c where c.active=1")
    List<String> findAllStates();
}
