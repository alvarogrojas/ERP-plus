package com.ndl.erp.services.pos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.erp.constants.BodegaConstants;
import com.ndl.erp.constants.TerminalConstants;
import com.ndl.erp.constants.TerminalUsuarioConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.*;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.AnnotationDetectionMethodCallback;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ndl.erp.constants.CotizacionConstants.*;
import static com.ndl.erp.constants.DescuentosConstants.DESCUENTO_TIPO_GLOBAL;

import static com.ndl.erp.constants.InvoiceConstants.*;
import static com.ndl.erp.constants.SessionPosConstants.*;
import static com.ndl.erp.constants.TerminalConstants.TERMINAL_ESTADO_ABIERTA;
import static com.ndl.erp.constants.TerminalConstants.TERMINAL_ESTADO_CERRADA;
import static com.ndl.erp.constants.UserConstants.*;
import static com.ndl.erp.util.StringHelper.IMP_VENTA;
import static com.ndl.erp.util.StringHelper.IMP_VENTA_DEFAULT;

@Component
public class PosService {

    @Autowired
    SessionPosRepository sessionPosRepository;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    TerminalUsuarioRepository terminalUsuarioRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private TaxesIvaRepository taxIvaRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private MedioPagoRepository medioPagoRepository;

    @Autowired
    private CentroCostosService costCenterService;


    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private GeneralParameterRepository generalParameterRepository;


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    private EconomicActivityRepository economicActivityRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private DescuentosRepository descuentosRepository;

    public SessionPos getSessionPosAbierta() {
       SessionPos sessionPos = null;
       Integer sessionCount = 0;
       sessionCount =  this.sessionPosRepository.countSesionAbierta(userService.getCurrentLoggedUser().getId());

       if ( sessionCount <= MAX_POS_OPEN_SESSIONS) {
          if (sessionCount > 0) {
               sessionPos = this.sessionPosRepository.getSesionAbierta(userService.getCurrentLoggedUser().getId());
           }
       }else {
           throw new GeneralInventoryException("El usuario sobrepasó el límite de sessiones abiertas!");
       }

       return sessionPos;
    }

    public synchronized void asignarTerminalSession(SessionPos sessionPos)throws Exception {

        TerminalUsuario terminalUsuario;
        terminalUsuario = this.terminalUsuarioRepository.findTerminalUsuarioByUsuario(this.userService.getCurrentLoggedUser().getId());
        if (terminalUsuario == null) {
            throw new GeneralInventoryException("El usuario no tiene una terminal disponible para iniciar una sesion");
        }

        if (terminalUsuario.getTerminal() == null) {
            throw new GeneralInventoryException("El usuario no tiene una terminal asignada para iniciar una sesion");
        }

        terminalUsuario.getTerminal().setEstado(TERMINAL_ESTADO_ABIERTA);
        terminalUsuario.getTerminal().setUpdateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        sessionPos.setTerminalUsuario(terminalUsuario);
        this.terminalRepository.save(terminalUsuario.getTerminal());

    }

    public synchronized void liberarTerminalSession(SessionPos sessionPos) throws Exception{

        if (sessionPos.getTerminalUsuario() == null) {
            throw new GeneralInventoryException("No se encuentra una terminal que cerrar.");
        }

        Terminal terminal = sessionPos.getTerminalUsuario().getTerminal();
        if (terminal == null) {
            throw new GeneralInventoryException("La session no tiene una terminal asignada");
        }

        terminal.setUpdateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        terminal.setEstado(TERMINAL_ESTADO_CERRADA);
        this.terminalRepository.save(terminal);
    }

