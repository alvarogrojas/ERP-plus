package com.ndl.erp.repository;

import com.ndl.erp.domain.ContactClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContactClientRepository extends JpaRepository<ContactClient, Integer> {


	@Query(value = "select c from ContactClient c where c.name like %?1% ")
    List<ContactClient> findUsingFilter(String filter);

    @Query(value = "select c from ContactClient c where c.name like %?1% and c.client.clientId=?2 and c.status<>'Borrado'")
    Page<ContactClient> findUsingFilterPageable(String filter, Integer clientId, Pageable pageable);

    @Query(value = "select c from ContactClient c where c.client.clientId=?1 and  c.status<>'Borrado'")
    List<ContactClient> findByClient(Integer clientId);

    @Query(value = "select count(c.id) from ContactClient c where c.name like %?1% and c.client.clientId=?2")
    public Integer countAllByFilter(String filter, Integer clientId);
}
