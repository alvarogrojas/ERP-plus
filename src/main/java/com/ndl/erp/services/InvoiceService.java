package com.ndl.erp.services;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.mappers.FacturaElectronicaExportacionV43Mapper;
import com.ndl.erp.fe.mappers.InvoiceNotaCreditoV43Mapper;
import com.ndl.erp.fe.mappers.InvoiceV43Mapper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.services.pos.PosService;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import com.ndl.erp.views.BillViewBuilder;
import com.ndl.erp.views.NoteCeditViewBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.ndl.erp.constants.InvoiceConstants.*;
import static com.ndl.erp.constants.MedioPagoConstants.*;
import static com.ndl.erp.constants.PurchaseOrderClientConstants.TIPO_DETALLE_PRODUCTO_PURCHASE_ORDER_CLIENT;
import static com.ndl.erp.constants.TransaccionPendienteConstants.TRANSACCION_PENDIENTE_ESTADO_ESPERA;
import static com.ndl.erp.constants.TransaccionPendienteConstants.TRANSACCION_PENDIENTE_TTPO_ENVIO_ASINCRONO;

@Service
@Transactional
public class InvoiceService {

    @Value( "${erp.site}" )
    private String serverWebsite;

    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private FacturaElectronicaUnmarshaller feu;

    @Autowired
    private FacturaElectronicaService facturaElectronicaService;


    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private PurchaseOrderClientRepository pocRepository;

    @Autowired
    private PurchaseOrderClientService pocService;

    private Path fileStorageLocation;

    @Autowired
    private GlobalDataManager globalDataManager;


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CentroCostosRepository costCenterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private EconomicActivityRepository economicActivityRepository;
    @Autowired
    private BillConfigService billConfigService;
    @Autowired
    GeneralParameterService generalParameterService;


    @Autowired
    private BodegaManagerService bodegaManagerService;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    private BillCollectRepository billCollectRepository;

    @Autowired
    private InvestoryManagementService investoryManagementService;

    @Autowired
    private InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    private SessionPosRepository sessionPosRepository;

    @Autowired
    private TransaccionPendienteRepository transaccionPendienteRepository;

    @Autowired
    private GeneralParameterRepository generalParameterRepository;

    @Autowired
    private PosService posService;

    @Autowired
    private MedioPagoRepository medioPagoRepository;


    public InvoiceDTO getNewInvoiceByPocId(Integer id) {


        if (id == null) {
            throw new NotFoundException("Servicio debe ser llamado con un ID de una orden de compra valida");
        }
        InvoiceDTO d = this.getNewInvoiceByPoc(id);
        Invoice i = new Invoice();

        i.setClient(d.getClients().get(0));
        i.setPoc(d.getPocs().get(0));
        i.setAddress(i.getClient().getAddress());
        i.setPhone(i.getPhone());
        i.setCreditDays(i.getClient().getCreditDay());
        d.setCurrent(i);


        return d;
    }