    @Transactional(rollbackFor = {Exception.class})
    public InventarioDTO getInventarioByCodigo(String barCode, Integer bodegaId) {



        if (bodegaId  == null){
            throw new GeneralInventoryException("La bodega de la terminal está nula, por favor revise el la parametrización!");
        }

        InventarioDTO r = new InventarioDTO();
        try {

            Inventario l = this.inventarioRepository.findByCodigobarrasAndBodega(barCode, bodegaId);

            if (l == null) {
                throw new NotFoundException("Código de barras no encontrado en el inventario de la bodega de la terminal POS ");
            }
            Inventario inventario = l;
//        Inventario inventario = l.get(0);

            r.setCurrent(inventario);
            DescuentosDTO d = this.productoService.getDescuentosDTO(inventario.getProducto().getId());
            r.setDescuentos(d.getDescuentos());

        } catch(Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error buscando el codigo de barras " + barCode + ". "+ e.getMessage());
        }
        return r;


    }

    @Transactional(rollbackFor = {Exception.class})
    public InventarioDTO getInventarioFlatByCodigo(String barCode, Integer bodegaId) {



        if (bodegaId  == null){
            throw new GeneralInventoryException("La bodega de la terminal está nula, por favor revise el la parametrización!");
        }

        InventarioDTO r = new InventarioDTO();
        try {

            Inventario l = this.inventarioRepository.findByCodigobarrasAndBodega(barCode, bodegaId);

            if (l != null) {
                Inventario inventario = l;
                r.setCurrent(inventario);
            }



        } catch(Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error buscando el codigo de barras " + barCode + ". "+ e.getMessage());
        }
        return r;
    }

