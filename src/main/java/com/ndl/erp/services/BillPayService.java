package com.ndl.erp.services;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.fe.core.impl.BillConfigurationDataImpl;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ndl.erp.constants.BillPayConstants.*;
import static com.ndl.erp.util.StringHelper.IMP_VENTA_DEFAULT;
import static com.ndl.erp.util.StringHelper.BP_NUMBER;

@Component
@Transactional
public class BillPayService {

    @Autowired
    private BillPayRepository repository;

    private final Path rootConfirmationLocation = Paths.get("confirm-dir");

    @Autowired
    private ServiceCabysRepository serviceCabysRepository;

    @Autowired
    private  ExhangeRateRepository exhangeRateRepository;

    @Autowired
    private InventarioItemRepository inventarioItemsRepository;


    @Autowired
    private ProductCabysRepository productCabysRepository;


    @Autowired
    private ConfirmacionRechazosService confirmacionRechazosService;


    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

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
    private MonthlyClosureBillPayRepository monthlyBillPayRepository;

    @Autowired
    private BillConfigurationDataImpl billConfigurationData;


    @Autowired
    private InvestoryManagementService inventoryManagementService;

    @Autowired
    private BodegaManagerService bodegaManagerService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private TaxesIvaRepository taxesIvaRepository;

    @Autowired
    private GeneralParameterService generalParameterService;


