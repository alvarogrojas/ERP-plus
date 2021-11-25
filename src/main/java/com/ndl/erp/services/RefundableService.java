package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Refundable;
import com.ndl.erp.domain.RefundableDetail;
import com.ndl.erp.dto.BalanceRefundableDTO;
import com.ndl.erp.dto.RefundableDTO;
import com.ndl.erp.dto.RefundablesDTO;
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

import static com.ndl.erp.constants.BodegaConstants.ESTADO_BODEGA_ACTIVA;
import static com.ndl.erp.constants.RefundableConstants.*;

@Component
@Transactional
public class RefundableService {

    @Autowired
    private RefundableRepository repository;

    @Autowired
    private ServiceCabysRepository serviceCabysRepository;

    @Autowired
    private InventarioItemRepository inventarioItemsRepository;

    @Autowired
    private ProductCabysRepository productCabysRepository;


    @Autowired
    private BodegaRepository bodegaRepository;

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
    private GeneralParameterRepository generalParameterRepository;

    @Autowired
    private BillPayService billPayService;

    @Autowired
    private ExchangeRateService exchangeRateService;


    public RefundableDTO getRefundable(Integer id) {
        RefundableDTO d = this.getRefundable();

        if (id==null) {
            Refundable d1 = new Refundable();
            d1.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            d1.setStatus(REFUNDABLE_STATUS_EDICION);
            return d;
        }
        Optional<Refundable> c = repository.findById(id);
        d.setCurrent(c.get());

//        initCabys(d);
        return d;
    }

    public List<String> getRefundableStatusList(){
        List<String> states = new ArrayList<String>();
        states.add(REFUNDABLE_STATUS_EDICION);
        states.add(REFUNDABLE_STATUS_PENDIENTE);
        states.add(REFUNDABLE_STATUS_PAGADA);
        states.add(REFUNDABLE_STATUS_VENCIDA);
        return states;
    }

