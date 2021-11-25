package com.ndl.erp.services;


import com.ndl.erp.domain.Taxes;
import com.ndl.erp.dto.TaxesDTO;
import com.ndl.erp.dto.TaxesListDTO;

import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TaxesService {
	
	@Autowired
	private TaxesRepository taxesRepository;

	@Autowired
    private UserServiceImpl userDetailsService;



    private final transient Logger log = LoggerFactory.getLogger(TaxesService.class);


	public Taxes save(Taxes c) {
        if (log.isDebugEnabled()) {
            log.debug("Inserting new Collaborator  " + c.getName());
        }
        if (c.getId()==null) {
            c.setCreateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        }
        c.setUpdateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));


        return this.taxesRepository.save(c);
	}



    public TaxesListDTO getTaxesList(String filter, Integer pageNumber,
                                         Integer pageSize, String sortDirection,
                                         String sortField) {

        TaxesListDTO d = new TaxesListDTO();

        d.setPage(
           this.taxesRepository.findUsingFilterPageable(filter,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.taxesRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;
    }


    public List<Taxes> getTaxesList() {

        return this.taxesRepository.findAll();
    }


    public TaxesDTO getTaxes(Integer id) {

        TaxesDTO d = new TaxesDTO();
        if (id==null) {

            return d;
        }
        Optional<Taxes> c = taxesRepository.findById(id);


//        Optional<Collaborator> c = collaboratorRepository.findById(id);

        d.setCurrent(c.get());
        return d;
    }




    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }


}
