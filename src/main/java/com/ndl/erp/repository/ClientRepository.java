package com.ndl.erp.repository;

import com.ndl.erp.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientRepository extends JpaRepository<Client, Integer> {


	@Query(value = "select c from Client c where c.name like %?1% ")
    List<Client> findUsingFilter(String filter);

    @Query(value = "select c from Client c where c.name like %?1% ")
    Page<Client> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Client c where c.name like %?1% ")
    public Integer countAllByFilter(String filter);

    public Client findByClientId(Integer clientId);

    @Query(value = "select c from Client c where c.status='Activo' order by name asc")
    List<Client> findClientsActive();
}
