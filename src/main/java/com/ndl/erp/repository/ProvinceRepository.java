package com.ndl.erp.repository;



import com.ndl.erp.domain.Province;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProvinceRepository extends JpaRepository<Province, Integer> {


//	@Query(value = "select c from Collaborator c where c.name like %?1% ")
//    List<Collaborator> findUsingFilter(String filter);
//
//    @Query(value = "select c from Collaborator c where c.name like %?1% or c.last_name like %?1% or c.phone like %?1% or c.identificationCard like %?1% " +
//            "or c.email like %?1% or c.status like %?1% ")
//    Page<Collaborator> findUsingFilterPageable(String filter, Pageable pageable);
//
//    @Query(value = "select count(c.id) from Collaborator c where c.name like %?1% or c.last_name like %?1% or c.phone like %?1% or c.identificationCard like %?1% " +
//            "or c.email like %?1% or c.status like %?1% ")
//    public Integer countAllByFilter(String filter);


}