    public InvoiceDTO getInvoice(Integer id) {
        InvoiceDTO d = this.getInvoice();

        if (id == null) {
            Invoice d1 = new Invoice();
            d1.setUserId(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            d.setCurrent(d1);
            return d;
        }
        Optional<Invoice> c = repository.findById(id);
        try {
            if (c==null || c.get()==null) {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }


        d.setCurrent(c.get());
        if (d.getCurrent().getStatus().equals("Anulada")) {
            d.setInvoiceNotaCredito(getNotaCreditoData(d.getCurrent()));

        }
        if (d.getCurrent() != null && d.getCurrent().getClient() != null) {
            d.setPocs(this.pocRepository.findByStatusAndClientId("Ejecucion",
                    d.getCurrent().getClient().getClientId()));
            if (d.getPocs() != null && d.getCurrent().getPoc() != null &&
                    !d.getPocs().contains(d.getCurrent().getPoc())) {
                Optional<PurchaseOrderClient> poc = this.pocRepository.findById(d.getCurrent().getPoc().getId());
                if (d.getPocs() == null) {
                    d.setPocs(new ArrayList<>());
                }
                if (poc != null) {
                    d.getPocs().add(poc.get());
                }
            }
        }
        return d;
    }

    private InvoiceNotaCredito getNotaCreditoData(Invoice current) {
        InvoiceNotaCredito notaCredito = null;
        try {
            notaCredito = this.invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(current.getId());
//            if (notaCredito!=null) {
//
//            }

        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        return notaCredito;
    }

    public InvoiceDTO getPurchaseOrders(Integer clientId) {
        InvoiceDTO d = new InvoiceDTO();

        d.setPocs(this.pocRepository.findByStatusAndClientId("Ejecucion", clientId));

        return d;
    }

    public InvoicesDTO getInvoice(String client, Integer currencyId,
                                  String purchaseOrderNumber,
                                  Date startDate, Date endDate,
                                  Integer number,
                                  Double total,
                                  String state, String estadoHacienda,
                                  Boolean isLoadedCombos,
                                  Integer pageNumber,
                                  Integer pageSize, String sortDirection,
                                  String sortField) {

        InvoicesDTO d = new InvoicesDTO();

        if (currencyId != 0 && !isNumberEmpty(number) && !isNumberEmpty(total) ) {
            d.setPage(this.repository.getFilterPageableByAll(
                    number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda,total,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByAll(number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda, total));
            d.setTotals(this.repository.getSumTotalByAll(number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda, total));


        } else if (currencyId == 0 && isNumberEmpty(number) && isNumberEmpty(total)) { //0 0 0 mas corto
            d.setPage(this.repository.getFilterBySpecial(
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countBySpecial(startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda));

            d.setTotals(this.repository.getSumTotalBySpecial(startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda));


        } else if (currencyId != 0 && !isNumberEmpty(number) &&  isNumberEmpty(total)) { // 1 1 0
            d.setPage(this.repository.getFilterPageableByAll(
                    number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByAll(number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda));

            d.setTotals(this.repository.getSumTotalByAll(number, startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda));


        } else if (currencyId != 0 && isNumberEmpty(number) && isNumberEmpty(total)) { // currency=1 0 0
            d.setPage(this.repository.getFilterPageableByCurrency(
                    startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByCurrency(startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda));

            d.setTotals(this.repository.getSumTotalByCurrency(startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda));
        } else if (currencyId == 0 && !isNumberEmpty(number) && isNumberEmpty(total)) { // 0 number=1 0
            d.setPage(this.repository.getFilterPageableByInvoiceNumber(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByInvoiceNumber(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda));

            d.setTotals(this.repository.getSumTotalByInvoiceNumber(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda));
        } else if (currencyId == 0 && isNumberEmpty(number) && !isNumberEmpty(total)) { // 0 0 total=1
            d.setPage(this.repository.getFilterPageableByTotal(
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByTotal(
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total));

            d.setTotals(this.repository.getSumTotalByTotal(startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total));
        } else if (currencyId != 0 && isNumberEmpty(number) && !isNumberEmpty(total)) { // currency=1 0 total=1
            d.setPage(this.repository.getFilterPageableByCurrencyAndTotal(
                    startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda, total,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByCurrencyAndTotal(startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda, total));

            d.setTotals(this.repository.getSumTotalByCurrencyAndTotal(startDate, endDate, client,
                    purchaseOrderNumber, currencyId, state, estadoHacienda, total));
        } else if (currencyId == 0 && !isNumberEmpty(number) && !isNumberEmpty(total)) { // 0 number=1 total=1
            d.setPage(this.repository.getFilterPageableByInvoiceNumberAndTotal(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.repository.countByInvoiceNumberAndTotal(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total));

            d.setTotals(this.repository.getSumTotalByInvoiceNumberAndTotal(number,
                    startDate, endDate, client,
                    purchaseOrderNumber, state, estadoHacienda, total));
        }

        if (isLoadedCombos) {

            d.setClients(this.clientRepository.findClientsActive());
            d.setCurrencies(this.currencyRepository.findAll());

            List<String> states = new ArrayList<String>();
            states.add("Edicion");
            states.add("Pendiente");
            states.add("Vencida");
            states.add("Pagada");
            states.add("Anulada");
            d.setStates(states);

            List<String> types = new ArrayList<String>();
            types.add("Servicio");
            types.add("Producto");

            d.setTypes(types);

            List<String> haciendaEstados = new ArrayList<String>();
            haciendaEstados.add("pendiente");
            haciendaEstados.add("rechazado");
            haciendaEstados.add("aceptado");
            haciendaEstados.add("no_enviada");
            d.setStatesHacieda(haciendaEstados);

        }


        if (d.getTotal()!=null && d.getTotal() > 0) {
            d.setPagesTotal(d.getTotal() / pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public InvoiceDTO getNewInvoiceByPoc(Integer pocId) {
        InvoiceDTO d = new InvoiceDTO();
        PurchaseOrderClient poc = this.pocRepository.getById(pocId);
        List<PurchaseOrderClient> pocl = new ArrayList<>();
        pocl.add(poc);
        d.setPocs(pocl);
        List<Client> cl = new ArrayList<>();
        cl.add(poc.getClient());


        d.setClients(cl);
        d.setCostCenters(new ArrayList<>());

        d.setCurrencies(currencyRepository.findAll());

        List<String> states = new ArrayList<String>();
        states.add("Edicion");
        states.add("Pendiente");
        states.add("Vencida");
        states.add("Pagada");
        states.add("Anulada");
        d.setStatus(states);

//        d.setClients(this.clientRepository.findClientsActive());
//        d.setCurrencies(this.currencyRepository.findAll());
        d.setActivities(this.economicActivityRepository.findAll());

        List<String> types = new ArrayList<String>();
        types.add("Servicio");
        types.add("Producto");
        d.setTypes(types);

        List<String> haciendaEstados = new ArrayList<String>();
        haciendaEstados.add("pendiente");
        haciendaEstados.add("rechazado");
        haciendaEstados.add("aceptado");
        haciendaEstados.add("no_enviada");
        d.setStatesHacieda(haciendaEstados);


        try {
            d.setSessionPos(this.posService.getSessionPosAbierta());

            if (d.getSessionPos()!=null) {

                d.setMedioPagos(this.medioPagoRepository.findAllMedioPagosActivo());

                d.setEmpresaTiqueteDTO(this.setEmpresaTiqueteInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    public InvoiceDTO getInvoice() {
        InvoiceDTO d = new InvoiceDTO();
//        d.setClients(this.clientRepository.findClientsActive());
        d.setCostCenters(new ArrayList<>());

//        d.setCurrencies(currencyRepository.findAll());

        List<String> states = new ArrayList<String>();
        states.add("Edicion");
        states.add("Pendiente");
        states.add("Vencida");
        states.add("Pagada");
        states.add("Anulada");
        d.setStatus(states);

        d.setClients(this.clientRepository.findClientsActive());
        d.setCurrencies(this.currencyRepository.findAll());
        d.setActivities(this.economicActivityRepository.findAll());

        List<String> types = new ArrayList<String>();
        types.add("Servicio");
        types.add("Producto");
        d.setTypes(types);

        List<String> haciendaEstados = new ArrayList<String>();
        haciendaEstados.add("pendiente");
        haciendaEstados.add("rechazado");
        haciendaEstados.add("aceptado");
        haciendaEstados.add("no_enviada");
        d.setStatesHacieda(haciendaEstados);


        try {
            d.setSessionPos(this.posService.getSessionPosAbierta());

            if (d.getSessionPos()!=null) {

                d.setMedioPagos(this.medioPagoRepository.findAllMedioPagosActivo());

                d.setEmpresaTiqueteDTO(this.setEmpresaTiqueteInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return d;
    }

    public EmpresaTiqueteDTO setEmpresaTiqueteInfo() throws Exception{


        EmpresaTiqueteDTO infoEmpresa = new EmpresaTiqueteDTO();
        GeneralParameter generalParameterNombreEmpresa =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        GeneralParameter generalParameterCedula = this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_CEDULA_EMPRESA);
        GeneralParameter generalParameterTelefono =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_PHONE_EMPRESA);
        GeneralParameter generalParameterCorreo =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_EMAIL_EMPRESA);
        GeneralParameter generalParameterDireccion =  this.generalParameterRepository.findByCode(POS_SHORT_ADDRESS);
        GeneralParameter generalParameterTiqueteBottom =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM);
        GeneralParameter generalParameterTiqueteBottom1 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM1);
        GeneralParameter generalParameterTiqueteBottom2 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM2);
        GeneralParameter generalParameterTiqueteBottom3 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM3);
//       GeneralParameter generalParameterDireccion =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_ADDRESS_EMPRESA);

        if (infoEmpresa  == null) {
            throw new GeneralInventoryException("Parametro  info de empresa no encontrado: " + INVOICE_PARAMETER_DATOS_EMPRESA);
        }

        if (generalParameterNombreEmpresa  == null) {
            throw new GeneralInventoryException("Parametro nombre empresa no encontrado: " + INVOICE_PARAMETER_NOMBRE_EMPRESA);
        }

        if (generalParameterCedula  == null) {
            throw new GeneralInventoryException("Parametro cedula empresa no encontrado: " + INVOICE_PARAMETER_CEDULA_EMPRESA);
        }

        if (generalParameterTelefono  == null) {
            throw new GeneralInventoryException("Parametro telefono empresa no encontrado: " + INVOICE_PARAMETER_PHONE_EMPRESA);
        }

        if (generalParameterCorreo  == null) {
            throw new GeneralInventoryException("Parametro correo empresa no encontrado: " + INVOICE_PARAMETER_EMAIL_EMPRESA);
        }

        if (generalParameterDireccion  == null) {
            throw new GeneralInventoryException("Parametro direccion empresa no encontrado: " + INVOICE_PARAMETER_ADDRESS_EMPRESA);
        }

        if (this.userDetailsService.getCurrentLoggedUser() == null) {
            throw new GeneralInventoryException("Error POS: No se encuentra un usuario para asignar al tiquete de caja!");
        }


        //Asignar los valores a la empresa
        infoEmpresa.setEmpresaNombre(generalParameterNombreEmpresa.getVal());
        infoEmpresa.setTiqueteBottom(generalParameterTiqueteBottom.getVal());

        infoEmpresa.setTiqueteBottom1(generalParameterTiqueteBottom1.getVal());
        infoEmpresa.setTiqueteBottom2(generalParameterTiqueteBottom2.getVal());
        infoEmpresa.setTiqueteBottom3(generalParameterTiqueteBottom3.getVal());



        infoEmpresa.setCedula(generalParameterCedula.getVal());
        infoEmpresa.setTelefono(generalParameterTelefono.getVal());
        infoEmpresa.setCorreo(generalParameterCorreo.getVal());
        infoEmpresa.setDireccionCorta(generalParameterDireccion.getName());
        infoEmpresa.setNombreCajero(this.userDetailsService.getCurrentLoggedUser().getDisplayName());

        return infoEmpresa;
    }


    public Invoice save(Invoice c) throws Exception{
        Invoice result = null;
        if (c.getId() == null) {
            c.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setSalidaBodega(false);
            c.setUserId(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
            c.setHacienda(false);
            c.setEstadoHacienda(StringHelper.NO_ENVIADA);
            c.setNumber(this.generalParameterService.getNextInvoiceNumber());
            c.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setSalidaBodega(false);
            result = this.repository.save(c);
            this.pocService.updateBilledDetails(c.getPoc().getDetails());
        } else {
            result = this.repository.saveAndFlush(c);
            if (c.getPoc() != null) {
                //this.pocRepository.saveAndFlush(c.getPoc());// TESTING SAVING OPC
                this.pocService.updateBilledDetails(c.getPoc().getDetails());// TESTING SAVING OPC
            }
        }
        this.generarTransaccionPendiente(result);
        //this.pocService.save(c.getPoc());
        return result;
    }


    public void actualizarMontosASessionPos(SessionPos sessionPos, List<InvoiceMedioPago> detalle, Currency invoiceCurrency, ExchangeRate er) throws Exception{
        //Todo: faltan medios de pago en los acumulados de la session pos
        for (InvoiceMedioPago elem : detalle){
            switch (elem.getMedioPago().getNombre()) {
                case MEDIO_PAGO_EFECTIVO: {
                    if (invoiceCurrency.getDefault()==true) {
                        sessionPos.setMontoEfectivoCierre(sessionPos.getMontoEfectivoCierre() + elem.getMontoPago());
                    } else {
                        sessionPos.setMontoEfectivoCierre(sessionPos.getMontoEfectivoCierre() + BillHelper.convertToDefaultCurrency(er,elem.getMontoPago()));
                    }
                    break;
                } case MEDIO_PAGO_SINPE: {

                    if (invoiceCurrency.getDefault()==true) {
                        sessionPos.setMontoSinpeCierre(sessionPos.getMontoSinpeCierre() + elem.getMontoPago());
                    } else {
                        sessionPos.setMontoSinpeCierre(sessionPos.getMontoSinpeCierre() + BillHelper.convertToDefaultCurrency(er,elem.getMontoPago()));
                    }
                    break;
                } case MEDIO_PAGO_TARJETA: {
                    if (invoiceCurrency.getDefault()==true) {
                        sessionPos.setMontoTarjetaCierre(sessionPos.getMontoTarjetaCierre() + elem.getMontoPago());

                    } else {
                        sessionPos.setMontoTarjetaCierre(sessionPos.getMontoTarjetaCierre() + BillHelper.convertToDefaultCurrency(er,elem.getMontoPago()));
                    }

                    break;
                } case MEDIO_PAGO_TRANSFERENCIA: {
                    if (invoiceCurrency.getDefault()==true) {
                        sessionPos.setMontoTransferenciaBancaria(sessionPos.getMontoTransferenciaBancaria() + elem.getMontoPago());

                    } else {
                        sessionPos.setMontoTransferenciaBancaria(sessionPos.getMontoTransferenciaBancaria() + BillHelper.convertToDefaultCurrency(er,elem.getMontoPago()));
                    }

                    break;
                }
            }

        }

        this.sessionPosRepository.save(sessionPos);

    }

    public boolean ventaPosFinalizada(Invoice invoice){

        Boolean puedeActualizar = false;

        Invoice oldInvoice = this.repository.getById(invoice.getId());
        if  (oldInvoice!= null) {

           if (oldInvoice!= null &&
                   (
                   (oldInvoice.getStatus().equals(INVOICE_STATUS_EDICION) && (invoice.getStatus().equals(INVOICE_STATUS_PAGADA))) ||
                           (oldInvoice.getStatus().equals(INVOICE_STATUS_PENDIENTE) && (invoice.getStatus().equals(INVOICE_STATUS_PAGADA)))
                   )
           ){
                puedeActualizar = true;
            }

        } else {
             if (oldInvoice == null  && invoice.getStatus().equals(INVOICE_STATUS_PAGADA)) {
                 puedeActualizar = true;
            }
        }
        return puedeActualizar;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void asignarClienteGenericoTiquetePos(Invoice invoice) throws Exception{

        GeneralParameter generalParameterClienteGenerico;

        generalParameterClienteGenerico = this.generalParameterService.getByCode(INVOICE_DEFAULT_CLIENT);

        if (generalParameterClienteGenerico == null) {
            throw new GeneralInventoryException("Parametro no encontrado para cliente default: " +INVOICE_DEFAULT_CLIENT);
        }

        Client client;
        client = this.clientRepository.findByClientId(generalParameterClienteGenerico.getIntVal());

        if (client == null){
            throw new GeneralInventoryException("Cliente generico  no encontrado usando parametro: " +INVOICE_DEFAULT_CLIENT);

        }

        if (invoice.getId() == null) {
           invoice.setClient(client);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void asignarCentroCostoGenericoTiquetePos(Invoice invoice) throws Exception{

        GeneralParameter generalParameterCentroCostoGenerico;

        generalParameterCentroCostoGenerico = this.generalParameterService.getByCode(INVOICE_DEFAULT_COST_CENTER);

        if (generalParameterCentroCostoGenerico == null) {
            throw new GeneralInventoryException("Parametro no encontrado para centro costo default: " +INVOICE_DEFAULT_COST_CENTER);
        }

        CostCenter costCenter;
        costCenter = this.costCenterRepository.getById(generalParameterCentroCostoGenerico.getIntVal());

        if (costCenter == null){
            throw new GeneralInventoryException("Centro Costo generico  no encontrado usando parametro: " + INVOICE_DEFAULT_COST_CENTER);

        }

        for (InvoiceDetail detail : invoice.getDetails()){
            if (detail.getCostCenter() == null){
                detail.setCostCenter(costCenter);
            }
        }

    }

   @Transactional(rollbackFor = {Exception.class})
   public void crearTransaccionPendiente(Invoice i){
        TransaccionPendiente transaccionPendiente = new TransaccionPendiente();
        transaccionPendiente.setInvoice(i);
//        transaccionPendiente.setTerminal((i.getSessionPos() == null ? null : i.getSessionPos().getTerminalUsuario().getTerminal()));
        transaccionPendiente.setStatus(TRANSACCION_PENDIENTE_ESTADO_ESPERA);
        transaccionPendiente.setFechaIngreso(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        transaccionPendiente.setTipo(i.getTipoTransaccion());
        transaccionPendiente.setFechaUltimaActualizacion(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        transaccionPendiente.setIngresadaPor(this.userDetailsService.getCurrentLoggedUser());
        transaccionPendiente.setTipoEnvio(TRANSACCION_PENDIENTE_TTPO_ENVIO_ASINCRONO);
        this.transaccionPendienteRepository.save(transaccionPendiente);
    }


    @Transactional(rollbackFor = {Exception.class})
    public void generarTransaccionPendiente(Invoice invoice) {

        GeneralParameter generalParameterRegimenSimplificado  = this.generalParameterService.getByCode(INVOICE_REGIMEN_SIMPLIFICADO_FLAG);

        if (generalParameterRegimenSimplificado == null) {
            throw new GeneralInventoryException("Error POS: No se pudo obtener el parametro para validar si es regimen simplificado");
        }

        if (!generalParameterRegimenSimplificado.getVal().equals("1") && invoice.getTipoTransaccion().equals(INVOICE_TIPO_TRANSACCION_TIQUETE)){

            if (invoice.getStatus().equals(INVOICE_STATUS_PAGADA)) {
                //ingresar el invoice a la tabla de transacciones pendientes por ser un tiquete
                crearTransaccionPendiente(invoice);
            }

        } else if (invoice.getTipoTransaccion().equals(INVOICE_TIPO_TRANSACCION_FE)) {
          crearTransaccionPendiente(invoice);
        }


    }

   /* @Transactional(rollbackFor = {Exception.class})
    public  Invoice savePosTransaction(Invoice invoice) throws Exception{
        Boolean salidaInventarioExitosa = false;
        if (invoice == null) {
            throw new GeneralInventoryException("Error POS: No se encuentra la factura!");
        }

        if (invoice.getTipoTransaccion() == null) {
            throw new GeneralInventoryException("Error Pos: Debe definir si es una factura o una venta pos!");
        }

        if (invoice.getSessionPos() == null) {
            throw new GeneralInventoryException("Sessión pos no valida!");
        }

        if (invoice.getId() == null){
            invoice.setSalidaBodega(false);
            invoice.setHacienda(false);
            invoice.setEstadoHacienda(StringHelper.NO_ENVIADA);
        }

        asignarClienteGenericoTiquetePos(invoice);
        asignarCentroCostoGenericoTiquetePos(invoice);
        if (ventaPosFinalizada(invoice)){
            actualizarMontosASessionPos(invoice.getSessionPos(), invoice.getMedioPagoDetails());
            salidaInventarioExitosa = this.bodegaManagerService.salidaBodegaInvoiceAsinc(invoice, null);
            if (!salidaInventarioExitosa) {
                throw new GeneralInventoryException("Error POS: Hubo un problema al hacer la salida de bodega el inventario");
            }
        }

        return this.repository.save(invoice);

    }*/

    public void posAuditoriaCreacion(Invoice c){
        c.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUserId(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
    }

    public void posAuditoriaModificacion(Invoice c){
        c.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized  Invoice savePosTransaction(Invoice invoice) throws Exception{

        Boolean salidaInventarioExitosa = false;

        if (invoice == null) {
            throw new GeneralInventoryException("No se encuentra la factura!");
        }

        if (invoice.getTipoTransaccion() == null) {
            throw new GeneralInventoryException("Debe definir si es una factura o una venta pos!");
        }


        if (invoice.getSessionPos() == null) {
            throw new GeneralInventoryException("Sessión pos no valida!");
        }
        synchronized(invoice) {
            if (invoice.getId() == null) {
                if (invoice.getTipoTransaccion().equals(INVOICE_TIPO_TRANSACCION_TIQUETE)) {
                    invoice.setConsecutivo(globalDataManager.getTiqueteElectronicoNext().toString());
                } else {
                    invoice.setConsecutivo(globalDataManager.getFacturaElectronicaNext().toString());
                }
                String tipo = invoice.getTipoTransaccion() == null || invoice.getTipoTransaccion().equals("FE") ? "01" : "04";

                //TODO: Obtener cedula
                //            BillHelper.generarClave(invoice, tipo, "3101629356");
                GeneralParameter generalParameterCedula = this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_CEDULA_EMPRESA);

                BillHelper.generarClave(invoice, tipo, generalParameterCedula.getVal());

                invoice.setHacienda(false);
                invoice.setSalidaBodega(false);
                invoice.setEstadoHacienda(StringHelper.NO_ENVIADA);

            }
            if (invoice.getTipoTransaccion().equals(INVOICE_TIPO_TRANSACCION_TIQUETE) && invoice.getClient() == null) {
                asignarClienteGenericoTiquetePos(invoice);
            }
            asignarCentroCostoGenericoTiquetePos(invoice);
            this.posAuditoriaModificacion(invoice);

            if (ventaPosFinalizada(invoice)) {
                updateSessionPosMontoNeto(invoice);
                invoice = this.savePos(invoice);
                generarTransaccionPendiente(invoice);
//                updateSessionPosMontoNeto(invoice);

                actualizarMontosASessionPos(invoice.getSessionPos(), invoice.getMedioPagoDetails(), invoice.getCurrency(), invoice.getExchangeRate());//IF y agregar to default
                this.bodegaManagerService.salidaBodegaInvoiceAsinc(invoice, null);
                this.posAuditoriaModificacion(invoice);

            }
        }

        return this.savePos(invoice);
//        Invoice result = this.savePos(invoice);
//
//        return result;

    }

    private void updateSessionPosMontoNeto(Invoice invoice) {
        Double montoTotal = invoice.getTotal();
        if (invoice.getCurrency().getDefault()==false) {
            if (invoice.getExchangeRate()!=null) {
                montoTotal = BillHelper.convertToDefaultCurrency(invoice.getExchangeRate(), invoice.getTotal());
            } else {
                throw new NotFoundException("El tipo de cambio esta vacio y debe haber sido inicializado");
            }
        }
        SessionPos p = this.sessionPosRepository.findSessionById(invoice.getSessionPos().getId());
        if (p==null) {
            throw new NotFoundException("Session no encontrada");
        }
        invoice.setSessionPos(p);
        if (invoice.getSessionPos().getMontoNeto()==null) {
            invoice.getSessionPos().setMontoNeto(0d);
        }

        montoTotal = invoice.getSessionPos().getMontoNeto() + montoTotal;
        invoice.getSessionPos().setMontoNeto(montoTotal);
    }

    private Invoice savePos(Invoice c) {
        if (c.getNumber() == null) {
            c.setNumber(this.generalParameterService.getNextInvoiceNumber());
        }
        this.posAuditoriaCreacion(c);
        this.posAuditoriaModificacion(c);
        return this.repository.save(c);
    }



    private void congelarInventarioBodegaFactura(Invoice invoice, String estadoAnterior) throws Exception {


        if (estadoAnterior.equals(INVOICE_STATUS_EDICION)
                && invoice.getStatus().equals(INVOICE_STATUS_PENDIENTE)
                && invoice.getSessionPos()==null) {

            this.bodegaManagerService.congelarInventarioBodegaInvoice(invoice);

        }

    }
    private void validateCodigoCabys(Invoice invoice, String estadoAnterior) throws Exception {


        if (estadoAnterior.equals(INVOICE_STATUS_EDICION) && invoice.getStatus().equals(INVOICE_STATUS_PENDIENTE)) {

            this.revisarCabysFaltantes(invoice);

        }

    }

    public void revisarCabysFaltantes(Invoice p) throws Exception{
        if (p!= null){
            if (p.getDetails()!= null) {
                for (InvoiceDetail d : p.getDetails()){
                    if (d.getType().equals(TIPO_DETALLE_PRODUCTO_PURCHASE_ORDER_CLIENT)) {
                        if (d.getInventario().getProducto().getCodigoCabys() == null ||  d.getInventario().getProducto().getCodigoCabys().equals("")) {
                            throw new GeneralInventoryException("Cabys NO asignado, por lo que no se puede cambiar el estado de la factura. " +
                                    "Debe asignarle codigo cabys al producto " + d.getInventario().getProducto().getDescripcionEspanol() );
                        }
                    }
                }
            }
        }
    }
    private boolean permitirModificarFacturaSegunEstadoAnterior(Invoice invoice) throws Exception {

        boolean permitirModificar = false;
        Invoice oldInvoice;

        oldInvoice = this.repository.getById(invoice.getId());

        if ((oldInvoice == null || !oldInvoice.getStatus().equals(INVOICE_STATUS_PENDIENTE))) {
            permitirModificar = true;
        } else if (oldInvoice.getStatus().equals(INVOICE_STATUS_PENDIENTE)) {
            permitirModificar = false;
        }
        return permitirModificar;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction == null || direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, byField);
    }

    public Boolean saveInvoice(Integer id, String consecutivo, String clave, String path) throws Exception {
        GeneralParameter gp = this.generalParameterService.getByCode("EMPRESA_INFO");
        Optional<Invoice> oi = this.repository.findById(id);
        if (oi == null) {
            return false;
        }
        Invoice invoice = oi.get();


        GeneralParameter leyenda = this.generalParameterService.getByCode("EINV_LEYENDA");
        GeneralParameter nombreEmpresaParameter = this.generalParameterService.getGeneralParameterByNameAndCode(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        Map<String, Object> e = new HashMap<String, Object>();
        e.put("leyenda", leyenda.getDescription() != null ? leyenda.getDescription() : "");
        if (clave != null)
            e.put("clave", clave);
        else {
            e.put("clave", "");

        }
        if (consecutivo != null) {
//            e.put("consecutivo", clave);
            e.put("consecutive", consecutivo);
        } else {
            e.put("consecutive", "");

        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("gp", gp);
        data.put("invoice", invoice);
        data.put("ebill", e);
        data.put("website", serverWebsite);
        data.put("nombreEmpresa", nombreEmpresaParameter);
        try {
            BillViewBuilder pvb = new BillViewBuilder();
            pvb.getPdf(data, path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return true;
    }

    public Path getFacturaPdfPath(Integer id) throws Exception{
        Optional<Invoice> invoiceOpt = this.repository.findById(id);
        String pdfFile = null;
        Path fileStorageLocation = Paths.get(this.billConfigService.getBasedPath());
        Path filePath = null;
        if (invoiceOpt == null) {
            return null;
        }
        Invoice factura = invoiceOpt.get();
        pdfFile = this.billConfigService.getPdfFileAndFullPath(factura.getNumber());

        saveInvoice(factura.getId(), factura.getConsecutivo(), factura.getClave(), pdfFile);
        filePath = fileStorageLocation.resolve(factura.getNumber() + ".pdf").normalize();

        return filePath;


    }

    public Path getFacturaBasePdfPath(Integer id) throws Exception {
        Optional<Invoice> invoiceOpt = this.repository.findById(id);
        String pdfFile = null;
        Path fileStorageLocation = Paths.get(this.billConfigService.getBasedPath());
        Path filePath = null;
        if (invoiceOpt == null) {
            return null;
        }
        Invoice factura = invoiceOpt.get();
        pdfFile = this.billConfigService.getBasePdfFile(factura.getNumber());
//       pdfFile = this.billConfigService.getPdfFileAndFullPath(factura.getNumber());

        saveInvoice(factura.getId(), factura.getConsecutivo(), factura.getClave(), pdfFile);
        filePath = fileStorageLocation.resolve(factura.getNumber() + ".pdf").normalize();

        return filePath;


    }

    //    public Resource loadFileAsResource(Integer id, String fileName, String path) throws Exception {
    public Resource loadFileAsResource(Integer id, Path filePath) throws Exception {
        try {

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + id);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + id, ex);
        }
    }

    private InvoiceNotaCredito createCreditNoteDB(Invoice iv) {
        InvoiceNotaCredito notaCredito;
        notaCredito = new InvoiceNotaCredito();
        BeanUtils.copyProperties(iv, notaCredito);
        notaCredito.setInvoice(iv);
        InvoiceNotaCreditoDetail incd = null;
        notaCredito.setDetails(new List<InvoiceNotaCreditoDetail>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<InvoiceNotaCreditoDetail> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(InvoiceNotaCreditoDetail invoiceNotaCreditoDetail) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends InvoiceNotaCreditoDetail> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends InvoiceNotaCreditoDetail> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public InvoiceNotaCreditoDetail get(int index) {
                return null;
            }

            @Override
            public InvoiceNotaCreditoDetail set(int index, InvoiceNotaCreditoDetail element) {
                return null;
            }

            @Override
            public void add(int index, InvoiceNotaCreditoDetail element) {

            }

            @Override
            public InvoiceNotaCreditoDetail remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<InvoiceNotaCreditoDetail> listIterator() {
                return null;
            }

            @Override
            public ListIterator<InvoiceNotaCreditoDetail> listIterator(int index) {
                return null;
            }

            @Override
            public List<InvoiceNotaCreditoDetail> subList(int fromIndex, int toIndex) {
                return null;
            }
        });

        java.util.Date today = new java.util.Date();
        notaCredito.setDate(new java.sql.Date(today.getTime()));
        notaCredito.setStatus("Emitida");


        for (InvoiceDetail vd : iv.getDetails()) {
            incd = new InvoiceNotaCreditoDetail(vd);
            incd.setInvoiceNotaCredito(notaCredito);
            //BeanUtils.copyProperties(incd,vd);
            //                incd.setCreateAt(new Date());
            // invoiceNotaCreditoDetailRepository.add(incd);
            notaCredito.getDetails().add(incd);

        }
        notaCredito = invoiceNotaCreditoRepository.save(notaCredito);
        return notaCredito;
    }

    public ResultFe sendToHaciendaNotaCredito(Integer id) throws Exception {
        OptionSet options;

        Invoice iv = get(id);
        this.revisarCabysFaltantes(iv);
        InvoiceNotaCredito notaCredito = null;
        ResultFe rfe = new ResultFe();
        if (!BillHelper.isDataClientOk(iv.getClient(), rfe)) {
            return rfe;
        }

        try {
            notaCredito = this.invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(id);

        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }

        List<EconomicActivity> lat = this.economicActivityRepository.findAll();
        if (iv != null && iv.getEconomicActivity() == null) {


            if (lat != null && lat.size() > 0) {

                iv.setEconomicActivity(lat.get(0));
            }


        }

        if (notaCredito != null && notaCredito.getEconomicActivity() == null) {
            notaCredito.setEconomicActivity(lat.get(0));
        }

        boolean ret = false;
        try {

            GeneralParameter gp = this.generalParameterService.get(4);
            List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
            InvoiceNotaCreditoV43Mapper ivm = new InvoiceNotaCreditoV43Mapper(emisor, gp);

            if (notaCredito == null) {
                notaCredito = createCreditNoteDB(iv);
            }

            NotaCreditoElectronica fe = ivm.mapFacturaElectronica(notaCredito);
            fe.setNumeroConsecutivoFe(globalDataManager.getNotaCreditoElectronicaNext());
            fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());

            BillHelper.generarClave(fe);
            notaCredito.setClave(fe.getClave());
            notaCredito.setConsecutivo(fe.getNumeroConsecutivo());
            notaCredito.setEstadoHacienda(BillHelper.ENVIANDO);

            Optional<BillSenderDetail> obs = billSenderDetailRepository.findById(iv.getBillSenderId());
            BillSenderDetail bsd = obs.get();
            fe.getInformacionReferencia().get(0).setNumero(bsd.getConsecutivo());

            BillSenderDetail pefd = this.facturaElectronicaService.createProcesoEmisionFacturaDetalle(id, BillHelper.TIPO_NOTA_CREDITO_FE);
            billConfigService.initBasePath(fe.getNumeroConsecutivo());

            String fileName = getXmlFileName(fe.getClave());
            this.fem.writeXMLFile(fe, billConfigService.getCompletePath() + fileName);
            options = billConfigService.initOptionsValues(fileName);
            pefd.setClave(fe.getClave());
            pefd.setStatus(BillHelper.NO_ENVIADA);
            pefd.setConsecutivo(fe.getNumeroConsecutivo());
            pefd.setPath(billConfigService.getCompletePath());
            if (!this.facturaElectronicaService.firmarFactura(options, BillHelper.NOTA_CREDITO_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_NOTA_CREDITO_FE, pefd)) {
                rfe.setResultStr("Hubo un error al firmar la factura");
                rfe.setResult(-1);
                return rfe;
            }

            String pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            this.saveCreditNote(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);

            rfe = facturaElectronicaService.emitNotaCreditoElectronica(fe, notaCredito.getId(), fe.getCorreo(), fe.getReceptor().getNombre());
            this.invoiceNotaCreditoRepository.save(notaCredito);

            if (rfe != null && rfe.getResult() == 1 || rfe.getResult() == -1) {
                iv.setStatus("Anulada");
                update(iv);
            }
            ret = rfe.getResult() == 1;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        } catch (Exception e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        }

        iv.setHacienda(ret);


        return rfe;
    }

    public ResultFe sendExportacionToHacienda(Integer id, Boolean esReenvio) throws Exception {
        Invoice iv = get(id);
        this.revisarCabysFaltantes(iv);
        String claveActual = iv.getClave();
        OptionSet options;

        ResultFe rfe = new ResultFe();
        if (!BillHelper.isDataClientOk(iv.getClient(), rfe)) {
            return rfe;
        }
        boolean ret = false;
        try {

            GeneralParameter gp = this.generalParameterService.get(4);
            List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
//            InvoiceMapper ivm = new InvoiceMapper(emisor, gp);
            FacturaElectronicaExportacionV43Mapper ivm = new FacturaElectronicaExportacionV43Mapper(emisor, gp);

            FacturaElectronicaExportacion fe = ivm.mapFacturaElectronica(iv);
            fe.setNumeroConsecutivoFe(globalDataManager.getFacturaElectronicaExportacionNext());
            fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());

            BillHelper.generarClave(fe);
            iv.setClave(fe.getClave());
            iv.setConsecutivo(fe.getNumeroConsecutivo());
            iv.setEstadoHacienda(BillHelper.ENVIANDO);

            BillSenderDetail pefd = this.facturaElectronicaService.createProcesoEmisionFacturaDetalle(id, BillHelper.TIPO_FACTURA_FE);
            billConfigService.initBasePath(fe.getNumeroConsecutivo());

            //RENVIO
            initReenvioInfo(esReenvio, fe, id, claveActual, ivm);

            String fileName = getXmlFileName(fe.getClave());
            this.fem.writeXMLFile(fe, billConfigService.getCompletePath() + fileName);
            options = billConfigService.initOptionsValues(fileName);
            pefd.setClave(fe.getClave());
            pefd.setStatus(BillHelper.NO_ENVIADA);
            pefd.setConsecutivo(fe.getNumeroConsecutivo());
            pefd.setPath(billConfigService.getCompletePath());
            if (!this.facturaElectronicaService.firmarFactura(options, BillHelper.FACTURA_ELECTRONICA_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_FACTURA_FEE, pefd)) {
                rfe.setResultStr("Hubo un error al firmar la factura");
                rfe.setResult(-1);
                return rfe;
            }
            String pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            this.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);

            rfe = facturaElectronicaService.emitFacturaElectronica(fe);

            update(iv);


            ret = rfe.getResult() == 1;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        } catch (Exception e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        }
        iv.setHacienda(ret);
        return rfe;
    }

    private void initReenvioInfo(Boolean esReenvio, FacturaElectronicaExportacion fe, Integer id, String oldClave, FacturaElectronicaExportacionV43Mapper mapper) {
        if (!esReenvio) {
            return;
        }

        BillSenderDetail bsd = null;
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByClave(oldClave);
        if (l != null && l.size() > 0) {
            bsd = l.get(0);
            try {
                FacturaElectronicaExportacion feOld = feu.readXMLFileFEE(bsd.getPath() + bsd.getClave() + ".xml");
                FacturaElectronicaExportacion.InformacionReferencia ir = mapper.getInformacionReferenciaSustituyePorRechazoFe(feOld);
                fe.getInformacionReferencia().add(ir);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean saveCreditNote(Integer id, String consecutivo, String clave, String path) throws Exception {
        GeneralParameter gp = this.generalParameterService.getByCode("EMPRESA_INFO");
        GeneralParameter empNombreParameter = this.generalParameterService.getGeneralParameterByNameAndCode(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        Optional<InvoiceNotaCredito> o = this.invoiceNotaCreditoRepository.findById(id);
        InvoiceNotaCredito invoiceNotaCredito = o.get();

        GeneralParameter leyenda = this.generalParameterService.getByCode("EINV_LEYENDA");
        Map<String, Object> e = new HashMap<String, Object>();
        e.put("leyenda", leyenda.getDescription() != null ? leyenda.getDescription() : "");
        e.put("clave", clave);
        e.put("consecutive", consecutivo);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("gp", gp);
        data.put("noteCredit", invoiceNotaCredito);
        data.put("ebill", e);
        data.put("empNombre", empNombreParameter);
        try {
            NoteCeditViewBuilder pvb = new NoteCeditViewBuilder();
            pvb.getPdf(data, path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return true;
    }

    public Invoice get(Integer id) {
        Invoice iv;
        Optional<Invoice> oi = this.repository.findById(id);
        if (oi == null) {
            throw new RuntimeException("Not found");
        }
        iv = oi.get();
        return iv;
    }

    public ResultFe sendToHacienda(Integer id, Boolean esReenvio) throws Exception {

        ResultFe rfe = new ResultFe();
        Invoice iv = null;
        Optional<Invoice> oi = this.repository.findById(id);
        if (oi == null) {
            return rfe;
        }
        iv = oi.get();
        this.revisarCabysFaltantes(iv);

        String claveActual = iv.getClave();
        OptionSet options;


        if (!BillHelper.isDataClientOk(iv.getClient(), rfe)) {
            return rfe;
        }
        boolean ret = false;
        try {

            GeneralParameter gp = this.generalParameterService.get(4);
            List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
//            InvoiceMapper ivm = new InvoiceMapper(emisor, gp);
            InvoiceV43Mapper ivm = new InvoiceV43Mapper(emisor, gp);

            FacturaElectronica fe = ivm.mapFacturaElectronica(iv);
            fe.setNumeroConsecutivoFe(globalDataManager.getFacturaElectronicaNext());
            fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());

            BillHelper.generarClave(fe);
            iv.setClave(fe.getClave());
            iv.setConsecutivo(fe.getNumeroConsecutivo());
            iv.setEstadoHacienda(BillHelper.ENVIANDO);

            BillSenderDetail pefd = this.facturaElectronicaService.createProcesoEmisionFacturaDetalle(id, BillHelper.TIPO_FACTURA_FE);
            billConfigService.initBasePath(fe.getNumeroConsecutivo());

            //RENVIO
            initReenvioInfo(esReenvio, fe, id, claveActual, ivm);

            String fileName = getXmlFileName(fe.getClave());

            this.fem.writeXMLFile(fe, billConfigService.getCompletePath() + fileName);

            options = billConfigService.initOptionsValues(fileName);

            pefd.setClave(fe.getClave());
            pefd.setStatus(BillHelper.NO_ENVIADA);
            pefd.setConsecutivo(fe.getNumeroConsecutivo());
            pefd.setPath(billConfigService.getCompletePath());
            if (!this.facturaElectronicaService.firmarFactura(options, BillHelper.FACTURA_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_FACTURA_FE, pefd)) {
                rfe.setResultStr("Hubo un error al firmar la factura");
                rfe.setResult(-1);
                return rfe;
            }
            String pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            this.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);

            rfe = facturaElectronicaService.emitFacturaElectronica(fe);

            update(iv);


            ret = rfe.getResult() == 1;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        } catch (Exception e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        }
        iv.setHacienda(ret);
        return rfe;
    }

    private void initReenvioInfo(Boolean esReenvio, FacturaElectronica fe, Integer id, String oldClave, InvoiceV43Mapper mapper) {
        if (!esReenvio) {
            return;
        }

        BillSenderDetail bsd = null;
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByClave(oldClave);
        if (l != null && l.size() > 0) {
            bsd = l.get(0);
            try {
                FacturaElectronica feOld = feu.readXMLFile(bsd.getPath() + bsd.getClave() + ".xml");
                FacturaElectronica.InformacionReferencia ir = mapper.getInformacionReferenciaSustituyePorRechazoFe(feOld);
                fe.getInformacionReferencia().add(ir);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("--> initReenvioInfo  NO encontro " );
        }
    }

    public String getXmlFileName(String clave) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = billConfigService.getCompletePath() + fileName;
        return fileName;
    }

//    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd) {
//        if (!billEmitter.sign(options, facturaNamespaceV42)) {
//            this.billTaskService.agregarError("FIRMA", billEmitter.getMessageError(), id);
//            this.billTaskService.createProcesoEmisionDetalle(id, new java.util.Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo);
//
//            return false;
//        }
//        return true;
//    }



    public void update(Invoice invoice) {
        invoice.setUpdateAt(new java.sql.Date(System.currentTimeMillis()));
        this.repository.save(invoice);
    }



//    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd) {
//        if (!billEmitter.sign(options, facturaNamespaceV42)) {
//            this.billTaskService.agregarError("FIRMA", billEmitter.getMessageError(), id);
//            this.billTaskService.createProcesoEmisionDetalle(id, new java.util.Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo);
//
//            return false;
//        }
//        return true;
//    }


    /* Actualizado por felix 9-7/2021 para que al cambiar el estado
       use el save de la api que es el que valida si reserva el inventario de
       de acuerdo al cambio de estado del documento

     }*/
    @Transactional(rollbackFor = {Exception.class})
    public Invoice updateStatus(Integer id, String status) throws Exception {
        Invoice c = this.repository.getById(id);

        if (c == null) {
            throw new GeneralInventoryException("Factura no encontrada!");
        }
        String estadoAnterior = c.getStatus();
        User user = userDetailsService.getCurrentLoggedUser();
        c.setUserId(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        c.setStatus(status);
//        validateCodigoCabys(c,estadoAnterior);
        congelarInventarioBodegaFactura(c, estadoAnterior);
        this.addBillCollectByInvoiceData(c, user.getId().intValue());

        c.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        c = this.save(c);

       /*    Eliminado por felix 9-7/2021, el inventario solo sale por el proceso de  salidas pendientes
            if (CostCenterConstants.BILL_PENDING_STATUS.equals(status)) {
            this.investoryManagementService.manageStockOut(c.getDetails(),true, id);

        }*/
        return c;

    }

    public FacturaElectronica getFacturaElectronica(Invoice iv) throws DatatypeConfigurationException {
        GeneralParameter gp = this.generalParameterService.get(4);
        List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
        InvoiceV43Mapper ivm = new InvoiceV43Mapper(emisor, gp);

        FacturaElectronica fe = ivm.mapFacturaElectronica(iv);
        return fe;
    }

    public NotaCreditoElectronica getNotaCreditoElectronica(InvoiceNotaCredito notaCredito) throws DatatypeConfigurationException {
        GeneralParameter gp = this.generalParameterService.get(4);
        List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
        InvoiceNotaCreditoV43Mapper ivm = new InvoiceNotaCreditoV43Mapper(emisor, gp);


        NotaCreditoElectronica fe = ivm.mapFacturaElectronica(notaCredito);
        return fe;
    }

    public FacturaElectronicaExportacion getFacturaElectronicaExportacion(Invoice iv)
            throws DatatypeConfigurationException {
        GeneralParameter gp = this.generalParameterService.get(4);
        List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
        FacturaElectronicaExportacionV43Mapper ivm = new FacturaElectronicaExportacionV43Mapper(emisor, gp);

        FacturaElectronicaExportacion fe = ivm.mapFacturaElectronica(iv);
        return fe;
    }

    public String getPdfFileName(String clave) {
        List<BillSenderDetail> bsd = this.billSenderDetailRepository.findByClave(clave);
        if (bsd != null && bsd.size() > 0) {
            BillSenderDetail c = bsd.get(0);
            return c.getPath() + c.getClave() + ".pdf";
        }
        return null;
    }


    public String getXmlFile(String clave) {
        List<BillSenderDetail> bsd = this.billSenderDetailRepository.findByClave(clave);
        if (bsd != null && bsd.size() > 0) {
            BillSenderDetail c = bsd.get(0);
            return c.getPath() + "signed" + c.getClave() + ".xml";
        }
        return null;
    }

    public BillCollect addBillCollectByInvoiceData(Invoice invoice, Integer idUser) {
        // BCStatus = Anulada , Pagada, Pendiente,Vencida
        BillCollect bc = createBillCollect(invoice.getClient(), invoice.getCurrency(), idUser, invoice);
        if (invoice.getPoc() != null) {
            bc.setInterPurchageOrder(invoice.getPoc().getConsecutive().toString());
        }

        if (StringHelper.INVOICE_STATUS.PENDIENTE.equals(invoice.getStatus())) {
            bc = this.saveBillCollect(bc);
//            List<InvoiceDetail> invds= this.getInvoiceDetailRepository().getAll(invoice.getId());
            Set<InvoiceDetail> invds = invoice.getDetails();
            this.addBillCollectDetails(invds, bc, idUser);
            // bc.setTax(taxes);
            this.billCollectRepository.save(bc);
            invoice.setBillCollect(bc);
        } else if (StringHelper.INVOICE_STATUS.VENCIDA.equals(invoice.getStatus())) {
            this.updateBillCollect(invoice.getBillCollect(), StringHelper.BILL_COLLECT_STATUS.VENCIDA, idUser);
        } else if (StringHelper.INVOICE_STATUS.ANULADA.equals(invoice.getStatus())) {
            this.updateBillCollect(invoice.getBillCollect(), StringHelper.BILL_COLLECT_STATUS.ANULADA, idUser);
        } else if (StringHelper.INVOICE_STATUS.PAGADA.equals(invoice.getStatus())) {
            this.updateBillCollect(invoice.getBillCollect(), StringHelper.BILL_COLLECT_STATUS.PAGADA, idUser);
        }

        //this.getInvoiceRepository().update(invoice);
        return bc;
    }


    private BillCollect saveBillCollect(BillCollect billCollect) {
        billCollect.setInClosing(false);
        billCollect.setStatusClosing(StringHelper.STATUS_DATA_ING);
        billCollect.setCreateDate(new java.sql.Date(System.currentTimeMillis()));
        billCollect.setUpdateDate(new java.sql.Date(System.currentTimeMillis()));
        return billCollectRepository.save(billCollect);
    }


    public BillCollect createBillCollect(Client client, Currency currency, Integer userId, Invoice i) {
        BillCollect ret = new BillCollect();
        ret.setStatus(i.getStatus());
        ret.setBillNumber(i.getNumber() != null ? i.getNumber().toString() : "0"); //todo: Validar
        ret.setBillDate(i.getDate());
        ret.setEndDate(StringHelper.plusDays(i.getDate(), i.getCreditDays()));
        ret.setStatus(StringHelper.BILL_COLLECT_STATUS.PENDIENTE);
        ret.setPurchageOrder(i.getPoc().getOrderNumber());
        ret.setPayDate(ret.getEndDate());
        ret.setCreditDay(i.getCreditDays());
        ret.setInterPurchageOrder(i.getPoc().getOrderNumber());
        ret.setTotal(i.getTotal());
        ret.setClient(i.getClient());
        ret.setCurrency(i.getCurrency());
        ret.setTax(i.getIv());
        ret.setExonerated(i.getExonerated());
        ret.setSubTotal(i.getSubTotal());
//        ret.setExon(this.getExonerated());
        ret.setDescription("");
        ret.setTransactDate(i.getDate());
        ret.setInClosing(false);
        ret.setStatusClosing(StringHelper.STATUS_DATA_ING);


//        BillCollect bc = ret.newBillCollect();
//        bc.setClient(client);
//        bc.setCurrency(currency);
//        bc.setUserId(userId);
        return ret;
    }

    ;

    private Double addBillCollectDetails(Set<InvoiceDetail> invds, BillCollect parent, Integer userId) {
        Double ret = 0d;
        BillCollectDetail bpd;

        if (parent.getDetails() == null) {
            parent.setDetails(new HashSet<>());
        }
        for (InvoiceDetail obj : invds) {
            bpd = new BillCollectDetail();
            bpd.setBillCollect(parent);
            bpd.setDetail(obj.getDescription());
            bpd.setQuantity(obj.getQuantity());
            bpd.setPrice(obj.getPrice());
            bpd.setTax(obj.getTax() != null ? obj.getTax() : 0d);
            bpd.setDiscount(0d); //todo: Validar de donde se puede tomar
            bpd.setGroceryCode(null);
            bpd.setStatus(StringHelper.STATUS_DATA_ING);
            bpd.setSubTotal(obj.getSubTotal());
            bpd.setTotal(obj.getTotal());
            bpd.setUserId(userId);
            bpd.setCostCenter(obj.getCostCenter());
            bpd.setTaxPorcent(obj.getTax());
            bpd.setExonerated(obj.getExonerated());

            bpd.setType(obj.getType());
            bpd.setCreateDate(new java.sql.Date(System.currentTimeMillis()));
            bpd.setUpdateDate(new java.sql.Date(System.currentTimeMillis()));

            parent.getDetails().add(bpd);
            ret += bpd.getTax();
        }
        return ret;
    }

    private Double getTaxPorcent(Double subTotal, Double tax) {
        if (subTotal < 1)
            return 0d;
        return tax * 100 / subTotal;

    }

    private void updateBillCollect(BillCollect bc, String status, Integer idUser) {
        if (bc != null) {
            bc.setStatus(status);
            bc.setUserId(idUser);
            if (bc.getInClosing() == null)
                bc.setInClosing(false);
            bc.setUpdateDate(new java.sql.Date(System.currentTimeMillis()));
            this.billCollectRepository.save(bc);
        }


    }

    Boolean isNumberEmpty(Integer number) {
        if (number==null || number==0) {
            return true;
        }
        return false;
    }

    Boolean isNumberEmpty(Double number) {
        if (number==null || number==0d) {
            return true;
        }
        return false;
    }

    public InvoicesListDTO getInvoices(String filter) {
        InvoicesListDTO dto = new InvoicesListDTO();
        if (!isNumeric(filter))
            dto.setInvoicesList(this.repository.getListFilter(filter));
        else {
            int d = Integer.parseInt(filter);
            dto.setInvoicesList(this.repository.getListFilter(d));
        }
        return dto;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public SalesDetailInfoDTO getSalesDetailInfo(Date startDocDate, Date endDocDate) {

        List<SaleDetailInfoDTO>  facturas = new ArrayList<>();
        List<SaleDetailInfoDTO>  notasCredito = new ArrayList<>();
        SalesDetailInfoDTO salesDetailInfo = new SalesDetailInfoDTO();
        facturas = this.repository.getSaleDetailInfo(startDocDate, endDocDate);
        notasCredito = this.repository.getSaleNCDetailInfo(startDocDate, endDocDate);

        if (facturas != null) {
           salesDetailInfo.getSalesDetailInfo().addAll(facturas);
        }

        if (notasCredito != null) {
            salesDetailInfo.getSalesDetailInfo().addAll(notasCredito);
        }

        return salesDetailInfo;
    }

    public String getServerWebsite() {
        return serverWebsite;
    }

    public void setServerWebsite(String serverWebsite) {
        this.serverWebsite = serverWebsite;
    }

    public InvoiceFlatDTO getInvoiceById(Integer id) throws Exception {
        Invoice i = this.repository.getById(id);
        InvoiceFlatDTO dto = new InvoiceFlatDTO();
        dto.setCurrent(i);
        dto.setEmpresa(this.loadEmpresa());

        return dto;
    }

    public EmpresaTiqueteDTO loadEmpresa() throws Exception{


        EmpresaTiqueteDTO infoEmpresa = new EmpresaTiqueteDTO();
        GeneralParameter generalParameterNombreEmpresa =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        GeneralParameter generalParameterCedula = this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_CEDULA_EMPRESA);
        GeneralParameter generalParameterTelefono =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_PHONE_EMPRESA);
        GeneralParameter generalParameterCorreo =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_EMAIL_EMPRESA);
        GeneralParameter generalParameterDireccion =  this.generalParameterRepository.findByCode(POS_SHORT_ADDRESS);
        GeneralParameter generalParameterTiqueteBottom =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM);
        GeneralParameter generalParameterTiqueteBottom1 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM1);
        GeneralParameter generalParameterTiqueteBottom2 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM2);
        GeneralParameter generalParameterTiqueteBottom3 =  this.generalParameterRepository.findByCode(POS_TIQUETE_BOTTOM3);
//       GeneralParameter generalParameterDireccion =  this.generalParameterRepository.findByCodeAndName(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_ADDRESS_EMPRESA);

        if (infoEmpresa  == null) {
            throw new GeneralInventoryException("Parametro  info de empresa no encontrado: " + INVOICE_PARAMETER_DATOS_EMPRESA);
        }

        if (generalParameterNombreEmpresa  == null) {
            throw new GeneralInventoryException("Parametro nombre empresa no encontrado: " + INVOICE_PARAMETER_NOMBRE_EMPRESA);
        }

        if (generalParameterCedula  == null) {
            throw new GeneralInventoryException("Parametro cedula empresa no encontrado: " + INVOICE_PARAMETER_CEDULA_EMPRESA);
        }

        if (generalParameterTelefono  == null) {
            throw new GeneralInventoryException("Parametro telefono empresa no encontrado: " + INVOICE_PARAMETER_PHONE_EMPRESA);
        }

        if (generalParameterCorreo  == null) {
            throw new GeneralInventoryException("Parametro correo empresa no encontrado: " + INVOICE_PARAMETER_EMAIL_EMPRESA);
        }

        if (generalParameterDireccion  == null) {
            throw new GeneralInventoryException("Parametro direccion empresa no encontrado: " + INVOICE_PARAMETER_ADDRESS_EMPRESA);
        }




        //Asignar los valores a la empresa
        infoEmpresa.setEmpresaNombre(generalParameterNombreEmpresa.getVal());
        infoEmpresa.setTiqueteBottom(generalParameterTiqueteBottom.getVal());
        infoEmpresa.setTiqueteBottom1(generalParameterTiqueteBottom1.getVal());
        infoEmpresa.setTiqueteBottom2(generalParameterTiqueteBottom2.getVal());
        infoEmpresa.setTiqueteBottom3(generalParameterTiqueteBottom3.getVal());

        infoEmpresa.setCedula(generalParameterCedula.getVal());
        infoEmpresa.setTelefono(generalParameterTelefono.getVal());
        infoEmpresa.setCorreo(generalParameterCorreo.getVal());
        infoEmpresa.setDireccionCorta(generalParameterDireccion.getName());


        return infoEmpresa;
    }
}
