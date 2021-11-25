package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.BillPayConstants.BILL_PAY_ESTADO_EDICION;
import static com.ndl.erp.constants.BodegaConstants.*;
import static com.ndl.erp.constants.RefundableConstants.REFUNDABLE_STATUS_EDICION;



@Component
public class BodegaService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    private InventarioItemRepository inventarioItemRepository;

    @Autowired
    private ProductoDescuentoService productoDescuentoService;

    @Autowired
    private BillPayRepository billPayRepository;

    @Autowired
    private BodegaManagerService bodegaManagerService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RefundableRepository refundableRepository;

    @Autowired
    private RequisicionBodegaRepository requisicionBodegaRepository;


    @Autowired
    private InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    private MotivoDevolucionRepository motivosDevolucionRepository;

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private DevolucionService devolucionService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillPayNotaCreditoRepository billPayNotaCreditoRepository;


    public BodegaDTO getBodegaData(Integer id) {

        BodegaDTO dto  = this.getBodega();

        Bodega f = this.bodegaRepository.getById(id);
        dto.setCurrent(f);

        return dto;
    }

    public BodegaDTO getBodega() {


        List<String> estados = this.getBodegaEstados();
        List<String> manejoBodegaFlags = this.getBodegaManejoBodegaFlags();
        List<String> manejoPreciosBodegaFlags = this.getBodegaManejoPreciosFlags();
        List<String> facturableFlags = this.getBodegaFacturableFlags();

        Bodega  b = new Bodega();
        b.setStatus(ESTADO_BODEGA_ACTIVA);
        b.setFacturable(BODEGA_FACTURABLE);
        b.setManejoBodega(MANEJO_BODEGA_SIMPLE);
        b.setFacturable(BODEGA_FACTURABLE);
        b.setManejoPrecio(MANEJO_BODEGA_PRECIO_MAYOR);

        BodegaDTO bodegaDTO = new BodegaDTO();
        bodegaDTO.setCurrent(b);
        bodegaDTO.setEstados(estados);
        bodegaDTO.setManejosBodega(manejoBodegaFlags);
        bodegaDTO.setManejoPrecios(manejoPreciosBodegaFlags);
        bodegaDTO.setFacturableFlags(facturableFlags);


        return bodegaDTO;
    }


    public List<String> getBodegaEstados(){
        List<String> estados = new ArrayList<>(0);
        estados.add(ESTADO_BODEGA_ACTIVA);
        estados.add(ESTADO_BODEGA_INACTIVA);
        return estados;
    }

    public List<String> getBodegaFacturableFlags(){
        List<String> facturableFlags = new ArrayList<>(0);
        facturableFlags.add(BODEGA_FACTURABLE);
        facturableFlags.add(BODEGA_NO_FACTURABLE);
        return facturableFlags;
    }

    public List<String> getBodegaManejoBodegaFlags(){
        List<String> manejosBodega = new ArrayList<>(0);
        manejosBodega.add(MANEJO_BODEGA_SIMPLE);
        manejosBodega.add(MANEJO_BODEGA_PEPS);
        manejosBodega.add(MANEJO_BODEGA_UEPS);

        return manejosBodega;
    }

    public List<String> getBodegaManejoPreciosFlags(){
        List<String> manejoPrecios = new ArrayList<>(0);
        manejoPrecios.add(MANEJO_BODEGA_PRECIO_MAYOR);
        manejoPrecios.add(MANEJO_BODEGA_PRECIO_ACTUALIZA);
        return manejoPrecios;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void inicializarProductosBodega(Bodega bodega) throws Exception {
        List<Producto> productoList = this.productoRepository.getProductoByEstado();
        InventarioBodega inventarioBodega;
        for (Producto p: productoList) {
           inventarioBodega = this.bodegaManagerService.nuevoInventarioBodega(bodega, p, 0d);
           this.inventarioBodegaRepository.save(inventarioBodega);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Bodega save(Bodega bodega) throws Exception {
       Boolean nuevaBodega = false;

       if (bodega.getId() == null){
           nuevaBodega = false;
       } else {
           nuevaBodega = true;
       }
       this.setAuditoria(bodega);

       this.bodegaRepository.save(bodega);

       if (nuevaBodega){
          this.inicializarProductosBodega(bodega);
       }
       return bodega;
    }

    public void setAuditoria(Bodega c) throws Exception{

        User u = this.userService.getCurrentLoggedUser();
        if (u==null) {
            throw new NotFoundException("User is not logged");
        }
//        c.setUserUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUserUltimoCambio(u);

    }


    public ProductsPendingsInDTO getProductsPendingsInFromBillPay(Date startDate, Date endDate, Integer bodegaId){

        ProductsPendingsInDTO dto = new ProductsPendingsInDTO();
        ProductsPendingsInDTO refundableDTO = new ProductsPendingsInDTO();
        refundableDTO.setBillPaysPendings(this.refundableRepository.getRefundableTotalByPayDates(startDate, endDate, bodegaId));
        dto.setBillPaysPendings(this.billPayRepository.getBillPayTotalByPayDates(startDate, endDate, bodegaId));
        dto.getBillPaysPendings().addAll(refundableDTO.getBillPaysPendings());
        return dto;
    }


    public ProductsPendingsOutDTO getProductsPendingsOutFromInvoice(Date startInvoiceDate, Date endInvoiceDate, Integer bodegaId){

        ProductsPendingsOutDTO dto = new ProductsPendingsOutDTO();
        ProductsPendingsOutDTO requisicionProductosPendingOutDTO = new ProductsPendingsOutDTO();
        ProductsPendingsOutDTO billPayNotaCreditoProductosPendingOutDTO = new ProductsPendingsOutDTO();
        dto.setInvoiceProductsPendings(this.invoiceRepository.getInvoiceProductTotalByPayDates(startInvoiceDate, endInvoiceDate, bodegaId));
        requisicionProductosPendingOutDTO.setInvoiceProductsPendings(this.requisicionBodegaRepository.getRequisicionProductTotalByPayDates(startInvoiceDate, endInvoiceDate, bodegaId));
        dto.getInvoiceProductsPendings().addAll(requisicionProductosPendingOutDTO.getInvoiceProductsPendings());
        //Agregar a la lista el listado de las Notas de Credito de proveedores pendientes de salida y que salida de bodega sea falso
        billPayNotaCreditoProductosPendingOutDTO.setInvoiceProductsPendings(this.billPayNotaCreditoRepository.getBillPayNotaCreditoProductTotalByPayDates(startInvoiceDate, endInvoiceDate, bodegaId));
        dto.getInvoiceProductsPendings().addAll(billPayNotaCreditoProductosPendingOutDTO.getInvoiceProductsPendings());

        return dto;
    }

    public ProductoDescuentoListDTO getDescuentosByProducto(Integer productoId){
        return productoDescuentoService.getProductoDescuentoList(productoId);
    }


    public BodegaInventarioDTO getItems(String filter, Integer bodegaId, Integer pageNumber,
                                        Integer pageSize, String sortDirection,
                                        String sortField) {

        BodegaInventarioDTO d = new BodegaInventarioDTO();

        if (bodegaId!=null && bodegaId>0) {
            d.setItems(this.inventarioItemRepository.getInventarioItemByBodegaIdAndFilter(bodegaId, filter,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.inventarioItemRepository.countByBodegaIdAndFilter(bodegaId, filter));
        } else {
            d.setItems(this.inventarioItemRepository.getInventarioItemByFilter(filter,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            d.setTotal(this.inventarioItemRepository.countByFilter(filter));
        }


        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        d.setBodegas(bodegaRepository.getBodegaByStatus("Activa"));

        return d;

    }

//    public ClientDTO getClient() {
//        List<IdentificationType> types = new ArrayList<>();
//        types.add(new IdentificationType("01","Cédula Física"));
//        types.add(new IdentificationType("02","Cédula Jurídica"));
//        types.add(new IdentificationType("03","DIMEX"));
//        types.add(new IdentificationType("04","NITE"));
//
//        List<String> estados = new ArrayList<>();
//        estados.add("Activo");
//        estados.add("Inactivo");
//
//        List<String> exonerados = new ArrayList<>();
//        exonerados.add("SI");
//        exonerados.add("NO");
//        ClientDTO d = new ClientDTO();
//
//        d.setEstados(estados);
//        d.setTypesId(types);
//        d.setExonedoStates(exonerados);
//        return d;
//    }

    public InventarioItem saveItem(InventarioItem c) {

        return this.inventarioItemRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public ProductosDTO getProductos(Integer bodegaId, String filter, Boolean esEntrada) {
        ProductosDTO r;

        r = this.productoService.getProductosListDTO(bodegaId, filter, esEntrada);

        return r;

    }

    public ProductosDTO getProductos(String filter) {
        ProductosDTO r;

        r = this.productoService.getProductosListDTO(filter);

        return r;

    }

    public InventarioDTO getProductosInventario(String filter, Integer bodegaId, Integer productoId) {
        InventarioDTO inventario = new InventarioDTO();

        inventario = this.productoService.getInventarioDTO(filter, bodegaId, productoId);

        return inventario;

    }

    public ProductsPendingsInDTO addInventory(String type,
                                              Integer id,
                                              Date startBillPayDate,
                                              Date endBillPayDate,
                                              Integer bodegaId) throws  Exception{

        if (type.equals(TIPO_ENTRADA_BODEGA_CXP)) {
            BillPay bp = this.billPayRepository.getOne(id);
            this.bodegaManagerService.entradaBodega(bp, bodegaId);
        } else if(type.equals(TIPO_ENTRADA_BODEGA_REM)) {
            Refundable  rf = this.refundableRepository.getOne(id);
            this.bodegaManagerService.entradaRefundableBodega(rf, bodegaId);
        } else {
            throw new GeneralInventoryException("Tipo de entrada a bodega desconocida: " + type);
        }

        return getProductsPendingsInFromBillPay(startBillPayDate, endBillPayDate, bodegaId); //agregar bodega
    }

      public ProductsPendingsOutDTO decreaseInventory(String type,
                                                    Integer invoiceId,
                                                    Date startInvoiceDate,
                                                    Date endInvoiceDate,
                                                    Integer bodegaId) throws  Exception{

        if (TIPO_SALIDA_BODEGA_INVOICE.equals(type)) {

            Invoice i = this.invoiceRepository.getOne(invoiceId);
            this.bodegaManagerService.salidaBodega(i, bodegaId, false);

        } else if (TIPO_SALIDA_BODEGA_REQUISICION.equals(type)) {

            RequisicionBodega r = this.requisicionBodegaRepository.findRequisicionBodegaById(invoiceId);
            this.bodegaManagerService.salidaRequisicionBodega(r, bodegaId);

        } else if (TIPO_SALIDA_BODEGA_BILL_PAY_NOTA_CREDITO.equals(type)) {
            BillPayNotaCredito r = this.billPayNotaCreditoRepository.findBillPayNotaCreditoById(invoiceId);
            this.bodegaManagerService.salidaNotaCreditoProveedorBodega(r, bodegaId);
        } else {
            throw new GeneralInventoryException("Tipo de salida de bodega desconocida: " + type);
        }
        return getProductsPendingsOutFromInvoice(startInvoiceDate, endInvoiceDate, bodegaId); //agregar bodega
    }

    public InPendingsDTO getProductsPendingsInFromBillPay(String type, Integer id) {

        InPendingsDTO result = new InPendingsDTO(id);
        if (type.equals(TIPO_ENTRADA_BODEGA_CXP)) {
            result.setDetails(this.billPayRepository.getBillPayDetailsPendings(id));
        } else if(type.equals(TIPO_ENTRADA_BODEGA_REM)) {
            result.setDetails(this.refundableRepository.getRefundableDetailPendings(id));
        } else {
           throw new GeneralInventoryException("Tipo de entrada a bodega desconocida: " + type);
        }
        result.setType(type);
        result.setBodegas(this.bodegaRepository.getBodegaByStatus( "Activa"));

        return result;
    }

    public OutPendingsDTO getProductDetailPendingsOutFromInvoice(String type, Integer id) {

        OutPendingsDTO result = new OutPendingsDTO(type, id);
        if (type.equals(TIPO_SALIDA_BODEGA_INVOICE)) {
            result.setDetails(this.invoiceRepository.getInvoiceDetailsPendings(id));
        } else if (type.equals(TIPO_SALIDA_BODEGA_REQUISICION)) {
            result.setDetails(this.requisicionBodegaRepository.getRequisicionDetailsPendings(id));
        } else if (type.equals(TIPO_SALIDA_BODEGA_BILL_PAY_NOTA_CREDITO)) {
                result.setDetails(this.billPayNotaCreditoRepository.getBillPayNotaCreditoDetailsPendings(id));
        } else{
            throw new GeneralInventoryException("Tipo de salida de bodega desconocido");
        }
        return result;
    }

    //Metodos para devoluciones por nota de credito
    public ProductsPendingReturnDTO getProductsReturnPendingsInFromInvoiceNotaCredito(Date startDate, Date endDate, Integer bodegaId){

        ProductsPendingReturnDTO dto = new ProductsPendingReturnDTO();
        dto.setProductsPendingReturn(this.invoiceNotaCreditoRepository.getInvoiceNotaCreditoProductReturnPendingTotalByDates(startDate, endDate, bodegaId));
        return dto;
    }

    public InPendingsDTO getProductsPendingsFromDevoluciones(String type, Integer id) {

        InPendingsDTO result = new InPendingsDTO(id);
        if (type.equals(TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO)) {
            result.setDetails(this.invoiceNotaCreditoRepository.getNotaCreditoDetailsPendings(id));

            Devolucion d = this.devolucionRepository.findDevolucionById(id);
            if (d!=null) {
                result.setDevolucionId(d.getId());
            }
        } else if(type.equals(TIPO_ENTRADA_DEVOLUCION_REQUISICION)) {
            result.setDetails(this.refundableRepository.getRefundableDetailPendings(id));
        } else {
            throw new GeneralInventoryException("Tipo de devolucion a bodega desconocida: " + type);
        }
        result.setType(type);
        result.setMotivos(this.motivosDevolucionRepository.getMotivoDevolucionActivo());
        result.setBodegas(this.bodegaRepository.getBodegaByStatus( "Activa"));

        return result;
    }

    public BodegasDTO getBodegas(String estado, Integer pageNumber, Integer pageSize, String sortDirection, String sortField) {

        BodegasDTO d = new BodegasDTO();


        d.setBodegas(this.bodegaRepository.getBodegasByEstado(estado,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.bodegaRepository.countsBodegasByEstado(estado));

        d.setEstados(getBodegaEstados());


        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }



        return d;
    }

    /******************************************************************************************************
      Rechazo de documentos de bodega segun especifique el cliente
    *******************************************************************************************************/


//    public synchronized RechazoEntradaDTO rechazoEntrada(Integer id, String type) throws Exception {
    @Transactional(rollbackFor = {Exception.class})
    public synchronized Boolean rechazoEntrada(Integer id, String type) throws Exception {

        RechazoEntradaDTO rechazoEntradaDTO = new RechazoEntradaDTO();

        if (type.equals(TIPO_ENTRADA_BODEGA_CXP)) {

            BillPay billPay;
            billPay = billPayRepository.getOne(id);

            if (billPay == null) {
                throw new GeneralInventoryException("No se encontró la cuenta  por pagar!");
            }

            if (billPay.getIngresadoBodega() == true) {
                throw new GeneralInventoryException("La cuenta por pagar ya fué ingresado a la bodega!");
            }


            if (billPay.getIngresadoBodega() == false) {
                billPay.setStatus(BILL_PAY_ESTADO_EDICION);
                billPayRepository.save(billPay);
                rechazoEntradaDTO.setBillPay(billPay);
                rechazoEntradaDTO.setType(type);
                rechazoEntradaDTO.setResult(true);
                rechazoEntradaDTO.setId(billPay.getId());
            }

        } else if (type.equals(TIPO_ENTRADA_BODEGA_REM)) {

            Refundable refundable;
            refundable = refundableRepository.getOne(id);

            if (refundable == null) {
                throw new GeneralInventoryException("El reembolsable no fué encontrado!");
            }

            if (refundable.getIngresadoBodega() == true) {
                throw new GeneralInventoryException("El reembolsable ya fué ingresado a la bodega!");
            }

            if (refundable.getIngresadoBodega() == false) {
                refundable.setStatus(REFUNDABLE_STATUS_EDICION);
                refundableRepository.save(refundable);
                rechazoEntradaDTO.setRefundable(refundable);
                rechazoEntradaDTO.setType(type);
                rechazoEntradaDTO.setResult(true);
                rechazoEntradaDTO.setId(refundable.getId());
            }
        } else {
            throw new GeneralInventoryException("El tipo de documento por rechazar es desconocido : " + type);
        }

        return true;
//        return rechazoEntradaDTO;

    }

/*
    @Transactional(rollbackFor = {Exception.class})
    public synchronized DevolucionDetalle createDevolucion(Integer detailId,
                                              Integer referenciaId,
                                              Integer devolucionId,
                                              String type,
                                              Integer motivoId
                                              ) throws Exception {

         Devolucion devolucion = devolucionRepository.findDevolucionById(devolucionId);
         MotivoDevolucion motivo = motivosDevolucionRepository.findMotivoDevolucionById(motivoId);
         DevolucionDetalle devolucionDetalle = null;


         if (motivo == null){
           throw new GeneralInventoryException("Motivo de la devolucion no encontrado!");
         }

         if (TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO.equals(type)) {
             InvoiceNotaCreditoDetail invoiceNotaCreditoDetail= this.invoiceNotaCreditoRepository.findInvoiceNotaCreditoDetalleById(detailId);
             if (invoiceNotaCreditoDetail == null){
                 throw new GeneralInventoryException("El detalle de la nota de credito no existe!");
             }
             if (devolucion == null) {
                 devolucion = new Devolucion(invoiceNotaCreditoDetail.getInvoiceNotaCredito());
                 devolucionService.setAuditoriaCreacionDevolucion(devolucion);
             } else {
                 devolucionService.setAuditoriaModificacionDevolucion(devolucion);
             }
             devolucionDetalle = devolucion.addDetails(invoiceNotaCreditoDetail.getInvoiceNotaCredito(),invoiceNotaCreditoDetail, motivo);

         } else if (TIPO_ENTRADA_DEVOLUCION_REQUSICION.equals(type)) {

             RequisicionBodegaDetalle requisicionBodegaDetalle = this.requisicionBodegaRepository.findRequisicionBodegaDetalleById(detailId);

             if (requisicionBodegaDetalle == null){
                 throw new GeneralInventoryException("El detalle de la requisicion no existe!");
             }

             if (devolucion == null){
                   devolucion = new Devolucion(requisicionBodegaDetalle.getRequisicionBodega());
             }
             devolucionDetalle= devolucion.addDetails(requisicionBodegaDetalle.getRequisicionBodega(), requisicionBodegaDetalle, motivo);

         } else {
             throw new GeneralInventoryException("Tipo de devolución por requisición no encontrado");
         }

         this.devolucionRepository.save(devolucion);
         //TODO: Que temporalmente ingrese el documento a la bodega para probar el proceso
         //if (devolucion.getEstado().equals(DEVOLUCION_STATUS_INGRESADO)) {
            //Actualizar el inventario segun el tipo de devolucion
            if (TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO.equals(type)) {
                bodegaManagerService.entradaDevolucionBodegaNotaCredito(devolucion, 0);
            } else if (TIPO_ENTRADA_DEVOLUCION_REQUSICION.equals(type)) {
                bodegaManagerService.entradaDevolucionBodegaRequisicion(devolucion, 0);
            }
        //}
         return devolucionDetalle;
    }*/
}
