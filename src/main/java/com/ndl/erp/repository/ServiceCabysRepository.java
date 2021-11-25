package com.ndl.erp.repository;


import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ServiceCabys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceCabysRepository extends JpaRepository<ServiceCabys, Integer> {


    @Query(value = "select c from ServiceCabys c where c.cabys.id=?1")
    ServiceCabys getByCabysId(Integer cabysId);

    @Query(value = "select c from ServiceCabys c where c.cabys.id=?1 and c.codigoIngpro=?2 ")
    ServiceCabys getByCabysIdAndCodigoIngpro(Integer cabysId, String codigoIngpro);


    @Query(value = "select c from ServiceCabys c where c.descripcion like %?1% or c.codigoCabys like %?1% or c.codigoIngpro like %?1%")
    List<ServiceCabys> getByFilter(String filter);

    @Query(value = "select c from ServiceCabys c where c.id=?1")
    ServiceCabys getById(Integer id);

    @Query(value = "select c from ServiceCabys c where c.id in (?1)")
    public List<ServiceCabys> findByIdIn(List<Integer> ids);

    @Query(value = "select c from ServiceCabys c where c.codigoIngpro=?1")
    List<ServiceCabys> getServiceByCodigo(String codigo);
}
