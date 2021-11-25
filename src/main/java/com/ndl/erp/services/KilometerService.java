package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.dto.BalanceKmDTO;
import com.ndl.erp.dto.KilometerDTO;
import com.ndl.erp.dto.KilometersDTO;
import com.ndl.erp.dto.Result;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class KilometerService {

    @Autowired
    private KilometerRepository repository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CentroCostosRepository costCenterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private BillPayService billPayService;

    @Autowired
    private FeeVehiculeFuelService feeVehiculeFuelService;


    public KilometerDTO getKilometer(Integer id) {
        KilometerDTO d = this.getKilometer();

        if (id==null) {
            Kilometer d1 = new Kilometer();
            d1.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<Kilometer> c = repository.findById(id);
        d.setCurrent(c.get());
        if (d.getCurrent()!=null) {
            Integer old = Calendar.getInstance().get(Calendar.YEAR) - d.getCurrent().getCollaborator().getVehicleYear();
            d.setFeeVehiculeFuel(this.feeVehiculeFuelService.getFeeVehiculeFuel(
                    d.getCurrent().getCollaborator().getVehiculeType(),
                    d.getCurrent().getCollaborator().getVehicleFuelType(),
                     old
                    ));
        }
        return d;
    }

    public KilometersDTO getKilometer(Integer collaboratorId, Integer costCenterId,
                                      Date startDate, Date endDate,
                                      String consecutive, Integer currencyId, String state,
                                      Boolean isLoadedCombos,
                                      Integer pageNumber,
                                      Integer pageSize, String sortDirection,
                                      String sortField) {

        KilometersDTO d = new KilometersDTO();

//        System.out.println("SORT FIELD " + sortField);
        if (collaboratorId!=0 && costCenterId!=0 && currencyId!=0) { // 1 1 1
            d.setPage(this.repository.getPageableByCostCenterIdAndCollaboratorAndCurrencyAndLaborDate(
                    costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalKilometers(this.repository.getSumKmCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalAmount(this.repository.getSumTotalCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state));


        } else if (collaboratorId==0 && costCenterId==0 && currencyId==0) { // 0 0 0
            d.setPage(this.repository.getPageableByDate(
                    startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDates(startDate, endDate, consecutive, state));
            d.setTotalKilometers(this.repository.getSumKmByDates(startDate, endDate, consecutive, state));
            d.setTotalAmount(this.repository.getSumTotalByDates(startDate, endDate, consecutive, state));
        } else if (collaboratorId==0 && costCenterId!=0 && currencyId!=0) { // 0 1 1
            d.setPage(this.repository.getFilterPageableByCostCenterAndDatesAndCurrency(
                    costCenterId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterAndDatesAndCurrency(costCenterId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalKilometers(this.repository.getSumKmCostCenterAndDatesAndCurrency(costCenterId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalAmount(this.repository.getSumTotalCostCenterAndDatesAndCurrency(costCenterId, startDate, endDate, consecutive, currencyId, state));
        } else if (collaboratorId!=0 && costCenterId==0 && currencyId!=0) { // 1 0 1
            d.setPage(this.repository.getFilterPageableByCollaboratorId(
                    collaboratorId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalKilometers(this.repository.getSumKmCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotalAmount(this.repository.getSumTotalCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, currencyId, state));
        }  else if (collaboratorId==0 && costCenterId==0 && currencyId!=0) { // 0 0 1
            d.setPage(this.repository.getPageableByDateAndCurrency(
                    startDate, endDate, consecutive, state, currencyId, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDatesAndCurrency(startDate, endDate, consecutive, state, currencyId));
            d.setTotalKilometers(this.repository.getSumKmByDatesAndCurrency(startDate, endDate, consecutive,state, currencyId));
            d.setTotalAmount(this.repository.getSumTotalByDatesAndCurrency(startDate, endDate, consecutive, state, currencyId));
        } else if (collaboratorId==0 && costCenterId!=0 && currencyId==0) { // 0 1 0
            d.setPage(this.repository.getFilterPageableByCostCenterAndDates(
                    costCenterId, startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterAndDates(costCenterId, startDate, endDate, consecutive, state));
            d.setTotalKilometers(this.repository.getSumKmCostCenterAndDates(costCenterId, startDate, endDate, consecutive, state));
            d.setTotalAmount(this.repository.getSumTotalCostCenterAndDates(costCenterId, startDate, endDate, consecutive, state));
        } else if (collaboratorId!=0 && costCenterId==0 && currencyId==0) { // 1 0 0 aca
            d.setPage(this.repository.getFilterPageableByCollaboratorId(
                    collaboratorId, startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, state));
            d.setTotalKilometers(this.repository.getSumKmCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, state));
            d.setTotalAmount(this.repository.getSumTotalCollaboratorrAndDates(collaboratorId, startDate, endDate, consecutive, state));
        } else if (collaboratorId!=0 && costCenterId!=0 && currencyId==0) { // 1 1 0
            d.setPage(this.repository.getFilterPageableByCollaboratorIdAndCoscenterId(
                    collaboratorId, startDate, endDate, consecutive, costCenterId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
            d.setTotalKilometers(this.repository.getSumKmByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
            d.setTotalAmount(this.repository.getSumTotalByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
        }

        if (!isLoadedCombos) {

//            d.setCostCenters(this.costCenterRepository.getByState(CostCenterConstants.ACTIVE));
            d.setCurrencies(this.currencyRepository.findAll());


            d.setCollaboratorList(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));

            List<String> states = new ArrayList<String>();
            states.add("Pagada");
            states.add("Pendiente");
            d.setStates(states);

        }
        if (currencyId!=null && currencyId!=0) {
            Optional<Currency> oc = this.currencyRepository.findById(currencyId);
            if (oc != null) {
                d.setCurrency(oc.get().getName());
            }
        }

        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public KilometerDTO getKilometer() {
        KilometerDTO d = new KilometerDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));
        d.setCostCenters(new ArrayList<>());
//        d.setCostCenters(this.costCenterRepository.getByStates(CostCenterConstants.ACTIVE, CostCenterConstants.QUOTE));
        d.setCurrency(currencyRepository.findAll());
        d.setDepartments(departmentRepository.findAll());

        List<String> states = new ArrayList<String>();
        states.add("Pagada");
        states.add("Pendiente");
        states.add("Vencida");
        d.setEstados(states);
        return d;
    }

    public ResultBase save(Kilometer c) {

        ResultBase r = new ResultBase();
        boolean isNew = false;
        if (c.getId()==null && existsCollaboratorCode(c)) {
            r = new ResultBase();
            r.setResult(false);
            r.setMessage("El codigo de kilometraje ya existe para este colaborador, debe cambiarlo para poder insertarlo");
            return r;
        }

        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            isNew = true;
        }


        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        Kilometer k =  this.repository.save(c);
        this.billPayService.addBillPay(c,isNew);

        r.setMessage(
                "El Kilometraje fue agregado");
        r.setResult(true);
        return r;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {
//        System.out.println("createPageable " + direction + " " + byField);
        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);

    }

    public List<KilometerDetail> getByKilometerIds(List<Integer> kmsId) {
        return this.repository.getDetailsByIds(kmsId);
    }

    public List<BalanceKmDTO> getBalanceKmDTO(java.sql.Date startDate, java.sql.Date endDate) {
        return this.repository.getBalanceKmDTO(startDate, endDate);
    }


    public Map getDataForPdf(Integer id) {


        Map <String,Object> data = new HashMap<String,Object>();

        Optional<Kilometer> c = repository.findById(id);
        Kilometer d = c.get();
        Integer old = Calendar.getInstance().get(Calendar.YEAR) - d.getCollaborator().getVehicleYear();
        FeeVehiculeFuel fvf = this.feeVehiculeFuelService.getFeeVehiculeFuel(
                d.getCollaborator().getVehiculeType(),
                d.getCollaborator().getVehicleFuelType(),
                old);


        data.put("km",d);
//        data.put("kmd",kmds);
        data.put("fee",fvf);
        return data;

    }

    private boolean existsCollaboratorCode(Kilometer r) {
        return this.repository.getCount(r.getCodeKm(), r.getCollaborator().getId()) > 0;
    }

    public Result delete(Kilometer k) {
        Result r = null;

        try {

            this.billPayService.deleteBillPay(k.getId(), StringHelper.KMS);
            this.repository.delete(k);

            r = new Result(Result.RESULT_CODE.DELETE, "Se elimino el kilometraje");
        } catch (Exception e) {
            r = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return r;
    }

    public void updateStatusByIds(List<Integer> kmsIds, String statusDataFrozen) {
        this.repository.updateKilometerToFrozenByIds(kmsIds);
    }
}