    @Transactional(rollbackFor = {Exception.class})
    public InventarioDTO getInventarioById(Integer id) {

       // List<Inventario> l = this.inventarioRepository.findByBarcodeAndBodega(codigo, bodegaId);
        Inventario inventario = this.inventarioRepository.getById(id);


        if (inventario==null) {
            throw new NotFoundException("El inventario de la bodega de la terminal POS no encontrado");
        }
//        Inventario inventario = l.get(0);

        InventarioDTO r = new InventarioDTO();

        r.setCurrent(inventario);
        DescuentosDTO d = this.productoService.getDescuentosDTO(inventario.getProducto().getId());
        r.setDescuentos(d.getDescuentos());

        return r;


    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized SessionPos open(SessionPos sessionPos) throws Exception {
        SessionPos sessionAbierta = getSessionPosAbierta();

        if (sessionPos.getId() == null) {
            //Si tiene una sesion abierta la usa
            if (sessionAbierta != null){
               sessionPos = sessionAbierta;
            } else {
              sessionPos.setEstado(SESSION_POS_ESTADO_ABIERTO);
            }
            asignarTerminalSession(sessionPos);
            this.setAuditoriaCreacion(sessionPos);
            this.setAuditoriaModificacion(sessionPos);
            this.addCurrencyAndExchangeRate(sessionPos);
        }else {
           this.setAuditoriaModificacion(sessionPos);
           if (sessionPos.getCurrency()==null) {
               this.addCurrencyAndExchangeRate(sessionPos);
           }
        }

       this.sessionPosRepository.save(sessionPos);

        return sessionPos;
    }

    private void addCurrencyAndExchangeRate(SessionPos s) {
        s.setCurrency(this.currencyService.getSystemCurrency());

        s.setExchangeRate(this.exchangeRateService.getFirstActiveExchangeRate());

    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized SessionPos closeSession(SessionPos sessionPos) throws Exception {

        SessionPos sessionAbierta = getSessionPosAbierta();

        if (sessionPos.getId() == null) {
            //busca una sesion abierta
            if (sessionAbierta != null){
                sessionPos = sessionAbierta;
            }
            //Hay que liberar la terminal en uso
            liberarTerminalSession(sessionPos);
            this.setAuditoriaCreacion(sessionPos);
            this.setAuditoriaModificacion(sessionPos);
        }else {
            //Liberar la terminal en uso y cerrar la session
            this.setAuditoriaModificacion(sessionPos);
            this.liberarTerminalSession(sessionPos);
            sessionPos.setEstado(SESSION_POS_ESTADO_CERRADO);
        }

        this.sessionPosRepository.save(sessionPos);

        return sessionPos;
    }

    public SessionPosDTO getSessionPos(Integer id) throws Exception {

        if (id == null){
            throw new GeneralInventoryException("La sesion no ha sido encontrada!");
        }

        SessionPos sessionPos = sessionPosRepository.findSessionById(id);

        if (sessionPos == null){
           throw new GeneralInventoryException("La sesssion no ha sido encontrada!");
        }

        SessionPosDTO  sessionPosDTO= new SessionPosDTO();
        sessionPosDTO.setCurrent(sessionPos);
        sessionPosDTO.setEstados(this.getEstadosSessionPos());

        return sessionPosDTO;
    }

    public List<String>  getEstadosSessionPos() {
       List <String> estados  =  new ArrayList<String>();
       estados.add(SESSION_POS_ESTADO_ABIERTO);
       estados.add(SESSION_POS_ESTADO_CERRADO);
       estados.add(SESSION_POS_ESTADO_ESPERA);
       return estados;
    }

    public void setAuditoriaModificacion(SessionPos sessionPos) {
        sessionPos.setFechaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        sessionPos.setUserModificadoPor(this.userService.getCurrentLoggedUser());
    }

    public void setAuditoriaCreacion(SessionPos sessionPos) {
        sessionPos.setFechaCreacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        sessionPos.setUserIngresadoPor(this.userService.getCurrentLoggedUser());
    }

    public SessionPosDTO getSessionPos() {


        SessionPosDTO sessionPosDTO = this.getNewSessionPos();

        SessionPos s = new SessionPos();
        User u = this.userService.getCurrentLoggedUser();
        TerminalUsuario tu = this.terminalUsuarioRepository.findTerminalUsuarioByUsuario(u.getId());

        if (tu==null) {
            throw new NotFoundException("No se encontró terminal disponible asignada para el usuario " + u.getDisplayName());
        }
        s.setTerminalUsuario(tu);
        s.setEstado(TERMINAL_ESTADO_ABIERTA);

        ObjectMapper mapper = new ObjectMapper();

//        String jsonString = null;
//        try {
//            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(" 1 " + jsonString);


        sessionPosDTO.setCurrent(s);
//        try {
//            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sessionPosDTO);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(" 1 " + jsonString);
        return sessionPosDTO;
    }

    public SessionPosDTO getNewSessionPos() {

        SessionPosDTO sessionPosDTO = new SessionPosDTO();
        sessionPosDTO.setEstados(getEstadosSessionPos());
        return sessionPosDTO;
    }

    public SesionesDTO getSesiones(java.util.Date startFecha,
                                   java.util.Date endFecha,
                                   String estado,
                                   Integer userId,
                                   Integer bodegaId,
                                   Integer pageNumber,
                                   Integer pageSize,
                                   String sortDirection,
                                   String sortField) {

        SesionesDTO sesionesDTO = getSesiones();
        try {
            if (userId==null || userId==0) {
                userId = getRestrictionIfNeeded();
            }
            sesionesDTO.setPage(this.sessionPosRepository.getPageableSessionsByDateAndEstadoAndUser(startFecha,
                    endFecha,
                    estado,
                    userId,
                    bodegaId,
                    createPageable(pageNumber,
                            pageSize,
                            sortDirection,
                            sortField)));


            sesionesDTO.setTotalAmount(this.sessionPosRepository.getSumTotalByDatesAndEstado(startFecha, endFecha, estado, userId, bodegaId));
            sesionesDTO.setTotal(this.sessionPosRepository.countPageableSessionsByDateAndEstadoAndUser(startFecha, endFecha, estado, userId, bodegaId));

            sesionesDTO.setEstados(getEstados());
            sesionesDTO.setBodegas(this.bodegaRepository.getBodegaByStatus(BodegaConstants.ESTADO_BODEGA_ACTIVA));
            sesionesDTO.setSessionAbierta(getSessionPosAbierta());
            if (sesionesDTO.getTotal() > 0) {
                sesionesDTO.setPagesTotal(sesionesDTO.getTotal() / pageSize);
            } else {
                sesionesDTO.setPagesTotal(0);
            }
        }  catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sesionesDTO;

    }

    private Integer getRestrictionIfNeeded() {
        Integer userId =null;
        User u = this.userService.getCurrentLoggedUser();
        userId  = u.getId().intValue();
        for (UserRole ur:u.getRoles()) {
            if (ur.getRole().getName().equals(USUARIO_ADMINISTRADOR) || ur.getRole().getName().equals(USUARIO_POWER_USUARIO)) {
                userId = null;
                break;
            }
        }

        return userId;


    }

    public List<String> getEstados(){

        List<String> estadoList = new ArrayList<>();
        estadoList.add(SESSION_POS_ESTADO_ABIERTO);
        estadoList.add(SESSION_POS_ESTADO_CERRADO);
        estadoList.add(SESSION_POS_ESTADO_ESPERA);

        return estadoList;
    }

    private SesionesDTO getSesiones() {
        SesionesDTO s =  new SesionesDTO();
        s.setEstados(getEstadosSessionPos());
        return s;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public TaxesListDTO getTaxesIva() {
        TaxesListDTO dto = new TaxesListDTO();
        dto.setIvas(this.taxIvaRepository.getTaxes());
        return dto;
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

       if (this.userService.getCurrentLoggedUser() == null) {
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
       infoEmpresa.setNombreCajero(this.userService.getCurrentLoggedUser().getDisplayName());

        return infoEmpresa;
   }


   public void setDefaultClient(PosDTO p){

        GeneralParameter defaultCustomerSelling = this.generalParameterRepository.findByCode(INVOICE_DEFAULT_CLIENT);
        if (defaultCustomerSelling == null) {
            throw new GeneralInventoryException("Error Pos: no se encuentra el parametro para el cliente default: " + INVOICE_DEFAULT_CLIENT);
        }

        Client defaultClient = this.clientRepository.findByClientId(defaultCustomerSelling.getIntVal());

        if (defaultClient  == null) {
            throw new GeneralInventoryException("Error Pos: no se encuentra el cliente default con el id: " + defaultCustomerSelling.getIntVal());
        }
        p.setClienteDefault(defaultClient);
   }


    public PosDTO getNewPos(Integer id) throws  Exception {
        PosDTO r = new PosDTO();
        try {

            r.setIvas(this.taxIvaRepository.getTaxes());
            r.setCurrentSession(getSessionPosAbierta());
            r.setActivities(this.economicActivityRepository.findAll());
            r.setCurrencies(this.currencyRepository.findAllCurrency());
            r.setMedioPagos(this.medioPagoRepository.findAllMedioPagosActivo());
            r.setDefaultCostCenter(this.costCenterService.getDefaultCostCenterForSelling());
            r.setEmpresaTiqueteDTO(this.setEmpresaTiqueteInfo());
            r.setExchangeRates(this.exchangeRateService.getActivesExchangeRate());
            r.setDescuentos(this.descuentosRepository.getDescuentosActivosByTipo(DESCUENTO_TIPO_GLOBAL));
            r.setDefaultIva(getDefaultIva());
            setDefaultClient(r);
            if (id != null) {
                r.setCurrent(this.invoiceRepository.getById(id));
                setDescuentosCache(r);
            }
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new GeneralInventoryException(re.getMessage());
        }catch (Exception re) {
            re.printStackTrace();
            throw new GeneralInventoryException(re.getMessage());
        }
        return r;
    }

    // GET DEFAULT IVA PORCENTAGE AND FIND FRONT TAXES
    private TaxesIva getDefaultIva() {
        List<GeneralParameter> gp = this.generalParameterRepository.getByCode(IMP_VENTA_DEFAULT);
        if (gp==null || gp.size()==0) {
            return null;
        }
        GeneralParameter defaultGp = gp.get(0);
        try {
            Double val = Double.parseDouble(defaultGp.getVal());
            List<TaxesIva> taxes = taxIvaRepository.getTaxes();
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

    private void setDescuentosCache(PosDTO r) {
        if (r.getCurrent().getStatus().equals(INVOICE_STATUS_EDICION)) {
//            if (r.getCurrent().getTipoDescuento().equals(DESCUENTO_TIPO_GLOBAL)) {
//
//            }
            List<DescuentosCacheDTO> cache = new ArrayList<>();
            for(InvoiceDetail id: r.getCurrent().getDetails()) {
                if(id.getInventario()!=null &&
                        !isBarcodeInCache(cache, id.getInventario().getBarcode())) {
                    DescuentosDTO d = this.productoService.getDescuentosDTO(id.getInventario().getProducto().getId());
                    //r.setDescuentos(d.getDescuentos());
                    cache.add(new DescuentosCacheDTO(id.getInventario().getBarcode(),d.getDescuentos()));
                }
            }
            r.setDescuentosCache(cache);
        }
    }

    private boolean isBarcodeInCache(List<DescuentosCacheDTO> cache, String barcode) {
        if (cache.size()==0) {
            return false;
        }

        for (DescuentosCacheDTO d: cache
             ) {
            if (d.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Invoice save(Invoice c) throws Exception {
        return this.invoiceService.savePosTransaction(c);
    }

    public TransactionsDTO getTransactionsById(Integer id) {

        TransactionsDTO result = new TransactionsDTO();
        try {


            if (id == null) {
                SessionPos p = this.getSessionPosAbierta();
                if (p == null) {
                    throw new NotFoundException("No hay sesion abierta, debe abrir una sesion previamente para poder consultar las transacciones");
                }
                result.setSessionPos(p);
                id = p.getId();
            }

            result.setInvoiceList(this.invoiceRepository.getBySessionPosId(id));
            if (result.getInvoiceList()==null || result.getInvoiceList().size()==0) {
                result.setSessionPos(this.sessionPosRepository.getSessionPosById(id));
            }
            List<Integer> ids = this.invoiceRepository.getIdsBySessionPosId(id);
            result.setInvoiceListNc(this.invoiceNotaCreditoRepository.getNotaCreditosByInvoicesIds(ids));
            result.setTotalFacturas(sumTotalFacturas(result));
//            result.setTotalFacturas(this.invoiceRepository.sumSessionFacturaTotal(id));
//            result.setTotalNcs(this.invoiceNotaCreditoRepository.sumSessionNCTotal(ids));
            result.setTotalNcs(sumTotalNcs(result));

            result.setDefaultCurrency(this.currencyRepository.getSystemCurrency());

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (GeneralInventoryException e1) {
            e1.printStackTrace();
            throw e1;
        }catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }


    private Double sumTotalFacturas(TransactionsDTO result) {
        Double total = 0d;

        if (result==null || result.getInvoiceList()==null) {
            return total;
        }
        Double currenTotal = 0d;
        for (Invoice i: result.getInvoiceList()) {
            if (i.getCurrency().getDefault()) {
                currenTotal = i.getTotal();
            } else {
                currenTotal = BillHelper.convertToDefaultCurrency(i.getExchangeRate(), i.getTotal());

            }
            total = total + currenTotal;
        }
        return total;
    }

    private Double sumTotalNcs(TransactionsDTO result) {
        Double total = 0d;

        if (result==null || result.getInvoiceListNc()==null) {
            return total;
        }
        Double currenTotal = 0d;
        for (InvoiceNotaCredito i: result.getInvoiceListNc()) {
            if (i.getCurrency().getDefault()) {
                currenTotal = i.getTotal();
            } else {
                currenTotal = BillHelper.convertToDefaultCurrency(i.getExchangeRate(), i.getTotal());

            }
            total = total + currenTotal;
        }
        return total;
    }

    public TerminalesDTO getTerminales(String estado, Integer bodegaId) {
        TerminalesDTO l = new TerminalesDTO();

        l.setTerminales(this.terminalRepository.getTerminalesByEstadoAndBodega(estado, bodegaId));

        l.setEstados(getEstadosSessionPos());
        l.setBodegas(this.bodegaRepository.getBodegaByStatus(BodegaConstants.ESTADO_BODEGA_ACTIVA));

        return l;

    }

    public TerminalDTO getTerminal(Integer id) {
        TerminalDTO dto = new TerminalDTO();

        List<String> roles = new ArrayList<>();
        roles.add(USUARIO_ADMINISTRADOR);
        roles.add(USUARIO_CAJERO);
        Set<User> users = this.userService.getUsersByRoles(roles);
        dto.setUsuarios(new ArrayList(users));
        dto.setBodegas(this.bodegaRepository.getBodegasFacturables());
        //dto.setEstados(getEstadosSessionPos());
        if (id==null)
            dto.setCurrent(new Terminal());
        else {
            dto.setCurrent(this.terminalRepository.getById(id));
            dto.setUsuariosCurrentTerminal(this.terminalUsuarioRepository.findTerminalUsuarioByTerminal(id));
        }
        return dto;
    }

    public TerminalDTO getNewTerminalForm() {
        return this.getTerminal(null);

    }

//    public Terminal saveTerminal(Terminal t ) {
    public Terminal saveTerminal(TerminalDTO dto ) {
        boolean isUpdate = true;
        Terminal t = dto.getCurrent();
        if (t.getId()==null) {
            t.setCreateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            t.setEstado(TERMINAL_ESTADO_CERRADA);
            isUpdate = false;
        }
        t.setUpdateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        t = this.terminalRepository.save(t);
        if (dto.getUsuariosCurrentTerminal()==null && !isUpdate){
            return t;
        }

        if (isUpdate) {
            inactiveAllUsers(t);
        }
        TerminalUsuario persisted;
        for (TerminalUsuario tu: dto.getUsuariosCurrentTerminal()) {
            persisted = this.terminalUsuarioRepository.findTerminalUsuarioByUsuarioOnly(tu.getUsuario().getId(), t.getId());
            if (persisted!=null) {
                tu = persisted;
            }

            manageTerminalAlreadyAsignedToUser(tu); // SOLO UNA TERMINA ASIGNADA PARA CADA USUARIO,
                                                    // si un usuario necesita ser asignado a una terminal se libera la que tenia en ese momento

            if (tu.getTerminal()==null) {
                tu.setTerminal(t);

            }

            tu.setEstado(TerminalUsuarioConstants.TERMINAL_USUARIO_ESTADO_ACTIVO);
            this.terminalUsuarioRepository.save(tu);
        }

        return t;
    }

    private void manageTerminalAlreadyAsignedToUser(TerminalUsuario tu) {
        TerminalUsuario tuAlreadyAsigned = this.terminalUsuarioRepository.
                    findTerminalUsuarioByUsuario(tu.getUsuario().getId());
        if (tuAlreadyAsigned!=null) {
            tuAlreadyAsigned.setEstado(TerminalUsuarioConstants.TERMINAL_USUARIO_ESTADO_INACTIVO);
            this.terminalUsuarioRepository.save(tuAlreadyAsigned);
        }
    }

    public void inactiveAllUsers(Terminal t) {
        List<TerminalUsuario> tus = this.terminalUsuarioRepository.findTerminalUsuarioByTerminalAll(t.getId());
        for (TerminalUsuario tu : tus) {
            tu.setEstado(TerminalUsuarioConstants.TERMINAL_USUARIO_ESTADO_INACTIVO);
             this.terminalUsuarioRepository.save(tu);
        }
    }



}
