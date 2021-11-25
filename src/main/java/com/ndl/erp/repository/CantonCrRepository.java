package com.ndl.erp.repository;

import com.ndl.erp.domain.CantonCr;
import com.ndl.erp.domain.ProvinciaCr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CantonCrRepository extends CrudRepository<CantonCr, Long> {

    List<CantonCr> findAll();

    CantonCr findById(Integer id);

    public List<CantonCr> findByProvinciaCrOrderByNombreCanton(ProvinciaCr p);

}
