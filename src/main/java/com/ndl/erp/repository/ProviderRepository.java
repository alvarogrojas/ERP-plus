package com.ndl.erp.repository;

import com.ndl.erp.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProviderRepository extends JpaRepository<Provider, Integer> {


    @Query(value = "select c from Provider c where c.name like %?1% ")
    Page<Provider> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Client c where c.name like %?1% ")
    public Integer countAllByFilter(String filter);

    @Query(value = "select c from Provider c where c.status=?1 order by c.name asc")
    public List<Provider> findByStatus(String status);
}
