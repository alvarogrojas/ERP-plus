package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.DevolucionDTO;
import com.ndl.erp.dto.DevolucionListDTO;
import com.ndl.erp.dto.DevolucionRequisicionDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.DevolucionRepository;
import com.ndl.erp.repository.InvoiceNotaCreditoRepository;
import com.ndl.erp.repository.MotivoDevolucionRepository;
import com.ndl.erp.repository.RequisicionBodegaRepository;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.ndl.erp.constants.BodegaConstants.TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO;
import static com.ndl.erp.constants.BodegaConstants.TIPO_ENTRADA_DEVOLUCION_REQUISICION;
import static com.ndl.erp.constants.DevolucionConstants.*;

@Component
public class DevolucionService {

    @Autowired
    RequisicionBodegaRepository requisicionBodegaRepository;

    @Autowired
    MotivoDevolucionRepository motivoDevolucionRepository;

    @Autowired
    InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    DevolucionRepository devolucionRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BodegaManagerService bodegaManagerService;

    @Autowired
    GeneralParameterService generalParameterService;


    @Autowired
    private UserService userDetailsService;

    public DevolucionDTO getDevolucion() {

//         RequisicionBodega requisicion = this.requisicionBodegaRepository.findRequisicionBodegaById(requisicionId);

//        if (requisicion == null) {
//            throw new GeneralInventoryException("La requisicion no fue encontrada por lo que no se puede generar una devolucion");
//        }


        DevolucionDTO drDTO = this.getNewDevolucion();

        Devolucion d = new Devolucion();
        d.setEstado("Edicion");
        d.setFechaDevolucion(new Date());

        drDTO.setCurrent(d);
        return drDTO;
    }

    public DevolucionDTO getNewDevolucion() {

        DevolucionDTO drDTO = new DevolucionDTO();
        drDTO.setEstadoList(getEstadosDevolucion());
        drDTO.setMotivoDevolucionList(getMotivoDevolucionActivos());
        return drDTO;
    }


    public DevolucionDTO getDevolucionDTO(RequisicionBodega requisicionBodega) {
        if (requisicionBodega  == null){
            //throw new GeneralInventoryException("No se encontró una requisicion para crear la devolución");

        }

        if (requisicionBodega.getRequisicionBodegaDetalle()  == null){
           // throw new GeneralInventoryException("La requisicion no tiene detalle para crear la devolucion");
            throw  new GeneralInventoryException("La requisicion no tiene detalle para crear la devolucion");
        }

        DevolucionDTO dDTO = new DevolucionDTO();
        dDTO.setEstadoList(getEstadosDevolucion());
        dDTO.setMotivoDevolucionList(getMotivoDevolucionActivos());
        return dDTO;
    }


    public DevolucionDTO getDevolucion(Integer id) throws Exception {

        if (id == null){
          // throw new GeneralInventoryException("La devolución no ha sido encontrada!");
            throw new GeneralInventoryException("La devolución no ha sido encontrada!");
        }

        Devolucion devolucion = devolucionRepository.findDevolucionById(id);

        if (devolucion == null){
            //throw new GeneralInventoryException("La devolución no ha sido encontrada!");
            throw new GeneralInventoryException("La devolución no ha sido encontrada!");
        }

        DevolucionDTO devolucionDTO = new DevolucionDTO();

        if (devolucion.getTipo().equals(TIPO_ENTRADA_DEVOLUCION_REQUISICION)) {

            RequisicionBodega requisicionBodega = this.requisicionBodegaRepository.findRequisicionBodegaById(devolucion.getReferenciaId());
            devolucionDTO.setCurrent(devolucion);
            devolucionDTO.setRequisicionBodega(requisicionBodega);

        } else if(devolucion.getTipo().equals(TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO)){

            InvoiceNotaCredito invoiceNotaCredito = this.invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(devolucion.getReferenciaId());
            devolucionDTO.setInvoiceNotaCredito(invoiceNotaCredito);
        }
        devolucionDTO.setEstadoList(this.getEstadosDevolucion());
        devolucionDTO.setMotivoDevolucionList(getMotivoDevolucionActivos());

        return devolucionDTO;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Devolucion save(Devolucion devolucion) throws Exception {

        if (devolucion.getId()==null) {
            setAuditoriaCreacionDevolucion(devolucion);
            devolucion.setIngresadoBodega(false);
            this.generalParameterService.generateNextDevolucionNumber(devolucion);
        } else {
            setAuditoriaModificacionDevolucion(devolucion);

        }

        return this.devolucionRepository.save(devolucion);
    }

    public void setAuditoriaModificacionDevolucion(Devolucion devolucion) throws Exception{

        User u = this.userService.getCurrentLoggedUser();

        devolucion.setUltimaActualizacionPor(u);
        devolucion.setFechaUltimaActualizacion((new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())));


    }

