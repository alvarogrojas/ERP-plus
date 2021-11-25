package com.ndl.erp.repository;

import com.ndl.erp.domain.DescuentoFamiliaCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface DescuentoFamiliaClienteRepository extends JpaRepository<DescuentoFamiliaCliente, Integer> {


    @Query(value = "select dfc from DescuentoFamiliaCliente dfc where dfc.descripcion like %?1% ")
    List<DescuentoFamiliaCliente> findUsingFilter(String filter);

    @Query(value = "select dfc from DescuentoFamiliaCliente dfc where dfc.descripcion like %?1% ")
    Page<DescuentoFamiliaCliente> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(dfc.id) from DescuentoFamiliaCliente dfc where dfc.descripcion like %?1% ")
    public Integer countAllByFilter(String filter);



}