    public BillPayDTO getBillPay(Integer id) throws Exception{
        BillPayDTO d = this.getBillPay();
        this.initDefaultCostCenter(d);

//        d.setExchangeRates(this.exhangeRateRepository.getActivoExchangeRates());

        if (id==null) {
            BillPay d1 = new BillPay();
            d1.setStatus(BILL_PAY_ESTADO_PENDIENTE);
            if (d.getIngresoAutomatico()!=null && d.getIngresoAutomatico()==1) {
                d1.setIngresadoBodega(true);
            } else {
                d1.setIngresadoBodega(false);
            }
            d1.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<BillPay> c = repository.findById(id);
        d.setCurrent(c.get());
        initCabys(d);

        return d;
    }

    private void initDefaultCostCenter(BillPayDTO d) throws Exception{
       GeneralParameter generalParameterDefaultCostCenter;
       generalParameterDefaultCostCenter = this.generalParameterRepository.findByCode(BILL_PAY_DEFAULT_COST_CENTER);

       if (generalParameterDefaultCostCenter == null) {
           throw new GeneralInventoryException("No se encontró el parametro del centro de costo default: " + BILL_PAY_DEFAULT_COST_CENTER);
       }

       CostCenter costCenter = this.costCenterRepository.getById(generalParameterDefaultCostCenter.getIntVal());
       if (costCenter == null) {
            throw new GeneralInventoryException("No se encontró el centro de costo default usando el parametro: " + BILL_PAY_DEFAULT_COST_CENTER);
       }

       d.setDefaultCostCenter(costCenter);


    }

    private void initCabys(BillPayDTO d) {

        List<Integer> products = this.repository.getProductoBillPayDetail(d.getCurrent().getId());
        List<Integer> services =  this.repository.getServiceBillPayDetail(d.getCurrent().getId());

        if (services!=null && services.size()>0) {
            List<ServiceCabys> l1 = this.serviceCabysRepository.findByIdIn(services);
            d.setServiceCabys(l1);
        }

        if (products!=null && products.size()>0) {
//            List<ProductCabys> l2 = this.productCabysRepository.findByIdIn(products);
            List<InventarioItem> l2 = this.inventarioItemsRepository.findByIdIn(products);


            d.setItems(l2);
        }
    }


    public BillPaysDTO getBillPay(Integer providerId,
                                     Date startDate,
                                  Date endDate,
                                  Date expireStartDate,
                                  Date expireEndDate,
                                     String consecutive,
                                     String type,
                                  Integer currencyId,
                                  String state,
                                     Boolean isLoadedCombos,
                                     Integer pageNumber,
                                     Integer pageSize, String sortDirection,
                                     String sortField) {

        BillPaysDTO d = new BillPaysDTO();


        if (providerId!=0&& currencyId!=0 && expireStartDate!=null) { // 1 1 1
            d.setPage(this.repository.getFilterPageableByProviderIdAndCurrencyIdAndExpireDates(
                    providerId, startDate, endDate, consecutive, currencyId, state, type,
                    expireStartDate, expireEndDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByProviderAndCurrencyIdAndExpireDates(providerId, startDate, endDate,
                    consecutive, currencyId, state, type,  expireStartDate, expireEndDate));
           d.setTotalAmount(this.repository.getSumTotalProviderAndCurrencyIdAndExpireDates(providerId, startDate, endDate, consecutive, currencyId, state, type,
                   expireStartDate, expireEndDate));
           d.setTotals(this.repository.getSumTotalsProviderAndCurrencyIdAndExpireDates(providerId, startDate, endDate, consecutive, currencyId, state, type,
                   expireStartDate, expireEndDate));


        } else if (providerId!=0&& currencyId!=0 && expireStartDate==null) { // 1 1 0
            d.setPage(this.repository.getFilterPageableByProviderIdAndCurrencyId(
                    providerId, startDate, endDate, consecutive, currencyId, state, type, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByProviderAndCurrencyIdAndDates(providerId, startDate, endDate,
                    consecutive, currencyId, state, type));
           d.setTotalAmount(this.repository.getSumTotalProviderAndCurrencyIdAndDates(providerId, startDate, endDate, consecutive, currencyId, state, type));
           d.setTotals(this.repository.getSumTotalsProviderAndCurrencyIdAndDates(providerId, startDate, endDate, consecutive, currencyId, state, type));


        } else if (providerId==0 && currencyId!=0 && expireStartDate==null) { // 0 1 0
            d.setPage(this.repository.getPageableByCurrencyId(
                    startDate, endDate, consecutive, currencyId, state, type, createPageable1(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCurrencyId(startDate, endDate, consecutive, currencyId, state, type));
            d.setTotalAmount(this.repository.getSumTotalByCurrencyId(startDate, endDate, consecutive, currencyId, state, type));
            d.setTotals(this.repository.getSumTotalsByCurrencyId(startDate, endDate, consecutive, currencyId, state, type));
        }  else if (providerId==0 && currencyId!=0 && expireStartDate!=null) { // 0 1 1
            d.setPage(this.repository.getPageableByCurrencyIdAndExpireDates(
                    startDate, endDate, consecutive, currencyId, state, type, expireStartDate, expireEndDate,
                    createPageable1(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByCurrencyIdAndExpireDates(startDate, endDate, consecutive, currencyId, state, type,
                     expireStartDate, expireEndDate));
            d.setTotalAmount(this.repository.getSumTotalByCurrencyIdAndExpireDates(startDate, endDate, consecutive, currencyId, state, type,
                    expireStartDate, expireEndDate));
            d.setTotals(this.repository.getSumTotalsByCurrencyIdAndExpireDates(startDate, endDate, consecutive, currencyId, state, type,
                    expireStartDate, expireEndDate));
        }  else if (providerId!=0 && currencyId==0 && expireStartDate==null) { // 1 0 0
            d.setPage(this.repository.getFilterPageableByProviderId(
                    providerId, startDate, endDate, consecutive, state, type, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByProviderId(providerId, startDate, endDate,
                    consecutive, state, type));
            d.setTotalAmount(this.repository.getSumTotalProviderId(providerId, startDate, endDate, consecutive, state, type));
            d.setTotals(this.repository.getSumTotalsProviderId(providerId, startDate, endDate, consecutive, state, type));

        } else if (providerId!=0 && currencyId==0 && expireStartDate!=null) { // 1 0 1
            d.setPage(this.repository.getFilterPageableByProviderIdAndExpireDates(
                    providerId, startDate, endDate, consecutive, state, type,
                     expireStartDate,expireEndDate,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByProviderIdAndExpireDates(providerId, startDate, endDate,
                    consecutive, state, type, expireStartDate, expireEndDate));
            d.setTotalAmount(this.repository.getSumTotalProviderIdAndExpireDates(providerId, startDate, endDate, consecutive,
                    state, type,  expireStartDate, expireEndDate));
            d.setTotals(this.repository.getSumTotalsProviderIdAndExpireDates(providerId, startDate, endDate, consecutive,
                    state, type, expireStartDate, expireEndDate));

        } else if (providerId==0 && currencyId==0  && expireStartDate==null) { // 0 0 0
            d.setPage(this.repository.getPageableByDates(
                    startDate, endDate, consecutive, state, type, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDates(startDate, endDate,
                    consecutive, state, type));
            d.setTotalAmount(this.repository.getSumTotalByDates(startDate, endDate, consecutive, state, type));
            d.setTotals(this.repository.getSumTotalsByDates(startDate, endDate, consecutive, state, type));

        } else if (providerId==0 && currencyId==0 && expireStartDate!=null) { // 0 0 1
            d.setPage(this.repository.getPageableByDatesAndExpireDates(
                    startDate, endDate, consecutive, state, type, expireStartDate, expireEndDate, createPageable(pageNumber, pageSize, sortDirection, sortField)
            ));
            d.setTotal(this.repository.countAllByDatesAndExpireDates(startDate, endDate,
                    consecutive, state, type, expireStartDate, expireEndDate));
            d.setTotalAmount(this.repository.getSumTotalByDatesAndExpireDates(startDate, endDate, consecutive, state,
                    type, expireStartDate, expireEndDate));
            d.setTotals(this.repository.getSumTotalsByDatesAndExpireDates(startDate, endDate, consecutive, state,
                    type, expireStartDate, expireEndDate));

        }

        if (!isLoadedCombos) {

            d.setProviders(this.providerRepository.findByStatus("Activo"));
            d.setCurrencies(this.currencyRepository.findAll());


            d.setStates(getEstadosList());

            List<String> types = new ArrayList<String>();
            types.add("CXP");
            types.add("REM");
            types.add("KMS");
            d.setTypes(types);

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

    public List <String> getEstadosList(){
        List<String> states = new ArrayList<String>();
        states.add(BILL_PAY_ESTADO_EDICION);
        states.add(BILL_PAY_ESTADO_PENDIENTE);
        states.add(BILL_PAY_ESTADO_PAGADA);
        states.add(BILL_PAY_ESTADO_VENCIDA);
        return states;
    }


    public BillPaysNcDTO getBillPaysNcList(String filter){
        BillPaysNcDTO billPaysNcDTO = new BillPaysNcDTO();

        billPaysNcDTO.setBillPayDetails(repository.getBillPaysNcByBillNumberAndProvider(filter));

        return billPaysNcDTO;

    }

    public BillPayDTO getBillPay() {
        BillPayDTO d = new BillPayDTO();


        d.setProviders(this.providerRepository.findByStatus("Activo"));
        d.setBodegas(this.bodegaRepository.getBodegaByStatus("Activa"));
        d.setCurrency(currencyRepository.findAll());
        d.setEstados(getEstadosList());
        d.setExchangeRates(this.exchangeRateService.getActivesExchangeRate());

        List<String> types = new ArrayList<String>();
        types.add("Producto");
        types.add("Servicio");
        d.setTypes(types);

        d.setTaxesList(this.taxesIvaRepository.getTaxes());
//        d.setTaxes(this.generalParameterRepository.getByCode("IMP_VENTA"));


        GeneralParameter gp = this.generalParameterRepository.findByCode("DEFAULT_INGRESO_BODEGA_CHECK");
        if (gp!=null) {
            d.setIngresoAutomatico(gp.getIntVal());
        }

        GeneralParameter gp2 = this.generalParameterRepository.findByCode(BP_NUMBER);
        if (gp2!=null && gp2.getVal()!=null) {
            d.setBillNumberAutomatic(Integer.parseInt(gp2.getVal()));
        }
        GeneralParameter gp1 = this.generalParameterRepository.findByCode("DEFAULT_CX_LITE_DETAIL");
        if (gp1!=null) {
            d.setIsLiteDetail(gp1.getIntVal());
        }
        d.setCostCenters(new ArrayList<>());

        d.setDefaultIva(getDefaultIva());

        return d;
    }

    private TaxesIva getDefaultIva() {
        List<GeneralParameter> gp = this.generalParameterRepository.getByCode(IMP_VENTA_DEFAULT);
        if (gp==null || gp.size()==0) {
            return null;
        }
        GeneralParameter defaultGp = gp.get(0);
        try {
            Double val = Double.parseDouble(defaultGp.getVal());
            List<TaxesIva> taxes = taxesIvaRepository.getTaxes();
            if (taxes==null) {
                return null;
            }
            TaxesIva foundTax = null;
            for (TaxesIva tiva: taxes) {
                if (tiva.getTaxPorcent()!=null && tiva.getTaxPorcent().doubleValue()==val.doubleValue()) {
                    foundTax = tiva;
                    break;
                }
            }
            return foundTax;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public BillPay chageStatus(BillPay bp) throws Exception{


        if (bp==null) {
            throw new GeneralInventoryException("No se encontro el id de la orden de compra");
        }

//        poc.setStatus(status);
//        poc = this.save(poc);
        return this.save(bp);
    }

    @Transactional(rollbackFor = {Exception.class})
    public BillPay save(BillPay c) throws Exception{

        boolean isNew = false;
        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUser(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());

            if (c.getIngresadoBodega()==null) {
                c.setIngresadoBodega(false);
            }

            GeneralParameter gp2 = this.generalParameterRepository.findByCode(BP_NUMBER);
            if (gp2!=null && gp2.getVal()!=null && Integer.parseInt(gp2.getVal())==1) {
                c.setBillNumber(this.generalParameterService.getNextDocumentNumber(BP_NUMBER).toString());
            }
            isNew = true;
        }

        if (c.getProvider()!=null && c.getProvider().getName()!=null) {
            c.setProviderName(c.getProvider().getName());
        }
        c.setUpdateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        /*Félix Saborio 21-06-2021: Si el documento pasa de edicion a
         otro estado y con ingresado a bodega true, se pasa directo al inventario
         */
        if (!isNew){
            this.procesarBillPayDirecto(c);
        }

        BillPay savedBp =  this.repository.save(c);

        return savedBp;
    }

    /*
      11/05/2021 Felix Saborio:  Este metodo procesa una orden de compra y la ingresa al inventario de la bodega si la bandera
      ingresado_bodega es igual a 1, y que el estado anterior haya sido Edicion
    */
    public void procesarBillPayDirecto(BillPay billPay) throws Exception {

        BillPay olddBillPay = this.getBillPayById(billPay.getId());

        if (billPay == null) {
            //hrow new java.lang.RuntimeException("La cuenta por pagar no existe!");
            throw new GeneralInventoryException("La cuenta por pagar no existe!");
        }

        if (olddBillPay == null) {
            //throw new java.lang.RuntimeException("No se pudo validar el estado anterior de la cuenta por pagar");
            throw new GeneralInventoryException("No se pudo validar el estado anterior de la cuenta por pagar");

        }


        if (billPay.getDetails() == null) {
            //throw new java.lang.RuntimeException("El detalle de la cuenta por pagar  está vacío");
            throw new GeneralInventoryException("El detalle de la cuenta por pagar  está vacío");
        }

        if (billPay.getIngresadoBodega() == true && !billPay.getStatus().equals(BILL_PAY_ESTADO_EDICION) && olddBillPay.getStatus().equals(BILL_PAY_ESTADO_EDICION)) {

            this.bodegaManagerService.entradaBodega(billPay, null);
        }

    }


    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    private PageRequest createPageable1(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
//        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public List<BillPay> getByIds(List<Integer> ids) {
        return this.repository.findByIdIn(ids);
    }

    public List<BillPayDetail> getAllDetailsByBillPayIds(List<Integer> idsBC) {
        return this.repository.getDetailsByIds(idsBC);
    }

    public List<BillPay> getByDates(Date start, Date end, Boolean isClosure) {
        return this.repository.getByDate(start, end, isClosure);
    }

    public BillPay getBillPayById(Integer id) {

        Optional<BillPay> c = repository.findById(id);
       if (c==null) {
           //throw new RuntimeException("No found BillPay " + id);
           throw new GeneralInventoryException("Cuenta por pagar no encontrada: " + id);
       }
        return c.get();
    }


    public List<BalanceBillPayDTO> getBalanceBillPayDTO(java.sql.Date startDate, java.sql.Date endDate) {
        return this.repository.getBalanceBillPayDTO(startDate, endDate);
    }

    private BillPay getBillPay(Kilometer km) {
        BillPay bp = new BillPay();
        bp.setCollaborator(km.getCollaborator());
        bp.setBillNumber(km.getCodeKm());
        bp.setBillDate(km.getDateKm());
        bp.setEndDate(km.getDueDate());
        bp.setPayDate(km.getDueDate());
        bp.setStatus(km.getStatus());
        bp.setCurrency(km.getCurrency());
        bp.setType(StringHelper.KMS);
        bp.setTypeId(km.getId());
        bp.setIdUser(km.getIdUser());
        bp.setTotal(km.getTotal());
        bp.setProviderName(km.getCollaborator().getName() + " " + km.getCollaborator().getLastName());

        return bp;
    }

    private BillPay getBillPay(BillPay bp, Kilometer km) {
       // BillPay bp = new BillPay();
        bp.setCollaborator(km.getCollaborator());
        bp.setBillNumber(km.getCodeKm());
        bp.setBillDate(km.getDateKm());
        bp.setEndDate(km.getDueDate());
        bp.setPayDate(km.getDueDate());
        bp.setStatus(km.getStatus());
        bp.setCurrency(km.getCurrency());
        //bp.setType(StringHelper.KMS);
        //bp.setTypeId(km.getId());
        bp.setIdUser(km.getIdUser());
        bp.setTotal(km.getTotal());
        bp.setProviderName(km.getCollaborator().getName() + " " + km.getCollaborator().getLastName());

        return bp;
    }


    public void addBillPay(Kilometer c, boolean isNew) {
        if(isNew) {
            BillPay bp = this.getBillPay(c);
            this.repository.save(bp);
        } else {
            BillPay bp =  this.repository.findByTypeIdAndType(c.getId(), StringHelper.KMS);
            bp = getBillPay( bp, c);
            this.repository.save(bp);
        }

    }

    public void addBillPay(Refundable c, boolean isNew) {
        if(isNew) {
            BillPay bp = this.getBillPay(c);
            this.repository.save(bp);
        } else {
            BillPay bp =  this.repository.findByTypeIdAndType(c.getId(), StringHelper.REM);
            bp = getBillPay( bp, c);
            this.repository.save(bp);
        }
    }

    private BillPay getBillPay(BillPay bp, Refundable refundable) {
        bp.setCollaborator(refundable.getCollaborator());
        bp.setBillNumber(refundable.getCodeInvoice());
        bp.setBillDate(refundable.getDateInvoice());
        bp.setEndDate(refundable.getDueDate());
        bp.setPayDate(refundable.getDueDate());
        bp.setCurrency(refundable.getCurrency());
        bp.setIdUser(refundable.getIdUser());
        bp.setTotal(refundable.getTotal());
        bp.setStatus(refundable.getStatus());
        bp.setProviderName(refundable.getCollaborator().getName() + " " + refundable.getCollaborator().getLastName());
        return bp;
    };


    private BillPay getBillPay(Refundable rf) {
        BillPay bp = new BillPay();
        bp.setCollaborator(rf.getCollaborator());
        bp.setBillNumber(rf.getCodeInvoice());
        bp.setBillDate(rf.getDateInvoice());
        bp.setEndDate(rf.getDueDate());
        bp.setPayDate(rf.getDueDate());
        bp.setStatus(rf.getStatus());
        bp.setCurrency(rf.getCurrency());
        bp.setType(StringHelper.REM);
        bp.setTypeId(rf.getId());
        bp.setTotal(rf.getTotal());
        bp.setIdUser(rf.getIdUser());
        bp.setProviderName(rf.getCollaborator().getName() + " " + rf.getCollaborator().getLastName());

        return bp;
    }

    @Transactional
    public void deleteBillPay(Integer id, String type) {
        BillPay bp = this.repository.getByTypeIdAndType(id, type);
        if (bp!=null) {
//            MonthlyClosureBillPay mbp = this.monthlyBillPayRepository.findByBillPay(bp);
            try {
                MonthlyClosureBillPay mbp = this.monthlyBillPayRepository.getBy(bp.getId());
                if (mbp != null) {
                    this.monthlyBillPayRepository.delete(mbp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //this.repository.deleteByTypeIdAndType(id, type);
            this.repository.delete(bp);
        }
    }

    public Result delete(BillPay c) {

        Result r = null;

        try {


            try {
                MonthlyClosureBillPay mbp = this.monthlyBillPayRepository.getBy(c.getId());
                if (mbp != null) {
                    this.monthlyBillPayRepository.delete(mbp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.repository.delete(c);

            r = new Result(Result.RESULT_CODE.DELETE, "Se elimino la cuenta por pagar");
        } catch (Exception e) {
            r = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return r;
    }

    @Transactional
    public void updateBillPaysOutOfClosure(List<Integer> ids) {
        this.repository.updateBillPayByIds(ids);
    }

    @Transactional
    public void updateStatusClosing(List<Integer> ids) {
        this.repository.updateBillPayToFrozenByIds(ids);
    }

    public List<Integer> getTypeIdsByType(List<Integer> ids, String type) {
        return this.repository.getTypeIdByType(ids, type);
    }


    public HaciendaMensajeDTO loadDataFromFile(MultipartFile file) throws JAXBException, IOException {

        return storeConfirmation(file);

    }

    private HaciendaMensajeDTO storeConfirmation(MultipartFile file) throws IOException, JAXBException {
        String fullFileName = "";
        Format formatter = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
        String prefix = formatter.format(new Date());
        String filename =   prefix + file.getOriginalFilename();
        Calendar calendar = new GregorianCalendar();

        HaciendaMensajeDTO dto = null;


        String path = this.billConfigurationData.getBillClientPath() + calendar.get(Calendar.YEAR)
                + File.separator + (calendar.get(Calendar.MONTH) + 1) + File.separator;

        try {
            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectories(rootConfirmationLocation);

                }
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!");
            }
            Path p = this.rootConfirmationLocation.resolve(filename);
//            Path p = this.rootConfirmationLocation.resolve(file.getOriginalFilename());
            if (Files.exists(p)) {
                Files.delete(p);
            }
            Files.copy(file.getInputStream(), this.rootConfirmationLocation.resolve(filename));
//            Files.copy(file.getInputStream(), this.rootConfirmationLocation.resolve(file.getOriginalFilename()));
            fullFileName = this.rootConfirmationLocation.toString() + File.separator + filename;
//            fullFileName = this.rootConfirmationLocation.toString() + File.separator + file.getOriginalFilename();




        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FAIL! " + e.getMessage());
        }
        return this.confirmacionRechazosService.getHaciendaMensajeDTO(fullFileName, true);

    }
}
