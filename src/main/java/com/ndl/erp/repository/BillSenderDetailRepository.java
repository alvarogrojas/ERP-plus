package com.ndl.erp.repository;

import com.ndl.erp.domain.BillSenderDetail;
import com.ndl.erp.domain.ConfirmaRechazaDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillSenderDetailRepository extends JpaRepository<BillSenderDetail, Integer> {

    List<BillSenderDetail> findByClave(String clave);




}
