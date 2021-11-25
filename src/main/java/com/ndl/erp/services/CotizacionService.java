package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.CotizacionDTO;
import com.ndl.erp.dto.CotizacionListDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.ndl.erp.constants.CotizacionConstants.*;
import static com.ndl.erp.constants.DocumentoConsecutivoConstants.CONSECUTIVO_COTIZACION_HISTORICO;
import static com.ndl.erp.constants.DocumentoConsecutivoConstants.CONSECUTIVO_EMPRESA;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_DATOS_EMPRESA;


@Component
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionHistoricoVersionRepository cotizacionHistoricoVersionRepository;

    @Autowired
    private CotizacionHistoricoVersionDetalleRepository cotizacionHistoricoVersionDetalleRepository;

    @Autowired
    private DocumentoConsecutivoService documentoConsecutivoService;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ContactClientRepository contactClientRepository;


    @Autowired
    private TaxesIvaRepository taxesIvaRepository;

    @Autowired
    private BodegaRepository BodegaRepository;

    @Autowired
    private BodegaManagerService bodegaManagerService;

    @Autowired
    private GeneralParameterService generalParameterService;


    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private ExchangeRateService exchangeRateService;


    public Cotizacion getCotizacionById(Cotizacion cotizacion) {

        Cotizacion c = this.cotizacionRepository.findCotizacionById(cotizacion.getId());

        return c;
    }


    private void validacionesSaveNuevaCotizacion(Cotizacion c) throws Exception {

        Integer conteo = this.cotizacionRepository.countAllCotizacionByCotizacionNumber(c.getCotizacionNumber());
        if (conteo > 0){
            throw new java.lang.Exception("El número de cotización ya existe!");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Cotizacion save(Cotizacion c) throws Exception {

        if (c.getId()==null) {

            validacionesSaveNuevaCotizacion(c);
            setAuditoriaCreacionCotizacion(c);
            this.generalParameterService.generateNextCotizacionNumber(c);

        } else {

           Cotizacion oldCotizacion = getCotizacionById(c);

           if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_APROBADA)) {
              throw new RuntimeException("No se permite modificar cotizaciones Aprobadas");
           }

           if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_ENVIADA) && c.getEstado().equals(COTIZACION_ESTADO_EDICION)) {
               this.manjejarCotizacionHistoricoVersionPorEstado(c);
           }
           this.manejarCantidadCotizadaPorEstado(oldCotizacion, c);

           setAuditoriaModificacionCotizacion(c);

        }

        return this.cotizacionRepository.save(c);
    }

    public void setAuditoriaCreacionCotizacion(Cotizacion c) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            throw new RuntimeException("User is not logged");
        }
        c.setFechaIngreso(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUserIngresadoPor(u);

    }

    public void setAuditoriaModificacionCotizacion(Cotizacion c) throws Exception{

        c.setFechaUltimaModificacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));

    }

    public CotizacionListDTO getCotizacionList(String filter, Date startFecha, Date endFecha, Integer pageNumber,
                                               Integer pageSize, String sortDirection,
                                               String sortField) {

        CotizacionListDTO c = new CotizacionListDTO();

        c.setPage((this.cotizacionRepository.getFilterPageableCotizacionByFechaEmision(filter,startFecha, endFecha,
                createPageable(pageNumber, pageSize, sortDirection, sortField))));

        c.setTotal(this.cotizacionRepository.countAllFilterPageableCotizacionByFechaEmision(filter,startFecha, endFecha));
        if (c.getTotal()>0) {
            c.setPagesTotal(c.getTotal() /pageSize);
        } else {
            c.setPagesTotal(0);
        }

        return c;

    }



    public void  removerContactoNoExistenteCliente(Client c, List<ContactClient> clientContactList) {

        if (clientContactList.contains(c)) {
            clientContactList.remove(c);
        }
    }

    public CotizacionDTO getCotizacion(Integer id) throws Exception{
        CotizacionDTO cotizacion = this.getCotizacion();
        Cotizacion c  = cotizacionRepository.findCotizacionById(id);
        if (c == null) {
            return cotizacion;
        }else {
            cotizacion.setCurrent(c);
            cotizacion.setEntregas(this.getTiempoEntrega());
            cotizacion.setEstadoList(getCotizacionEstados());
            cotizacion.setMonedasList(currencyRepository.findAll());
            cotizacion.setTaxes(this.taxesIvaRepository.findAll());

            removerContactoNoExistenteCliente(c.getClient(), cotizacion.getContactClientList());
            cotizacion.setClienteList(this.clientRepository.findClientsActive());
            cotizacion.setContactClientList(this.contactClientRepository.findByClient(cotizacion.getCurrent().getClient().getClientId()));
        }
        return cotizacion;
    }


    public List<String> getCotizacionEstados(){

        List<String> estadoList = new ArrayList<>();
        estadoList.add(COTIZACION_ESTADO_EDICION);
        estadoList.add(COTIZACION_ESTADO_ENVIADA);
        estadoList.add(COTIZACION_ESTADO_NO_ADJUDICADA);
        estadoList.add(COTIZACION_ESTADO_VENCIDA);
        estadoList.add(COTIZACION_ESTADO_APROBADA);


        return estadoList;
    }

    public List<String> getTiempoEntrega(){

        List<String> entregas = new ArrayList<>();
        entregas.add(ENTREGA_INMEDIATA);
        entregas.add(ENTREGA_2_3_SEMANAS);
        entregas.add(ENTREGA_4_5_SEMANAS);

        return entregas;
    }

    public CotizacionDTO getCotizacion() throws Exception {

        CotizacionDTO d = new CotizacionDTO();
        d.setEstadoList(getCotizacionEstados());
        d.setEntregas(getTiempoEntrega());
        d.setVendedorList(this.userRepository.findUsersActive());
        d.setClienteList(this.clientRepository.findClientsActive());
        d.getCurrent().setBodega(this.BodegaRepository.getById(1));
        d.setExchangeRates(this.exchangeRateService.getActivesExchangeRate());
        d.setTaxes(this.taxesIvaRepository.findAll());

        if (!d.getClienteList().isEmpty()) {
            List <ContactClient> contactClientList = this.contactClientRepository.findByClient(d.getClienteList().get(0).getClientId());
            if (contactClientList != null) {
                d.setContactClientList(contactClientList);
            }
        }

       d.setMonedasList(currencyRepository.findAll());

        return d;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public Map getDataForPdf(Integer id) throws Exception {


        Map <String,Object> data = new HashMap<String,Object>();

        CotizacionDTO dto = this.getCotizacion(id);
        dto.setEmpresaInfoParameters(generalParameterService.getByCodes(INVOICE_PARAMETER_DATOS_EMPRESA));
        data.put("cotizacionDTO",dto);

        return data;

    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized void manejarCantidadCotizadaPorEstado(Cotizacion oldCotizacion, Cotizacion cotizacion) throws Exception {

        if (oldCotizacion == null){
            throw new java.lang.Exception("La cotización no fue encontrada!");
        }

        if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_EDICION) && cotizacion.getEstado().equals(COTIZACION_ESTADO_ENVIADA)) {
            bodegaManagerService.incrementarCantidadCotizadaBodega(cotizacion);
        } else if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_ENVIADA) && cotizacion.getEstado().equals(COTIZACION_ESTADO_EDICION)) {
            bodegaManagerService.restaurarCantidadCotizadaBodega(oldCotizacion);
        } else if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_APROBADA) && cotizacion.getEstado().equals(COTIZACION_ESTADO_EDICION)) {
            bodegaManagerService.restaurarCantidadCotizadaBodega(oldCotizacion);
        }

    }


    @Transactional(rollbackFor = {Exception.class})
    public void cotizacionHistoricoVersion (Cotizacion oldCotizacion) throws Exception{


        if (oldCotizacion.getDetalles() == null){
            throw new java.lang.Exception(" Control de versiones: Error al generar version de cotización, por favor valide que el detalle contenga datos");
        }

        Integer consecutivo = this.documentoConsecutivoService.getCotizacionHistoricoVersionNext(CONSECUTIVO_EMPRESA,
                                                                                                CONSECUTIVO_COTIZACION_HISTORICO,
                                                                                                oldCotizacion.getId());
        if (consecutivo == null){
            throw new java.lang.Exception("Control de versiones: Error al generar consecutivo de version de cotización, ");
        }

        CotizacionHistoricoVersion cotizacionHistoricoVersion = new CotizacionHistoricoVersion();
        cotizacionHistoricoVersion.setVersionNumber(consecutivo);
        cotizacionHistoricoVersion.setCotizacionPadre(oldCotizacion);
        try {
            BeanUtils.copyProperties(cotizacionHistoricoVersion, oldCotizacion);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        /*cotizacionHistoricoVersion.setCotizacionNumber(oldCotizacion.getCotizacionNumber());
        cotizacionHistoricoVersion.setFechaEmision(oldCotizacion.getFechaEmision());
        cotizacionHistoricoVersion.setFechaVencimiento(oldCotizacion.getFechaVencimiento());
        cotizacionHistoricoVersion.setClient(oldCotizacion.getClient());
        cotizacionHistoricoVersion.setContactClient(oldCotizacion.getContactClient());
        cotizacionHistoricoVersion.setAsunto(oldCotizacion.getAsunto());
        cotizacionHistoricoVersion.setDireccion(oldCotizacion.getDireccion());
        cotizacionHistoricoVersion.setPhone(oldCotizacion.getPhone());
        cotizacionHistoricoVersion.setEmail(oldCotizacion.getEmail());
        cotizacionHistoricoVersion.setCreditDays(oldCotizacion.getCreditDays());
        cotizacionHistoricoVersion.setVendedor(oldCotizacion.getVendedor());
        cotizacionHistoricoVersion.setTelefonoVendedor(oldCotizacion.getTelefonoVendedor());
        cotizacionHistoricoVersion.setEstado(oldCotizacion.getEstado());
        cotizacionHistoricoVersion.setCorreoVendedor(oldCotizacion.getCorreoVendedor());
        cotizacionHistoricoVersion.setObservaciones(oldCotizacion.getObservaciones());
        cotizacionHistoricoVersion.setSubTotal(oldCotizacion.getSubTotal());
        cotizacionHistoricoVersion.setIva(oldCotizacion.getIva());
        cotizacionHistoricoVersion.setTotal(oldCotizacion.getTotal());
        cotizacionHistoricoVersion.setUserIngresadoPor(oldCotizacion.getUserIngresadoPor());
        cotizacionHistoricoVersion.setFechaIngreso(oldCotizacion.getFechaIngreso());
        cotizacionHistoricoVersion.setFechaUltimaModificacion(oldCotizacion.getFechaUltimaModificacion());
        cotizacionHistoricoVersion.setCurrency(oldCotizacion.getCurrency());
        cotizacionHistoricoVersion.setBodega(oldCotizacion.getBodega());
        cotizacionHistoricoVersion.setExonerated(oldCotizacion.getExonerated());
        this.cotizacionHistoricoVersionRepository.save(cotizacionHistoricoVersion);*/


        List <CotizacionDetalle> cotizacionDetalle = oldCotizacion.getDetalles();
      //  List <CotizacionHistoricoVersionDetalle> cotizacionHistoricoVersionDetalleList = new ArrayList<CotizacionHistoricoVersionDetalle>();

        for (CotizacionDetalle cd : cotizacionDetalle){
            CotizacionHistoricoVersionDetalle cotizacionHistoricoVersionDetalle = new CotizacionHistoricoVersionDetalle();
            cotizacionHistoricoVersionDetalle.setCotizacionHistoricoVersion(cotizacionHistoricoVersion);
            cotizacionHistoricoVersionDetalle.setInventarioBodegaId(cd.getInventario().getId());

            /*
            cotizacionHistoricoVersionDetalle.setLineNumber(cd.getLineNumber());
            cotizacionHistoricoVersionDetalle.setDescripcion(cd.getDescripcion());
            cotizacionHistoricoVersionDetalle.setEntrega(cd.getEntrega());
            cotizacionHistoricoVersionDetalle.setCantidad(cd.getCantidad());
            cotizacionHistoricoVersionDetalle.setPrecioUnitario(cd.getPrecioUnitario());
            cotizacionHistoricoVersionDetalle.setPrecioTotal(cd.getPrecioTotal());
            cotizacionHistoricoVersionDetalle.setPorcentajeDescuento(cd.getPorcentajeDescuento());
            cotizacionHistoricoVersionDetalle.setIva(cd.getIva());
            cotizacionHistoricoVersionDetalle.setPorTax(cd.getPorTax());
            cotizacionHistoricoVersionDetalle.setTax(cd.getTax());
            cotizacionHistoricoVersionDetalle.setExonerated(cd.getExonerated());
            cotizacionHistoricoVersionDetalle.setSubTotal(cd.getSubTotal());
            cotizacionHistoricoVersionDetalle.setInventarioBodega(cd.getInventarioBodega());
            cotizacionHistoricoVersionDetalle.setCentroCosto(cd.getCostCenter());
            cotizacionHistoricoVersionDetalle.setClienteDescuento(cd.getClienteDescuento());
            cotizacionHistoricoVersionDetalle.setDescuentoMonto(cd.getDescuentoMonto());
            this.cotizacionHistoricoVersionDetalleRepository.save(cotizacionHistoricoVersionDetalle);
            cotizacionHistoricoVersionDetalleList.add(cotizacionHistoricoVersionDetalle);*/

            try {
                BeanUtils.copyProperties(cotizacionHistoricoVersionDetalle, cd);
                cotizacionHistoricoVersion.getCotizacionDetalleHistoricoVersionList().add(cotizacionHistoricoVersionDetalle);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        //cotizacionHistoricoVersion.setCotizacionDetalleHistoricoVersionList(cotizacionHistoricoVersionDetalleList);
        this.cotizacionHistoricoVersionRepository.save(cotizacionHistoricoVersion);

      }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized void manjejarCotizacionHistoricoVersionPorEstado(Cotizacion cotizacion) throws Exception {
        Cotizacion oldCotizacion = this.getCotizacionById(cotizacion);

        if (oldCotizacion == null){
            throw new java.lang.Exception("Control de versiones: La cotización no fué encontrada!");
        }

        //Si se modifica el estado de enviado a otro estado entonces se crea una nueva version con la proforma antes de ser modificada
        if (oldCotizacion.getEstado().equals(COTIZACION_ESTADO_ENVIADA) && !cotizacion.getEstado().equals(COTIZACION_ESTADO_ENVIADA)) {
            cotizacionHistoricoVersion(oldCotizacion);
        }

    }

    @Transactional
    public Cotizacion updateStatus(Integer id, String status) throws Exception{
        Cotizacion c = this.cotizacionRepository.findCotizacionById(id);
        if (c == null) {

            throw new GeneralInventoryException("Cotizacion no encontrada!");
        }

        c.setEstado(status);
        c.setFechaUltimaModificacion(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));

        c = this.cotizacionRepository.save(c);
        return c;

    }
}
