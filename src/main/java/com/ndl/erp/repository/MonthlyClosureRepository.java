package com.ndl.erp.repository;


import com.ndl.erp.domain.MonthlyClosure;

import com.ndl.erp.dto.MonthlyClosureCustomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public interface MonthlyClosureRepository extends JpaRepository<MonthlyClosure, Integer> {

    @Query(value = "select new com.ndl.erp.dto.MonthlyClosureCustomDTO(c.id, c.name, c.start, c.end, c.status) from MonthlyClosure c "
    )
    Page<MonthlyClosureCustomDTO> getPageable(
            Pageable pageable);



    @Query(value = "select count(c.id) from MonthlyClosure c " )
    Integer countAll();


    @Query(value = "select c from MonthlyClosure c where c.start>=?1 and c.end<=?2 " +
            "order by c.start desc")
    Page<MonthlyClosure> getFilterPageableByDates(
            Date start, Date end,
            Pageable pageable);


    @Query(value = "select c from MonthlyClosure c where c.start>=?1 and c.end<=?2 ")
    List<MonthlyClosure> getByDates(
            Date start, Date end);


    @Query(value = "select count(c.id) from MonthlyClosure c where  c.start>=?1 and c.end<=?2 " )
    Integer countAllByDates(
            Date start, Date end);


}