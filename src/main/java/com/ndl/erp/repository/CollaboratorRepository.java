package com.ndl.erp.repository;


import com.ndl.erp.domain.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {


	@Query(value = "select c from Collaborator c where c.name like %?1% order by c.name, c.lastName desc")
    List<Collaborator> findUsingFilter(String filter);

    @Query(value = "select c from Collaborator c where c.status=?1 order by c.name, c.lastName desc")
    List<Collaborator> findByStatus(String status);

    @Query(value = "select c from Collaborator c where (c.status='Inactivo' or c.status='Activo') and (c.name like %?1% or c.lastName like %?1% or c.phone like %?1% or c.identificationCard like %?1% " +
            "or c.email like %?1% or c.status like %?1%) ")
    Page<Collaborator> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Collaborator c where c.name like %?1% or c.lastName like %?1% or c.phone like %?1% or c.identificationCard like %?1% " +
            "or c.email like %?1% or c.status like %?1% ")
    Integer countAllByFilter(String filter);

}
