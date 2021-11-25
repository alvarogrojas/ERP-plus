package com.ndl.erp.repository;

import com.ndl.erp.domain.CantonCr;
import com.ndl.erp.domain.DistritoCr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistritoCrRepository extends CrudRepository<DistritoCr, Long> {

    List<DistritoCr> findAll();

    DistritoCr findById(Integer id);

    List<DistritoCr> findByCantonCrOrderByNombreDistrito(CantonCr c);

}
