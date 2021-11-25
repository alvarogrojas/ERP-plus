package com.ndl.erp.repository;


import com.ndl.erp.domain.GeneralParameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GeneralParameterRepository extends JpaRepository<GeneralParameter, Long> {

	GeneralParameter findByCode(String code);

	@Query(value = "select c from GeneralParameter c where c.code = ?1")
	List<GeneralParameter> findByCodes(String code);

	@Query(value = "select c from GeneralParameter c where c.code = ?1 and name = ?2")
	GeneralParameter findByCodeAndName(String code, String name);

	@Query(value = "select c from GeneralParameter c where c.code = ?1")
	List<GeneralParameter> getByCode(String code);

	GeneralParameter findById(Integer id);

	List<GeneralParameter> findAll();
}
