package com.ndl.erp.repository;


import com.ndl.erp.domain.Canton;
import com.ndl.erp.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CantonRepository extends JpaRepository<Canton, Integer> {


	@Query(value = "select c from Canton c where c.province.id=?1")
    List<Canton> findByProvince(Integer idProvince);

}
