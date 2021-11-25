package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.DeductionsDetails;
import com.ndl.erp.dto.DeductionDTO;
import com.ndl.erp.dto.DeductionsDTO;
import com.ndl.erp.repository.CollaboratorRepository;
import com.ndl.erp.repository.DeductionRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private UserServiceImpl userDetailsService;


    public DeductionDTO     getDeduction(Integer id) {
        DeductionDTO d = this.getDeduction();

        if (id==null) {
            Deductions d1 = new Deductions();
            d1.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<Deductions> c = deductionRepository.findById(id);
        d.setCurrent(c.get());

        checkAditionalInactiveCollaborator(d);
        return d;
    }

    private void checkAditionalInactiveCollaborator(DeductionDTO d) {
        if (d!=null && d.getColaborators()!=null) {
            for(DeductionsDetails c: d.getCurrent().getDetails()) {
                if (!c.getCollaborator().getStatus().equals("Activo")) {
                    d.getColaborators().add(c.getCollaborator());
                }
            }
        }
    }

    public Deductions getDeductionsById(Integer id) {

        Optional<Deductions> c = deductionRepository.findById(id);

        return c.get();
    }

    public DeductionsDTO getDeductions(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        DeductionsDTO d = new DeductionsDTO();

        d.setDeductionsPage(this.deductionRepository.getFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.deductionRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public DeductionDTO getDeduction() {


        List<String> tipos = new ArrayList<>();
        tipos.add("SIMPLE");
        tipos.add("CCSS");


        DeductionDTO d = new DeductionDTO();
        d.setColaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));
//        d.setColaborators(this.collaboratorRepository.findAll());

        d.setTypes(tipos);

        return d;
    }

    public Deductions save(Deductions c) {

        if (c.getId()==null) {
            c.setCreateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        }

        c.setUpdateDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        return this.deductionRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