    public RefundablesDTO getRefundable(Integer collaboratorId, Integer costCenterId,
                                      Date startDate, Date endDate,
                                      String consecutive, Integer currencyId, String state,
                                      Boolean isLoadedCombos,
                                      Integer pageNumber,
                                      Integer pageSize, String sortDirection,
                                      String sortField) {

        RefundablesDTO d = new RefundablesDTO();


        if (collaboratorId!=0 && costCenterId!=0  && currencyId!=0) { //1 1 1
            d.setPage(this.repository.getPageableByCostCenterIdAndCollaboratorAndCurrencyAndLaborDate(
                    costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterIdAndCollaboratorAndCurrencyAndLaborDates(costCenterId, collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotals(this.repository.getSumTotalCostCenterIdAndCollaboratorAndCurrencyAndLaborDates
                    (costCenterId,
                    collaboratorId, startDate, endDate, consecutive, currencyId, state));


        } else if (collaboratorId==0 && costCenterId==0 && currencyId==0) { // 0 0 0
            d.setPage(this.repository.getPageableByDateOnly(
                    startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDatesOnly(startDate, endDate, consecutive, state));
            d.setTotals(this.repository.getSumTotalByDatesOnly(startDate, endDate, consecutive, state));
        } else if (collaboratorId==0 && costCenterId!=0 && currencyId!=0) { // 0 1 1
            d.setPage(this.repository.getFilterPageableByCostCenterAndDatesAndCurrency(
                    costCenterId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterAndDatesAndCurrency(costCenterId, startDate, endDate, consecutive, currencyId, state));
            d.setTotals(this.repository.getSumTotalCostCenterAndDatesAndCurrency(costCenterId, startDate, endDate, consecutive, currencyId, state));
        } else if (collaboratorId!=0 && costCenterId==0 && currencyId!=0) { // 1 0 1
            d.setPage(this.repository.getFilterPageableByCollaboratorIdAndCurrency(
                    collaboratorId, startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorAndCurrencyAndDates(collaboratorId, startDate, endDate, consecutive, currencyId, state));
            d.setTotals(this.repository.getSumTotalCollaboratorAndCurrencyAndDates(collaboratorId, startDate, endDate, consecutive, currencyId, state));
        } else if (collaboratorId==0 && costCenterId==0 && currencyId!=0) { // 0 0 1 only currency
            d.setPage(this.repository.getPageableByCurrencyIdAndDates(
                    startDate, endDate, consecutive, currencyId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCurrencyAndDates(startDate, endDate, consecutive, currencyId, state));
            d.setTotals(this.repository.getSumTotalByCurrencyAndDates(startDate, endDate, consecutive, currencyId, state));
        } else if (collaboratorId==0 && costCenterId!=0 && currencyId==0) { // 0 1 0
            d.setPage(this.repository.getFilterPageableByCostCenterAndDates(
                    costCenterId, startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCostCenterAndDates(costCenterId, startDate, endDate, consecutive, state));

            d.setTotals(this.repository.getSumTotalCostCenterAndDates(costCenterId, startDate, endDate, consecutive, state));
        } else if (collaboratorId!=0 && costCenterId==0 && currencyId==0) { // 1 0 0 aca
            d.setPage(this.repository.getFilterPageableByCollaboratorId(
                    collaboratorId, startDate, endDate, consecutive, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCollaboratorAndDates(collaboratorId, startDate, endDate, consecutive, state));

            d.setTotals(this.repository.getSumTotalCollaboratorAndDates(collaboratorId, startDate, endDate, consecutive, state));
        }
//        else if (collaboratorId!=0 && costCenterId==0 && currencyId!=0) { // 1 1 0
//            d.setPage(this.repository.getFilterPageableByCollaboratorIdAndCurrencyId(
//                    collaboratorId, startDate, endDate, consecutive, costCenterId, state, createPageable(pageNumber, pageSize, sortDirection, sortField)
//            ));
//            d.setTotal(this.repository.countAllByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
//            d.setTotalKilometers(this.repository.getSumKmByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
//            d.setTotals(this.repository.getSumTotalByCollaboratorIdAndCoscenterId(collaboratorId, startDate, endDate, consecutive, costCenterId, state));
//        }





        if (!isLoadedCombos) {

            //d.setCostCenters(this.costCenterRepository.getByState(CostCenterConstants.ACTIVE));
            d.setCurrencies(this.currencyRepository.findAll());
            d.setCollaboratorList(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));
            d.setStates(this.getRefundableStatusList());

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

    public RefundableDTO getRefundable() {
        RefundableDTO d = new RefundableDTO();
        d.setCollaborators(this.collaboratorRepository.findByStatus(CostCenterConstants.COLLABORATOR_ACTIVE));
        d.setCostCenters(new ArrayList<>());
        d.setCurrency(currencyRepository.findAll());
        d.setDepartments(departmentRepository.findAll());
        d.setBodegas(this.bodegaRepository.getBodegaByStatus(ESTADO_BODEGA_ACTIVA));
        d.setEstados(this.getRefundableStatusList());
        d.setExchangeRates(this.exchangeRateService.getActivesExchangeRate());

        List<String> types = new ArrayList<String>();
        types.add("Alimentación");
        types.add("Bodega");
        types.add("Instalación");
        types.add("Comunicaciones");
        types.add("Oficina");
        types.add("Repuestos");
        types.add("Seguros");
        types.add("Taller");
        types.add("Transporte");
        types.add("Otros");
        d.setTypes(types);

        List<String> parentType = new ArrayList<String>();
        parentType.add("Servicio");
        parentType.add("Producto");
        d.setParentTypes(parentType);
        d.setTypeExpends(parentType);

        d.setTaxes(this.generalParameterRepository.getByCode("IMP_VENTA"));
        return d;
    }


    public ResultBase save(Refundable c) {

        ResultBase r = new ResultBase();
        boolean isNew = false;
        if (c.getId()==null && existsCollaboratorBillCode(c)) {
            r = new ResultBase();
            r.setResult(false);
            r.setMessage("El codigo del reebolsable ya existe para este colaborador, debe cambiarlo para poder insertarlo");
            return r;
        }
        if (c.getId()==null) {

            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            isNew = true;
        }

        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        c =  this.repository.save(c);

        this.billPayService.addBillPay(c,isNew);
        r.setMessage(
                "El reembolsable fue agregado");
        r.setResult(true);
        return r;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public List<RefundableDetail> getByRefundableDetailsByIds(List<Integer> remsId) {
        return this.repository.getDetailsByIds(remsId);
    }


    public List<BalanceRefundableDTO> getBalanceRefundableDTO(java.sql.Date startDate, java.sql.Date endDate) {
        return this.repository.getBalanceRefundableDTO(startDate, endDate);
    }

    public Map getDataForPdf(Integer id) {
        Map <String,Object> data = new HashMap<String,Object>();

        Optional<Refundable> c = repository.findById(id);
        Refundable d = c.get();

        data.put("rfd",d);
        return data;
    }

//    private void initCabys(RefundableDTO d) {
//
//        List<Integer> products = this.repository.getProductoDetail(d.getCurrent().getId());
//        List<Integer> services =  this.repository.getServiceDetail(d.getCurrent().getId());
//
//        if (services!=null && services.size()>0) {
//            List<ServiceCabys> l1 = this.serviceCabysRepository.findByIdIn(services);
//            d.setServiceCabys(l1);
//        }
//
//        if (products!=null && products.size()>0) {
//            List<InventarioItem> l2 = this.inventarioItemsRepository.findByIdIn(products);
//
//
//            d.setItems(l2);
//        }
//    }

    private boolean existsCollaboratorBillCode(Refundable r) {
        return this.repository.getCount(r.getCodeInvoice(), r.getCollaborator().getId()) > 0;
    }

    public Result delete(Refundable r) {
//        this.repository.delete(r);
//        this.billPayService.deleteByTypeIdAndType(r.getId(), StringHelper.REM);
//        ResultBase r1 = new ResultBase();
//        r1.setMessage(
//                "El Reembolsable fue borrado exitosamente");
//        r1.setResult(true);
//        return r1;

        Result result = null;

        try {

            this.repository.delete(r);
            this.billPayService.deleteBillPay(r.getId(), StringHelper.REM);
            result = new Result(Result.RESULT_CODE.DELETE, "El Reembolsable fue borrado exitosamente");
        } catch (Exception e) {
            result = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return result;
    }



    public void updateStatusByIds(List<Integer> remIds) {
        this.repository.updateRefundableToFrozenByIds(remIds);
    }
}
