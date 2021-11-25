package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.DeductionsRefunds;
import com.ndl.erp.domain.DeductionsRefundsDetails;
import com.ndl.erp.dto.DeductionRefundDTO;
import com.ndl.erp.dto.DeductionsRefundsDTO;
import com.ndl.erp.dto.DeductionsRefundsDTO;
import com.ndl.erp.repository.CollaboratorRepository;
import com.ndl.erp.repository.DeductionRefundRepository;
import com.ndl.erp.repository.DeductionsRefundsDetailsRepository;
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
public class DeductionsRefundsService {

    @Autowired
    private DeductionRefundRepository deductionRepository;

    @Autowired
    private DeductionsRefundsDetailsRepository detailsRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private UserServiceImpl userDetailsService;


    public DeductionRefundDTO getDeductionsRefunds(Integer id) {
        DeductionRefundDTO d = this.getDeductionsRefunds();

        if (id==null) {
            DeductionsRefunds d1 = new DeductionsRefunds();
            d1.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<DeductionsRefunds> c = deductionRepository.findById(id);
        d.setCurrent(c.get());
        return d;
    }

    public DeductionsRefundsDTO getDeductionsRefunds(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        DeductionsRefundsDTO d = new DeductionsRefundsDTO();

        d.setDeductionsRefundsPage(this.deductionRepository.getFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.deductionRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public DeductionRefundDTO getDeductionsRefunds() {


        List<String> tipos = new ArrayList<>();
        tipos.add("DEVOLUCION");
        tipos.add("REINTEGRO");


        DeductionRefundDTO d = new DeductionRefundDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));

        d.setTypes(tipos);

        return d;
    }

    public DeductionsRefunds save(DeductionsRefunds c) {

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

    public List<DeductionsRefunds> getDeductionsRefundsByCollaboratorIdList(Integer id) {
        return this.deductionRepository.getDeductionsRefundsByCollaboratorId(id);
    }

    public DeductionsRefundsDetails getDeductionDetailByIdDeductionAndActive(Integer deductionId) {
        List<DeductionsRefundsDetails>  d = this.detailsRepository.getActiveByParentId(deductionId);
        DeductionsRefundsDetails result = null;
        if (d!=null && d.size()>0){
            result = d.get(0);
        }
        return result;
    }
}
