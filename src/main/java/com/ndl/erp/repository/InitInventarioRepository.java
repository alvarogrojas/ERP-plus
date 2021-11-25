package com.ndl.erp.repository;

import com.ndl.erp.domain.InitInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InitInventarioRepository extends JpaRepository <InitInventario, Integer>{

    @Query(value= "select i  from  InitInventario i ")
    List<InitInventario> findAllInitInventario();

}
