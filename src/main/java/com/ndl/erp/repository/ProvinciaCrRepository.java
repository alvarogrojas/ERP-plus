package com.ndl.erp.repository;

import com.ndl.erp.domain.ProvinciaCr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface  ProvinciaCrRepository extends CrudRepository<ProvinciaCr, Long> {

    List<ProvinciaCr> findAll();

    ProvinciaCr findById(Integer id);
}