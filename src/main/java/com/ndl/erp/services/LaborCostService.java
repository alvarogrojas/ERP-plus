package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.dto.*;

import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class LaborCostService {

    @Autowired
    private LaborCostRepository laborCostRepository;

    @Autowired
    private PayRollCollaboratorDetailRepository payRollCollaboratorDetailRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CentroCostosRepository costCenterRepository;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private CurrencyRepository currencyRepository;


    public LaborCostDTO getLaborCost(Integer id) {
        LaborCostDTO d = this.getLaborCost();

        if (id==null) {
            LaborCost d1 = new LaborCost();
            d1.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<LaborCost> c = laborCostRepository.findById(id);
        d.setCurrent(c.get());
        return d;
    }

    public LaborCostsDTO getLaborCost(Integer collaboratorId, Integer costCenterId,
                                      Date startDate, Date endDate, Boolean isLoadedCombos,
                                      Integer pageNumber,
                                      Integer pageSize, String sortDirection,
                                      String sortField) {

        LaborCostsDTO d = new LaborCostsDTO();


        if (collaboratorId!=0 && costCenterId!=0) {
            d.setPage(this.laborCostRepository.getPageableByCostCenterIdAndCollaboratorAndLaborDate(
                    costCenterId, collaboratorId, startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.laborCostRepository.countAllByCostCenterIdAndCollaboratorAndLaborDates(costCenterId, collaboratorId, startDate, endDate));
            if (d.getTotal()>0)
                d.initHours(this.laborCostRepository.sumHoursByCostCenterIdAndCollaboratorAndLaborDates(costCenterId, collaboratorId, startDate, endDate));
        } else if (collaboratorId==0 && costCenterId==0) {
            d.setPage(this.laborCostRepository.getPageableByDate(
                    startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.laborCostRepository.countAllByDates(startDate, endDate));
            if (d.getTotal()>0)
                d.initHours(this.laborCostRepository.sumHoursAllByDates(startDate, endDate));
        } else if (collaboratorId==0) {
            d.setPage(this.laborCostRepository.getFilterPageableByCostCenterAndDates(
                    costCenterId, startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.laborCostRepository.countAllByCostCenterAndDates(costCenterId, startDate, endDate));
            if (d.getTotal()>0)
                d.initHours(this.laborCostRepository.sumHoursByCostCenterAndDates(costCenterId, startDate, endDate));

        } else if (costCenterId==0) {
            d.setPage(this.laborCostRepository.getFilterPageableByCollaboratorId(
                    collaboratorId, startDate, endDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.laborCostRepository.countAllByCCollaboratorrAndDates(collaboratorId, startDate, endDate));
            if (d.getTotal()>0)
                d.initHours(this.laborCostRepository.sumHoursByCollaboratorAndDates(collaboratorId, startDate, endDate));
        }

        if (!isLoadedCombos) {

           // d.setCostCenters(this.costCenterRepository.getByState(CostCenterConstants.ACTIVE));


            d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));


        }
        CostCenter c = createDefaultCC();
        List<CostCenter> l = new ArrayList<>();

        if (costCenterId!=0) {

            Optional<CostCenter> oc = this.costCenterRepository.findById(costCenterId);
            if (oc!=null) {
                l.add(oc.get());
            }

        }
        l.add(c);
        d.setCostCenters(l);


        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    private CostCenter createDefaultCC() {
        CostCenter costCenter = new CostCenter();
        costCenter.setCode("        ");
        costCenter.setName("No Centros Costos Seleccionado");
        costCenter.setId(0);
        costCenter.setDescription("");
        return costCenter;
    }

    public LaborCostDTO getLaborCost() {
        LaborCostDTO d = new LaborCostDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));
        d.setCostCenters(new ArrayList<>());
//        d.setCostCenters(this.costCenterRepository.getByStates(CostCenterConstants.ACTIVE, CostCenterConstants.QUOTE));

        Optional<Currency> co = currencyRepository.findById(1);
        if (co!=null) {
            d.setCurrency(co.get());
        }
        return d;
    }

    public LaborCost save(LaborCost c) {

        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        }

        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        return this.laborCostRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public List<LaborCostDetail> getLaborCostDetailRangeDate(Date start,Date end) {
        return this.laborCostRepository.getLaborCostsByDates(start, end);
    }

    public List<LaborCostDetail> getLaborCostDetailOrderByCollaborator(Date start,Date end) {
        return this.laborCostRepository.getLaborCostsByStarAndEndOrderByCollaborator(start, end);
    }

    public LaborCostDetailDTO getLaborCostDetail(Date startDate, Date endDate) {
        LaborCostDetailDTO detailDTO = new LaborCostDetailDTO();
        detailDTO.setDetails(this.laborCostRepository.getLaborCostHhMm(startDate, endDate));

        return detailDTO;
    }

//    public List<BalanceLaborCostDetailDTO> getBalanceLaborCostDetailDTO(List<Integer> ids) {
    public List<BalanceLaborCostDetailDTO> getBalanceLaborCostDetailDTO(List<Integer> ids) {
        return this.payRollCollaboratorDetailRepository.getBalanceLaborCostDetailDTO(ids);
    }

    public Result delete(LaborCost c) {
        Result r = null;

        try {


//            try {
//                MonthlyClosureBillPay mbp = this.monthlyBillPayRepository.getBy(c.getId());
//                if (mbp != null) {
//                    this.monthlyBillPayRepository.delete(mbp);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            this.laborCostRepository.delete(c);

            r = new Result(Result.RESULT_CODE.DELETE, "Se elimino la cuenta por pagar");
        } catch (Exception e) {
            r = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return r;
    }
}
