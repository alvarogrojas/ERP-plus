package com.ndl.erp.repository;

import com.ndl.erp.domain.Permission;
import com.ndl.erp.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PermissionRepository extends JpaRepository<Permission, Integer> {


    @Query(value = "select c from Permission c where c.action like %?1% or c.resourceCode like %?1%")
    Page<Permission> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Permission c where c.action like %?1% or c.resourceCode like %?1%")
    public Integer countAllByFilter(String filter);


}