    public void setAuditoriaCreacionDevolucion(Devolucion devolucion) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            //throw new GeneralInventoryException("User is not logged");
            throw new GeneralInventoryException("El usuario no está logueado!");
        }
        devolucion.setIngresadoPor(u);
        devolucion.setUltimaActualizacionPor(u);
        devolucion.setFechaUltimaActualizacion((new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())));


    }
    public DevolucionListDTO getDevolucionList(java.util.Date startFecha, java.util.Date endFecha, String status, Integer pageNumber,
                                               Integer pageSize, String sortDirection,
                                               String sortField) {

        DevolucionListDTO devolucionListDTO = getDevolucionListDTO();

        devolucionListDTO.setPage(this.devolucionRepository.getPageableDevolucionByDateAndEstado(startFecha,
                endFecha,
                status,
                createPageable(pageNumber,
                        pageSize,
                        sortDirection,
                        sortField)));

        devolucionListDTO.setTotal(this.devolucionRepository.countAllDevolucionByDateAndEstado(startFecha, endFecha,status));
        if (devolucionListDTO.getTotal()>0) {
            devolucionListDTO.setPagesTotal(devolucionListDTO.getTotal() /pageSize);
        } else {
            devolucionListDTO.setPagesTotal(0);
        }
        devolucionListDTO.setMotivoDevolucionList(this.motivoDevolucionRepository.getMotivoDevolucionActivo());
        return devolucionListDTO;

    }

    private DevolucionListDTO getDevolucionListDTO() {
        DevolucionListDTO r =  new DevolucionListDTO();
        String [] estados = {DEVOLUCION_STATUS_EDICION,DEVOLUCION_STATUS_PENDIENTE, DEVOLUCION_STATUS_INGRESADO, DEVOLUCION_STATUS_ANULADO};

        r.setEstadoList(Arrays.asList(estados));
        return r;
    }


    List<String> getEstadosDevolucion() {

        List<String> estadoList = new ArrayList<>(0);
//        estadoList.add(DEVOLUCION_STATUS_EDICION);
        estadoList.add(DEVOLUCION_STATUS_PENDIENTE);
        estadoList.add(DEVOLUCION_STATUS_INGRESADO);
        estadoList.add(DEVOLUCION_STATUS_ANULADO);

        return estadoList;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Devolucion createDevolucion(Integer id,
                                                    String type
    ) throws Exception {

        Devolucion devolucion =  this.devolucionRepository.getDevolucionByReferenciaIdAndType(id, type);

        if (devolucion != null) {
            throw new GeneralInventoryException("La devolucion para el documento ya existe!");
        }

        if (TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO.equals(type)) {
            InvoiceNotaCredito invoiceNotaCredito= this.invoiceNotaCreditoRepository.findByInvoiceNotaCreditoById(id);
            if (invoiceNotaCredito == null){
                //throw new GeneralInventoryException("La nota de crédito no existe!");
                throw new GeneralInventoryException("La nota de crédito no existe!");
            }
            if (invoiceNotaCredito.getDetails() == null){
                //throw new GeneralInventoryException("El detalle de la nota de credito está vacio!");
                throw  new GeneralInventoryException("El detalle de la nota de credito está vacio!");
            }

            if (invoiceNotaCredito.getIngresadoBodega()){
                throw new GeneralInventoryException("La nota de crédito ya inició el proceso de devolución!");
            }

            devolucion = new Devolucion(invoiceNotaCredito);
            setAuditoriaCreacionDevolucion(devolucion);
            invoiceNotaCredito.setIngresadoBodega(true);
            devolucion.setEstado(DEVOLUCION_STATUS_PENDIENTE);
            this.invoiceNotaCreditoRepository.save(invoiceNotaCredito);

        } else if (TIPO_ENTRADA_DEVOLUCION_REQUISICION.equals(type)) {

            RequisicionBodega requisicionBodega= this.requisicionBodegaRepository.findRequisicionBodegaById(id);
            if (requisicionBodega == null){
               //throw new GeneralInventoryException("La requisicion no existe!");
               throw new GeneralInventoryException("La requisicion no existe!");
            }

            if (requisicionBodega.getRequisicionBodegaDetalle() == null){
                //throw new GeneralInventoryException("El detalle de la requisición está vacio!");
                throw new GeneralInventoryException("El detalle de la requisición está vacio!");
            }

            if (requisicionBodega.isSalidaBodega()){
                throw new GeneralInventoryException("La requisición ya salió de bodega, no se puede procesar!");

            }

            devolucion = new Devolucion(requisicionBodega);
            devolucion.setEstado(DEVOLUCION_STATUS_EDICION);
            setAuditoriaCreacionDevolucion(devolucion);

        } else {
            throw new GeneralInventoryException("La requisición ya inició el proceso de devolución!");
        }

        this.save(devolucion);

        return devolucion;
    }

    List<MotivoDevolucion> getMotivoDevolucionActivos() {

        List<MotivoDevolucion> motivoDevolucionList =  motivoDevolucionRepository.getMotivoDevolucionActivo();

        return motivoDevolucionList;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public void validarIdProcesoDevolucion(Integer devolucionId,
                                           Integer devolucionDetalleId,
                                           Integer motivoId) throws Exception {
        if (devolucionId == null){
            //throw new GeneralInventoryException("La devolución   es un dato requerido");
            throw new GeneralInventoryException("La devolución   es un dato requerido");
        }

        if (devolucionDetalleId == null){
            //throw new GeneralInventoryException("El detalle de la devolución es un dato requerido");
            throw new GeneralInventoryException("El detalle de la devolución es un dato requerido");
        }

        if (motivoId == null){
            //throw new GeneralInventoryException("El motivo de la devolcion es un dato requerido");
            throw new GeneralInventoryException("El motivo de la devolucion es un dato requerido");
        }

    }

    public boolean ingresoDevolucionBodegaCompleto(Integer devolucionId){
        boolean procesoCompleto = false;
        Integer lineasPendientes = this.devolucionRepository.countDevolucionDetalleByDevolucionId(devolucionId);

        if (lineasPendientes == 0) {
            procesoCompleto = true;
        }

        return procesoCompleto;
    }

    public void validarConsultasProcesoDevolucion(  Devolucion devolucion,
                                                    DevolucionDetalle devolucionDetalle,
                                                    MotivoDevolucion motivoDevolucion) throws Exception{
        if (devolucion == null){
            //throw new GeneralInventoryException("La devolución   es un dato requerido");
            throw  new GeneralInventoryException("La devolución   es un dato requerido");
        }

        if (devolucionDetalle == null){
            //throw new GeneralInventoryException("El detalle de la devolución es un dato requerido");
            throw  new GeneralInventoryException("El detalle de la devolución es un dato requerido");
        }


        if (motivoDevolucion == null){
            //throw new GeneralInventoryException("El motivo de la devolucion es un dato requerido");
           throw  new GeneralInventoryException("El motivo de la devolucion es un dato requerido");
        }

    }

    public DevolucionDetalle procesarDevolucion(Integer devolucionId,
                                                Integer devolucionDetalleId,
                                                Integer motivoId) throws Exception {


        validarIdProcesoDevolucion(devolucionId,
                devolucionDetalleId,
                motivoId);

        Devolucion devolucion = this.devolucionRepository.findDevolucionById(devolucionId);
        DevolucionDetalle devolucionDetalle = this.devolucionRepository.getDevolucionDetalleByIdAndDevolucionId(devolucionId, devolucionDetalleId);
        MotivoDevolucion motivoDevolucion = this.motivoDevolucionRepository.findMotivoDevolucionById(motivoId);

        validarConsultasProcesoDevolucion( devolucion,
                devolucionDetalle,
                motivoDevolucion );

        if (devolucionDetalle.isIngresadoBodega()) {
            throw new  GeneralInventoryException("No se puede procesar un detalle de devolución  ingresado a bodega");
        }

        if (devolucion.getIngresadoBodega()) {
            throw new  GeneralInventoryException("No se puede procesar un documento de devolución  ingresado a bodega");
        }

        if (devolucion.getTipo().equals(TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO)) {

            this.bodegaManagerService.entradaDevolucionBodegaNotaCredito(devolucion, devolucionDetalle, motivoDevolucion);
        } else if (devolucion.getTipo().equals(TIPO_ENTRADA_DEVOLUCION_REQUISICION)) {
            this.bodegaManagerService.entradaDevolucionBodegaRequisicion(devolucion, devolucionDetalle, motivoDevolucion);
        } else {
            //throw new GeneralInventoryException("Tipo de entrada por devolucion desconocido!");
            throw new GeneralInventoryException("Tipo de entrada por devolucion desconocido!");
        }

        if (this.ingresoDevolucionBodegaCompleto(devolucionId)) {
            devolucion.setIngresadoBodega(true);
            devolucion.setEstado(DEVOLUCION_STATUS_INGRESADO);
            this.devolucionRepository.save(devolucion);
        }

        return devolucionDetalle;

    }

    public Devolucion saveDevolucionRequisicion(DevolucionRequisicionDTO devolucion) throws Exception {
        if (devolucion.getDevolucion()==null || devolucion.getRequisicion()==null) {
            //throw new GeneralInventoryException("La data no esta completa");
            throw new GeneralInventoryException("La data no esta completa");

        }

        Devolucion d = this.save(devolucion.getDevolucion());

        this.requisicionBodegaRepository.save(devolucion.getRequisicion());
        return d;
    }

    @Transactional
    public Devolucion updateStatus(Integer id, String status) throws Exception{
        Optional<Devolucion> o = this.devolucionRepository.findById(id);
        if (o == null) {
            //throw new GeneralInventoryException("Not found");
            throw new GeneralInventoryException("Devolucion no encontrada!");
        }
        Devolucion c = o.get();
        User user = userDetailsService.getCurrentLoggedUser();
        c.setUltimaActualizacionPor(user);

        c.setEstado(status);

        c.setFechaUltimaActualizacion(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c = this.devolucionRepository.save(c);


        return c;

    }
}
