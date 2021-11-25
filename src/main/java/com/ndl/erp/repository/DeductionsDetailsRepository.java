package com.ndl.erp.repository;

import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.DeductionsDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeductionsDetailsRepository extends JpaRepository<DeductionsDetails, Integer> {


    @Query(value = "select c from DeductionsDetails c  where c.collaborator.id=?1")
    List<DeductionsDetails> getDeductionsByCollaborator(Integer id);


}
