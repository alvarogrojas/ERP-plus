package com.ndl.erp.repository;

import com.ndl.erp.domain.TaxesIva;
import com.ndl.erp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TaxesIvaRepository extends JpaRepository<TaxesIva, Long> {

	List<TaxesIva> findAll();

	@Query(value = "select d from TaxesIva d order by id desc")
	List<TaxesIva> getTaxes();
}
