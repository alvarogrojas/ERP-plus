package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.exceptions.NotAvailableItemsException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.GeneralParameterService;
import com.ndl.erp.services.UserService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import static com.ndl.erp.constants.BillPayNotaCreditoConstants.BILL_PAY_NOTA_CREDITO_EMITIDA;
import static com.ndl.erp.constants.BodegaConstants.*;
import static com.ndl.erp.constants.InitInventarioConstants.INIT_INVENTARIO_ESTADO_IMPORTADO;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_DETAIL_TIPO_PRODUCTO;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_STATUS_PENDIENTE;
import static com.ndl.erp.constants.ParseCargaArchivoConstants.*;
import static com.ndl.erp.constants.RequisicionBodegaConstants.REQUISICION_BODEGA_ESTADO_APROBADO;
import static com.ndl.erp.constants.RequisicionBodegaConstants.REQUISICION_BODEGA_ESTADO_EDICION;
import static com.ndl.erp.constants.TrasladoConstants.TRASLADO_STATUS_TRASLADADO;
import static com.ndl.erp.util.DateUtil.getNameMonthString;
import static com.ndl.erp.util.DateUtil.getYearString;


@Component
public class BodegaManagerService {



    @Autowired
    UserService user;

    @Autowired
    InventarioBodegaVentaDetalleRepository inventarioBodegaVentaDetalleRepository;

    @Autowired
    ContextoSalidaService contextoSalidaService;

    @Autowired
    ContextoEntradaService contextoEntradaService;


    @Autowired
    TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    SalidasInventarioRepository salidasInventarioRepository;

    @Autowired
    EntradasInventarioRepository entradasInventarioRepository;

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    BillPayRepository billPayRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    RefundableRepository refundableRepository;

    @Autowired
    RequisicionBodegaRepository requisicionBodegaRepository;

    @Autowired
    DevolucionRepository devolucionRepository;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    BodegaRepository BodegaRepository;

    @Autowired
    InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

   @Autowired
   InvoiceNotaCreditoDetailRepository invoiceNotaCreditoDetailRepository;

   @Autowired
   RequisicionBodegaDetalleRepository requisicionBodegaDetalleRepository;

   @Autowired
   DevolucionDetalleRepository devolucionDetalleRepository;

   @Autowired
   InventarioRepository inventarioRepository;

   @Autowired
   TrasladoRepository trasladoRepository;

   @Autowired
   BodegaRepository bodegaRepository;

   @Autowired
   CostCenterRepository costCenterRepository;

   @Autowired
   ProductoRepository productoRepository;

   @Autowired
   InitInventarioRepository initInventarioRepository;

   @Autowired
  BillPayNotaCreditoRepository billPayNotaCreditoRepository;


    @Transactional(rollbackFor = {Exception.class})
    public InventarioBodegaVentaDetalle nuevoInventarioVentaDetalle(InventarioBodega inventarioBodega, String mes, Integer year, Double cantidad) {

        InventarioBodegaVentaDetalle inventarioBodegaVentaDetalle = new InventarioBodegaVentaDetalle();

        inventarioBodegaVentaDetalle.setInventarioBodega(inventarioBodega);
        inventarioBodegaVentaDetalle.setMes(mes);
        inventarioBodegaVentaDetalle.setYear(year);
        inventarioBodegaVentaDetalle.setCantidad(cantidad);
        inventarioBodegaVentaDetalle.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        this.inventarioBodegaVentaDetalleRepository.save(inventarioBodegaVentaDetalle);

        return inventarioBodegaVentaDetalle;
    }

    @Transactional(rollbackFor = {Exception.class})
    public SalidasInventario nuevaSalidaInventario(Inventario inventario, Integer costsCenterId,  Integer documentoOrigenId, TipoDocumento tipoDocumento,
                                                   Double cantidad, Double costo, Integer documentoDetalleid) {

        SalidasInventario salidasInventario = new SalidasInventario();

        salidasInventario.setId(0);
        salidasInventario.setCostsCenterId(costsCenterId);
        salidasInventario.setInventario(inventario);
        salidasInventario.setUser(this.user.getCurrentLoggedUser());
        salidasInventario.setDocumentoOrigenId(documentoOrigenId);
        salidasInventario.setVentaDetalleId(documentoDetalleid);
        salidasInventario.setTipoDocumento(tipoDocumento);
        salidasInventario.setFecha(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        salidasInventario.setCosto(costo);
        salidasInventario.setCantidad(cantidad);
        salidasInventario.setDetalle("Movimiento de salida de inventario del producto: " + inventario.getProducto().getDescripcionEspanol() + " Bodega: " + inventario.getBodega().getName());
        salidasInventario.setTotal(salidasInventario.getCosto() * salidasInventario.getCantidad());

        this.salidasInventarioRepository.save(salidasInventario);

        return salidasInventario;
    }

    @Transactional(rollbackFor = {Exception.class})
    public EntradasInventario nuevaEntradaInventario(Inventario inventario, Integer costsCenterId, Integer documentoOrigenId, TipoDocumento tipoDocumento,
                                                    Double cantidad, Double costo, Integer detalleOrigenId) {

        EntradasInventario entradasInventario = new EntradasInventario();

        entradasInventario.setId(0);
        entradasInventario.setCostsCenterId(costsCenterId);
        entradasInventario.setInventario(inventario);
        entradasInventario.setUser(this.user.getCurrentLoggedUser());
        entradasInventario.setDocumentoOrigenId(documentoOrigenId);
        entradasInventario.setCompraDetalleId(detalleOrigenId);
        entradasInventario.setTipoDocumento(tipoDocumento);
        entradasInventario.setCosto(costo);
        entradasInventario.setFecha(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        entradasInventario.setCantidad(cantidad);
        entradasInventario.setDetalle("Movimiento de entrada de inventario del producto: " + inventario.getProducto().getDescripcionEspanol() + " Bodega: " + inventario.getBodega().getName());
        entradasInventario.setTotal(entradasInventario.getCosto() * entradasInventario.getCantidad());

        this.entradasInventarioRepository.save(entradasInventario);

        return entradasInventario;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void validarInventarioBodega(InventarioBodega inventarioBodega) throws Exception{

        if (inventarioBodega == null)
           throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el inventario de la bodega!");
        if (inventarioBodega.getBodega() == null)
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
        if (inventarioBodega.getProducto() == null)
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");
    }

    @Transactional(rollbackFor = {Exception.class})
    private synchronized void actualizarInventarioBodegaVentaDetalle(InventarioBodega inventarioBodega, String mes, Integer year, Double cantidad) throws Exception {

        InventarioBodegaVentaDetalle inventarioBodegaVentaDetalle;

        validarInventarioBodega(inventarioBodega);

        inventarioBodegaVentaDetalle =  this.inventarioBodegaVentaDetalleRepository.findByBodegaAndYearAndMesAndProducto(inventarioBodega.getBodega().getId(), year, mes, inventarioBodega.getProducto().getId());


        if (inventarioBodegaVentaDetalle != null) {

            inventarioBodegaVentaDetalle.setCantidad(inventarioBodegaVentaDetalle.getCantidad() + cantidad);
            inventarioBodegaVentaDetalle.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            this.inventarioBodegaVentaDetalleRepository.save(inventarioBodegaVentaDetalle);

        } else {

            inventarioBodegaVentaDetalle = nuevoInventarioVentaDetalle(inventarioBodega, mes, year, cantidad);

        }
    }


    @Transactional(rollbackFor = {Exception.class})
    private synchronized void actualizarInventarioBodegaEntrada(Bodega bodega, Producto producto, Double cantidad, Boolean isDevolucion, String modoCargaInventario) throws Exception {

        InventarioBodega inventarioBodega;

        inventarioBodega =  this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());


        if (inventarioBodega != null) {
            if (modoCargaInventario.equals("") || modoCargaInventario.equals(IMPORT_ACUMULAR_INVENTARIO)) {
                inventarioBodega.setCantidadDisponible(inventarioBodega.getCantidadDisponible() + cantidad);
            } else if (modoCargaInventario.equals(IMPORT_REEMPLAZAR_INVENTARIO)){
                if (cantidad >= inventarioBodega.getCantidadDisponible()) {
                    inventarioBodega.setCantidadDisponible(cantidad);
                }
            }
            this.inventarioBodegaRepository.save(inventarioBodega);
        } else {

            inventarioBodega = this.nuevoInventarioBodega(bodega, producto, cantidad);

        }
    }



    //@Async
    public void  salidaBodegaInvoiceAsinc(Invoice invoice, Integer bodegaId) throws Exception {
       this.salidaBodega(invoice, bodegaId, true);
    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  salidaBodega(Invoice invoice, Integer bodegaId, boolean isSalidaPos) throws Exception{

        boolean salidaExitosa = false;
        Set<InvoiceDetail> invoiceDetail = invoice.getDetails();
        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_VENTA);

        if (invoiceDetail== null)
            throw new GeneralInventoryException("Ocurrió un error, el recibo no tiene detalle!");

        if (invoice.isSalidaBodega() == true) {
            throw new GeneralInventoryException("La factura ya fué despachada de la bodega!");
        }
        //Se recorre el detalle del recibo
        for (InvoiceDetail detalle: invoiceDetail) {
            //Solo se procesan productos
            if (detalle.getType().equals(DETALLE_RECIBO_PRODUCTO)) {

                if (detalle.getInventario() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el inventario!");
                if (detalle.getInventario().getProducto() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");

                     this.contextoSalidaService.aplicarSalida(detalle.getInventario().getBodega().getTienda(),
                            detalle.getInvoice().getId(),
                            detalle.getId(),
                            detalle.getInventario().getBodega(),
                            detalle.getInventario().getProducto(),
                            detalle.getInventario().getBarcode(),
                            detalle.getQuantity(),
                            detalle.getPrice(),
                             false,
                             false,
                             isSalidaPos);

                    this.nuevaSalidaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                            detalle.getInvoice().getId(), tipoDocumento,  detalle.getQuantity(),
                            detalle.getPrice(), detalle.getId());
                    InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
                    this.actualizarInventarioBodegaVentaDetalle(inventarioBodega, getNameMonthString(), Integer.parseInt(getYearString()), detalle.getQuantity());
                    detalle.setSalidaBodega(true);
                    //TODO: Agregar el flag salida_bodeega y ponerlo en true en cada producto procesado

            }
        }

        invoice.setSalidaBodega(true); //TODO: Setear en true solo si se procesan todos los productos del detalle
        this.invoiceRepository.save(invoice);

        salidaExitosa = true;

        return salidaExitosa;
    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  salidaRequisicionBodega(RequisicionBodega requisicion, Integer bodegaId) throws Exception{

        boolean salidaExitosa = false;
        List<RequisicionBodegaDetalle> requisicionBodegaDetalle = requisicion.getRequisicionBodegaDetalle();
        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_REQUISICION);

        if (requisicionBodegaDetalle== null)
            throw new GeneralInventoryException("Ocurrió un error, la requisicion no tiene detalle!");

        if (requisicion.isSalidaBodega() == true) {
            throw new GeneralInventoryException("La requisición ya fué despachada de la bodega!"); //TODO: Implementar excepciones para estos casos de uso
        }
        //Se recorre el detalle de la requisicion
        for (RequisicionBodegaDetalle detalle: requisicionBodegaDetalle) {

                if (detalle.getInventario() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
                if (detalle.getInventario().getProducto() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");

                    this.contextoSalidaService.aplicarSalida(detalle.getInventario().getTienda(),
                            detalle.getId(),
                            detalle.getRequisicionBodega().getId(),
                            detalle.getInventario().getBodega(),
                            detalle.getInventario().getProducto(),
                            detalle.getInventario().getBarcode(),
                            detalle.getCantidadDespachada(),
                            detalle.getPrecioUnidad(),
                            false,
                            false,
                            false);

                    this.nuevaSalidaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                            detalle.getRequisicionBodega().getId(), tipoDocumento, detalle.getCantidadDespachada(),
                            detalle.getPrecioUnidad(), detalle.getId());
                    InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
                    this.actualizarInventarioBodegaVentaDetalle(inventarioBodega, getNameMonthString(), Integer.parseInt(getYearString()), detalle.getCantidadDespachada());
                    detalle.setSalidaBodega(true);
                    //TODO: Agregar el flag salida_bodeega y ponerlo en true en cada producto procesado


        }

        requisicion.setSalidaBodega(true); //TODO: Setear en true solo si se procesan todos los productos del detalle
        this.requisicionBodegaRepository.save(requisicion);

        salidaExitosa = true;

        return salidaExitosa;
    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  salidaNotaCreditoProveedorBodega(BillPayNotaCredito billPayNotaCredito, Integer bodegaId) throws Exception{

        boolean salidaExitosa = false;
        List<BillPayNotaCreditoDetail> details = billPayNotaCredito.getDetails();
        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_NC_PROVEEDOR);

        if (details== null)
            throw new GeneralInventoryException("Ocurrió un error, la nota de credito del proveedor  no tiene detalle!");

        if (!billPayNotaCredito.getStatus().equals( BILL_PAY_NOTA_CREDITO_EMITIDA) ) {
            throw new GeneralInventoryException("La nota de credito debe estar en estado emitida para ser despachada!"); //TODO: Implementar excepciones para estos casos de uso
        }

        if (billPayNotaCredito.getSalidaBodega() == true) {
            throw new GeneralInventoryException("La nota de credito del proveedor ya fué despachada de la bodega!"); //TODO: Implementar excepciones para estos casos de uso
        }
        //Se recorre el detalle de la nc del proveedor
        for (BillPayNotaCreditoDetail detalle: details) {

            if (detalle.getInventario() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se encuentra el inventario afectado por la nc del proveedor!");
            if (detalle.getInventario().getProducto() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto de la nc del proveedor!");

            this.contextoSalidaService.aplicarSalida(detalle.getInventario().getTienda(),
                    detalle.getId(),
                    detalle.getBillPayNotaCredito().getId(),
                    detalle.getInventario().getBodega(),
                    detalle.getInventario().getProducto(),
                    detalle.getInventario().getBarcode(),
                    detalle.getQuantity(),
                    detalle.getPrice(),
                    false,
                    true,
                    false);

            this.nuevaSalidaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                    detalle.getBillPayNotaCredito().getId(), tipoDocumento, detalle.getQuantity(),
                    detalle.getPrice(), detalle.getId());
            InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
            this.actualizarInventarioBodegaVentaDetalle(inventarioBodega, getNameMonthString(), Integer.parseInt(getYearString()), detalle.getQuantity());
            detalle.setSalidaBodega(true);
            //TODO: Agregar el flag salida_bodeega y ponerlo en true en cada producto procesado


        }

        billPayNotaCredito.setSalidaBodega(true); //TODO: Setear en true solo si se procesan todos los productos del detalle
        this.billPayNotaCreditoRepository.save(billPayNotaCredito);

        salidaExitosa = true;

        return salidaExitosa;
    }




    @Transactional(rollbackFor = {Exception.class})
    public synchronized InventarioBodega nuevoInventarioBodega(Bodega bodega, Producto producto, Double cantidad) throws Exception {

       InventarioBodega inventarioBodega = new InventarioBodega();

        inventarioBodega.setId(0);
        inventarioBodega.setBodega(bodega);
        inventarioBodega.setProducto(producto);
        inventarioBodega.setCantidadDisponible(cantidad);
        inventarioBodega.setCantidadCongelada(0d);
        inventarioBodega.setCantidadApartado(0d);
        inventarioBodega.setCantidadCotizada(0d);
        inventarioBodegaRepository.save(inventarioBodega);

        return inventarioBodega;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  entradaBodega(BillPay billPay, Integer bodegaId) throws Exception{
        boolean exitoso = false;
        List<BillPayDetail> billPayDetails = billPay.getDetails();
        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_COMPRA);

        if (billPayDetails== null)
            throw new GeneralInventoryException("Ocurrió un error, la compra no tiene detalle!");

        /*  Comentado por Felix Saborio 21/06/2021 para que procese el caso de
            CxC que pasan directo al inventario
            if (billPay.getIngresadoBodega() == true)
            throw new GeneralInventoryException("La compra ya fué procesada en bodega!"); //TODO: Implementar excepciones para estos casos de uso
        */

        //Se recorre el detalle de la compra recibo
        for (BillPayDetail detalle: billPayDetails) {
            //Solo se procesan productos
            if (detalle.getType().equals(DETALLE_RECIBO_PRODUCTO)) {

                    //Asignar el inventario id al detalle del billPay
                    Inventario inventario;

                    if (detalle.getBodega() == null)
                        throw new GeneralInventoryException("La bodega es un detalle requerido para CxC!");

                    if (detalle.getProducto() == null)
                        throw new GeneralInventoryException("El producto es un dato requerido para el detalle de la CxC!");

                    if (detalle.getBarcode() == null || detalle.getBarcode().equals(""))
                        throw new GeneralInventoryException("El código de barras es un dato requerido para el detalle de la CxC!");


                  inventario = this.contextoEntradaService.aplicarEntrada(detalle.getBodega().getTienda(),
                                                                        detalle.getBillPay().getId(),
                                                                        detalle.getId(),
                                                                        detalle.getBodega(),
                                                                        detalle.getProducto(),
                                                                        detalle.getBarcode(),
                                                                        detalle.getQuantity(),
                                                                        detalle.getPrice(),
                                                                        false,
                                                                       "");

                //Asignar el id del inventario al detalle del bill pay
                detalle.setInventario(inventario);

                if (detalle.getInventario() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo crear/actualizar el inventario para el producto: " +  detalle.getProducto().getDescripcionEspanol()  +  ", Bodega: " + detalle.getBodega().getName());


                this.nuevaEntradaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                            detalle.getBillPay().getId(), tipoDocumento, detalle.getQuantity(),
                            detalle.getPrice(), detalle.getId());
                    this.actualizarInventarioBodegaEntrada(detalle.getInventario().getBodega(), detalle.getInventario().getProducto(), detalle.getQuantity(), false, "");
                    detalle.setIngresadoBodega(true);
                    //TODO: agregar flag  para llevar el control de cada articulo  ingresado a la bodega
                }
            }

        billPay.setIngresadoBodega(true); //TODO: poner en true solo si se procesaron todos los articulos
        this.billPayRepository.save(billPay);

        return true;

    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  entradaRefundableBodega(Refundable refundable, Integer bodegaId) throws Exception{
        boolean exitoso = false;
        List<RefundableDetail> refundableDetails = refundable.getDetails();
        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_REEMBOLSO);

        if (refundableDetails== null) {
            throw new GeneralInventoryException("Ocurrió un error, el reembolso no tiene detalle!");
        }
        if (refundable.getIngresadoBodega() == true) {
            throw new GeneralInventoryException("El reembolso ya fué procesada en bodega!"); //TODO: Implementar excepciones para estos casos de uso
        }

        //Se recorre el detalle de la compra recibo
        for (RefundableDetail detalle: refundableDetails) {
            //Solo se procesan productos
            if (detalle.getTypeLine().equals(DETALLE_REEMBOLSO_PRODUCTO)) {


                    if (detalle.getBodega() == null)
                        throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
                    if (detalle.getProducto() == null)
                        throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");
                    Inventario inventario;
                    inventario= this.contextoEntradaService.aplicarEntrada(detalle.getBodega().getTienda(),
                                                                           detalle.getRefundable().getId(),
                                                                           detalle.getId(),
                                                                           detalle.getBodega(),
                                                                           detalle.getProducto(),
                                                                           detalle.getBarcode(),
                                                                           detalle.getQuantity(),
                                                                           detalle.getPrice(),
                                                                           false,
                                                                            "");
                    detalle.setInventario(inventario);
                    if (detalle.getInventario() == null)
                      throw new GeneralInventoryException("Ocurrió un error, no se pudo crear/actualizar el inventario para el producto: " +  detalle.getProducto().getDescripcionEspanol()  +  ", Bodega: " + detalle.getBodega().getName());

                    this.nuevaEntradaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                            detalle.getRefundable().getId(), tipoDocumento,  detalle.getQuantity(),
                            detalle.getPrice(), detalle.getId());

                this.actualizarInventarioBodegaEntrada(detalle.getInventario().getBodega(), detalle.getInventario().getProducto(), detalle.getQuantity(), false, "");
                    detalle.setIngresadoBodega(true);
                    //TODO: agregar flag  para llevar el control de cada articulo  ingresado a la bodega
                }
            }

        refundable.setIngresadoBodega(true); //TODO: poner en true solo si se procesaron todos los articulos
        this.refundableRepository.save(refundable);

        return true;

    }


    Bodega getBodegaDesecho(String codigoParametro) throws Exception {
        Bodega bodega;

        GeneralParameter parameter = generalParameterService.getByCode(codigoParametro);
        String bodegaName = parameter.getVal();
        bodega = this.BodegaRepository.getBodegaByName(bodegaName);

        return bodega;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  entradaDevolucionBodegaNotaCredito(Devolucion devolucion, DevolucionDetalle devolucionDetalle, MotivoDevolucion motivoDevolucion) throws Exception{

        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_DEVOLUCION);

        if (devolucionDetalle== null) {
            throw new GeneralInventoryException("Ocurrió un error, la devolucion por nota de credito no tiene detalle!");
        }
        if (devolucion.getIngresadoBodega() == true) {
            throw new GeneralInventoryException("La devolución por nota de credito ya fué procesada en bodega!"); //TODO: Implementar excepciones para estos casos de uso
        }
        if (motivoDevolucion == null) {
            throw new GeneralInventoryException("Ocurrió un error, el motivo de devolución es obligatorio!");
        }
        if (devolucionDetalle.getInventario() == null) {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
        }
        if (devolucionDetalle.getInventario().getProducto() == null) {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");
        }

        Bodega bodegaDestino = devolucionDetalle.getInventario().getBodega();
        devolucionDetalle.setMotivoDevolucion(motivoDevolucion);

        if (devolucionDetalle.getMotivoDevolucion().isIngresaBodega() == false) {
            //Todo: Escribir metodo para obtener bodega de desecho
            bodegaDestino = getBodegaDesecho(BODEGA_DESECHO);
            if (bodegaDestino == null){
                throw new GeneralInventoryException("Bodega de desecho no encontrada!");
            }

        }

        this.contextoEntradaService.aplicarEntrada(devolucionDetalle.getInventario().getBodega().getTienda(),
                devolucionDetalle.getDevolucion().getId(),
                devolucionDetalle.getId(),
                bodegaDestino,
                devolucionDetalle.getInventario().getProducto(),
                devolucionDetalle.getInventario().getBarcode(),
                devolucionDetalle.getCantidad(),
                devolucionDetalle.getPrecio(),
                true,
                "");

        InvoiceNotaCreditoDetail detalleNotaCredito = this.invoiceNotaCreditoRepository.findInvoiceNotaCreditoDetalleById(devolucionDetalle.getLineaParentId());
        if (detalleNotaCredito == null){
            throw new GeneralInventoryException("No se encontró la nota de credito detalle de la devolución");
        }
        this.nuevaEntradaInventario(devolucionDetalle.getInventario(), detalleNotaCredito.getCostCenter().getId(),
                devolucionDetalle.getDevolucion().getId(), tipoDocumento, devolucionDetalle.getCantidad(),
                devolucionDetalle.getPrecio(), devolucionDetalle.getId());
        this.actualizarInventarioBodegaEntrada(bodegaDestino, devolucionDetalle.getInventario().getProducto(), devolucionDetalle.getCantidad(), true,"");
        detalleNotaCredito.setIngresadoBodega(true);
        invoiceNotaCreditoDetailRepository.save(detalleNotaCredito);
        devolucionDetalle.setIngresadoBodega(true);
        this.devolucionDetalleRepository.save(devolucionDetalle);

        return true;

    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  entradaDevolucionBodegaRequisicion(Devolucion devolucion, DevolucionDetalle devolucionDetalle, MotivoDevolucion motivoDevolucion) throws Exception{

        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_DEVOLUCION);

        if (devolucionDetalle== null) {
            throw new GeneralInventoryException("Ocurrió un error, la devolucion por requisición no tiene detalle!");
        }
        if (devolucion.getIngresadoBodega() == true) {
            throw new GeneralInventoryException("La devolución por requisición ya fué procesada en bodega!"); //TODO: Implementar excepciones para estos casos de uso
        }
        if (motivoDevolucion == null) {
            throw new GeneralInventoryException("Ocurrió un error, el motivo de devolución es obligatorio!");
        }
        if (devolucionDetalle.getInventario() == null) {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
        }
        if (devolucionDetalle.getInventario().getProducto() == null) {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");
        }
        Bodega bodegaDestino = devolucionDetalle.getInventario().getBodega();
        devolucionDetalle.setMotivoDevolucion(motivoDevolucion);

        if (devolucionDetalle.getMotivoDevolucion().isIngresaBodega() == false) {
            //Todo: Escribir metodo para obtener bodega de desecho
            bodegaDestino = getBodegaDesecho(BODEGA_DESECHO);
            if (bodegaDestino == null){
                throw new GeneralInventoryException("Bodega de desecho no encontrada!");
            }

        }

        this.contextoEntradaService.aplicarEntrada(devolucionDetalle.getInventario().getBodega().getTienda(),
                devolucionDetalle.getDevolucion().getId(),
                devolucionDetalle.getId(),
                bodegaDestino,
                devolucionDetalle.getInventario().getProducto(),
                devolucionDetalle.getInventario().getBarcode(),
                devolucionDetalle.getCantidad(),
                devolucionDetalle.getPrecio(),
                true,
                "");
        RequisicionBodegaDetalle requisicionBodegaDetalle = this.requisicionBodegaRepository.findRequisicionBodegaDetalleById(devolucionDetalle.getLineaParentId());
        if (requisicionBodegaDetalle == null){
            throw new GeneralInventoryException("No se encontró requisición de la devolución");
        }
        this.nuevaEntradaInventario(devolucionDetalle.getInventario(), requisicionBodegaDetalle.getCostCenter().getId(),
                devolucionDetalle.getDevolucion().getId(), tipoDocumento, devolucionDetalle.getCantidad(),
                devolucionDetalle.getPrecio(), devolucionDetalle.getId());
        this.actualizarInventarioBodegaEntrada(bodegaDestino, devolucionDetalle.getInventario().getProducto(), devolucionDetalle.getCantidad(), true, "");
        devolucionDetalle.setIngresadoBodega(true);

        requisicionBodegaDetalle.setQuantityReturned(requisicionBodegaDetalle.getQuantityReturned() + devolucionDetalle.getCantidad());
       //TODO: Salvar detalle de la requisicion para persistir la cantidad retornada
        requisicionBodegaDetalleRepository.save(requisicionBodegaDetalle);
        devolucionDetalleRepository.save(devolucionDetalle);
       return true;

    }



    //Felix Saborio 10/05/2021 Operaciones de reserva, cotizacion y congelamiento de inventario de bodega

    //Metodos para ordenes de compra

   /* @Transactional(rollbackFor = {Exception.class})
    public synchronized  void congelarSaldoBodega(PurchaseOrderClient purchaseOrderClient) throws Exception{


        List<PurchaseOrderClientDetail> purchaseOrderClientDetail = purchaseOrderClient.getDetails();


        if (purchaseOrderClientDetail== null)
            throw new GeneralInventoryException("Ocurrió un error, la orden de compra no tiene detalle!");

        //Se recorre el detalle la orden de compra
        for (PurchaseOrderClientDetail detalle: purchaseOrderClientDetail) {
            //Solo se procesan productos
            if (detalle.getType().equals(DETALLE_RECIBO_PRODUCTO)) {

                if (detalle.getInventario() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega!");
                if (detalle.getInventario().getProducto() == null)
                    throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto!");

                this.congelarSaldoBodegaProducto(detalle.getInventario().getBodega(),
                        detalle.getInventario().getProducto(),
                        detalle.getQuantity());
            }
        }


    }*/


    //Metodos para congelar inventario de bodega por medio del invoice
    /* Felix Saborio 9-7-2021: Metodo para congelar el inventario de bodega para una factura */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void congelarInventarioBodegaInvoice(Invoice invoice){

        if (invoice.getStatus().equals(INVOICE_STATUS_PENDIENTE)){
            if (invoice.getDetails() != null){
                for(InvoiceDetail detalle : invoice.getDetails()){
                    if (detalle.getType().equals(INVOICE_DETAIL_TIPO_PRODUCTO)) {

                        InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
                        if (inventarioBodega == null) {
                            throw new GeneralInventoryException("Error: Artículo del detalle  de la factura no encontrado");
                        }
                        Double disponible = inventarioBodega.getCantidadDisponible() - inventarioBodega.getCantidadCongelada();
                        if (disponible >= detalle.getQuantity()) {
                            inventarioBodega.setCantidadCongelada(inventarioBodega.getCantidadCongelada() + detalle.getQuantity());
                            this.inventarioBodegaRepository.save(inventarioBodega);
                        } else {
//                            throw new GeneralInventoryException("No hay cantidad disponible para reservar para el articulo: " + detalle.getInventario().getProducto().getDescripcionEspanol());
                            throw new NotAvailableItemsException("No hay cantidad disponible para reservar para el articulo: " + detalle.getInventario().getProducto().getDescripcionEspanol() + ".\r\n" +
                                    " Actualmente la cantidad disponible es de: " + disponible);
                        }

                    }
                }
            }else {
                throw new GeneralInventoryException("No se encontró un detalle de Factura para reservar!");
            }
        }

    }

    /* Felix Saborio 12-7-2021: Metodo para congelar el inventario de bodega para una requisición */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void congelarIventarioRequisicionBodega(RequisicionBodega requisicionBodega){

        if (requisicionBodega.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)){
            if (requisicionBodega.getRequisicionBodegaDetalle() != null){
                for(RequisicionBodegaDetalle detalle : requisicionBodega.getRequisicionBodegaDetalle()){
                    InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
                    if (inventarioBodega == null){
                        throw new GeneralInventoryException("Error: Artículo del detalle  de RQ no encontrado");
                    }
                    Double disponible = inventarioBodega.getCantidadDisponible() - inventarioBodega.getCantidadCongelada();
                    if (disponible >= detalle.getCantidadDespachada()){
                        inventarioBodega.setCantidadCongelada(inventarioBodega.getCantidadCongelada()+ detalle.getCantidadDespachada());
                        this.inventarioBodegaRepository.save(inventarioBodega);
                    } else {
                        throw new NotAvailableItemsException("No hay cantidad disponible para reservar para el articulo: " + detalle.getInventario().getProducto().getDescripcionEspanol() +
                                " Actualmente la cantidad disponible es de: " + disponible);
                    }
                }
            }else {
                throw new GeneralInventoryException("No se encontró un detalle de RQ para reservar!");
            }
        }

    }


    /* Felix Saborio 26-7-2021: Metodo para descongelar el inventario de bodega para una requisición */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized RequisicionBodega descongelarInventarioRequisicionBodega(RequisicionBodega requisicionBodega){

        if (!requisicionBodega.getEstado().equals(REQUISICION_BODEGA_ESTADO_APROBADO)){
            throw new GeneralInventoryException("Solo se puede descongelar requisiciones aprobadas!");
        }

        if (requisicionBodega == null){
            throw new GeneralInventoryException("La requisicion no fué encontrada!");
        }

        if (!requisicionBodega.isSalidaBodega()){
            if (requisicionBodega.getRequisicionBodegaDetalle() != null){
                for(RequisicionBodegaDetalle detalle : requisicionBodega.getRequisicionBodegaDetalle()){
                    InventarioBodega inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(detalle.getInventario().getBodega().getId(), detalle.getInventario().getProducto().getId());
                    if (inventarioBodega == null){
                        throw new GeneralInventoryException("Error: Artículo del detalle  de RQ no encontrado");
                    }
                    inventarioBodega.setCantidadCongelada(inventarioBodega.getCantidadCongelada() - detalle.getCantidadDespachada());
                    this.inventarioBodegaRepository.save(inventarioBodega);
                }
                requisicionBodega.setEstado(REQUISICION_BODEGA_ESTADO_EDICION);
            }else {
                throw new GeneralInventoryException("No se puede descongelar requisiciones con el detalle vacío!");
            }
        }

        return this.requisicionBodegaRepository.save(requisicionBodega);

    }



    @Transactional(rollbackFor = {Exception.class})
    public synchronized void congelarSaldoBodegaProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        InventarioBodega inventarioBodega;
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());

        if (inventarioBodega == null) {
            throw new GeneralInventoryException("No hay Inventario disponible del producto: " + producto.getDescripcionEspanol() + ", en la bodega: " + bodega.getName());
        }

        if (inventarioBodega.getCantidadNeta() >= cantidad) {
            //efectuar la reserva de la cantdad congelada por producto y bodega
            this.congelarSaldoProducto(bodega, producto, cantidad);
        } else {
            throw new GeneralInventoryException("No hay reservas suficientes del producto: " + producto.getDescripcionEspanol() + ", en la bodega: " + bodega.getName());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    private void congelarSaldoProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        InventarioBodega inventarioBodega;
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());

        //actualizar el saldo congelado de la bodega
        if (inventarioBodega != null) {
            inventarioBodega.setCantidadCongelada(inventarioBodega.getCantidadCongelada() + cantidad);
            inventarioBodega.setCantidadDisponible(inventarioBodega.getCantidadDisponible() - cantidad);
            this.inventarioBodegaRepository.save(inventarioBodega);
        } else {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el inventario por bodega!");
        }
    }



//Metodos para cotizaciones

    //Metodo para incrementar la cantidad cotizada en el inventario de la bodega
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void  incrementarCantidadCotizadaBodega(Cotizacion cotizacion) throws Exception {

        List<CotizacionDetalle> cotizacionDetalle = cotizacion.getDetalles();

        if (cotizacionDetalle == null) {

        }
        //Se recorre el detalle de la cotizacion
        for (CotizacionDetalle dc : cotizacionDetalle) {
            if (dc.getCotizacion().getBodega() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega de la cotización!");
            if (dc.getInventario().getProducto() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto de la linea " + dc.getLineNumber());

            this.cotizarBodegaProducto(dc.getCotizacion().getBodega(),
                    dc.getInventario().getProducto(),
                    dc.getCantidad());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    private synchronized void cotizarBodegaProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        InventarioBodega inventarioBodega;
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());

        if (inventarioBodega.getCantidadNeta() >= cantidad) {
            //efectuar la reserva de la cantidad cotizda del producto por bodega
            this.reservarCantidadCotizadaBodegaProducto(bodega, producto, cantidad);
        } else {
            throw new GeneralInventoryException("No hay reservas suficientes del producto: " + producto.getDescripcionEspanol() + ", en la bodega: " + bodega.getName());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    private void reservarCantidadCotizadaBodegaProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        InventarioBodega inventarioBodega;
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());

        //actualizar la cantidad cotizada del producto por bodega
        if (inventarioBodega != null) {
            inventarioBodega.setCantidadCotizada(inventarioBodega.getCantidadCotizada() + cantidad);
            inventarioBodega.setCantidadDisponible(inventarioBodega.getCantidadDisponible() - cantidad);
            this.inventarioBodegaRepository.save(inventarioBodega);
        } else {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el inventario por bodega!");
        }
    }


    //Metodo para restaurar la cantidad cotizada en el inventario de la bodega
    @Transactional(rollbackFor = {Exception.class})
    public synchronized void  restaurarCantidadCotizadaBodega(Cotizacion cotizacion) throws Exception {

        List<CotizacionDetalle> cotizacionDetalle = cotizacion.getDetalles();

        if (cotizacionDetalle == null) {
            throw new GeneralInventoryException("Ocurrió un error, la cotizacion no tiene detalle!");
        }
        //Se recorre el detalle de la cotizacion
        for (CotizacionDetalle dc : cotizacionDetalle) {
            if (dc.getCotizacion().getBodega() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener la  bodega de la cotización!");
            if (dc.getInventario().getProducto() == null)
                throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el  producto de la linea " + dc.getLineNumber());

            this.restaurarCantidadCotizadaBodegaProducto(dc.getCotizacion().getBodega(),
                    dc.getInventario().getProducto(),
                    dc.getCantidad());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    private synchronized void restaurarCantidadCotizadaBodegaProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        if (cantidad > 0) {
            //efectuar restauracion de la cantidad cotizada del producto por bodega
            this.liberarCantidadCotizadaBodegaProducto(bodega, producto, cantidad);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void liberarCantidadCotizadaBodegaProducto(Bodega bodega, Producto producto, Double cantidad) throws Exception{

        InventarioBodega inventarioBodega;
        inventarioBodega = this.inventarioBodegaRepository.findByBodegaAndProducto(bodega.getId(), producto.getId());

        //libearar la cantidad cotizada del producto por bodega
        if (inventarioBodega != null) {
            inventarioBodega.setCantidadCotizada(inventarioBodega.getCantidadCotizada() - cantidad);
            inventarioBodega.setCantidadDisponible(inventarioBodega.getCantidadDisponible() + cantidad);
            this.inventarioBodegaRepository.save(inventarioBodega);
        } else {
            throw new GeneralInventoryException("Ocurrió un error, no se pudo obtener el inventario por bodega!");
        }
    }

    //Felix Saborio 13/7/2021 : Validaciones traslados entre bodegas
    public void validacionesTrasladoBodega(Traslado traslado) throws Exception{
        if (traslado == null){
            throw new GeneralInventoryException("No se encuentra el documento de traslado");
        }

        if (traslado.getEstado().equals(TRASLADO_STATUS_TRASLADADO)) {
            throw new GeneralInventoryException("Solo se puede procesar documentos con el estado distinto de Trasladado");
        }

        if (traslado.getDetails() == null){
            throw new GeneralInventoryException("El traslado no cuenta con  detalle!");
        }

        if (traslado.getBodegaOrigen() == null){
            throw new GeneralInventoryException("La bodega origen es un dato requerido en el  traslado!");
        }

        if (traslado.getBodegaDestino() == null){
            throw new GeneralInventoryException("La bodega destino es un dato requerido en el  traslado!");
        }

        if (traslado.getBodegaOrigen().getId() == traslado.getBodegaDestino().getId()){
            throw new GeneralInventoryException("La bodega origen y destino no pueden ser la misma!");
        }
    }

    //Felix Saborio 13/7/2021 : Traslados entre bodegas
    @Transactional(rollbackFor = {Exception.class})
    public synchronized Traslado  trasladoBodega(Integer id) throws Exception {

        Traslado traslado =  this.trasladoRepository.findTrasladoById(id);

        validacionesTrasladoBodega(traslado);

        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_TRASLADO);

        if (tipoDocumento == null){
            throw new GeneralInventoryException("Tipo de documento para traslados de bodega no encontrado!");
        }


        //Ciclo para hacer los traslados entre la bodega origen y la bodega destino
        for (TrasladoDetalle detalle : traslado.getDetails()){

            //////////////////////////////////////////////////////////////////
            //Operaciones para registrar la salida en la bodega  origen
            //////////////////////////////////////////////////////////////////
            this.contextoSalidaService.aplicarSalida(detalle.getInventario().getTienda(),
                    detalle.getId(),
                    detalle.getTraslado().getId(),
                    detalle.getTraslado().getBodegaOrigen(),
                    detalle.getInventario().getProducto(),
                    detalle.getInventario().getBarcode(),
                    detalle.getCantidadTraslado(),
                    detalle.getInventario().getCostoSalida(),
                    true,
                    false,
                    false);

            this.nuevaSalidaInventario(detalle.getInventario(), detalle.getCostCenter().getId(),
                    detalle.getTraslado().getId(), tipoDocumento, detalle.getCantidadTraslado(),
                    detalle.getInventario().getCostoSalida(), detalle.getId());

            //////////////////////////////////////////////////////////////////////
            //Operaciones par registrar una entrada en la bodega destino
            //////////////////////////////////////////////////////////////////////
            Bodega bodegaDestino = detalle.getTraslado().getBodegaDestino();

            if (detalle.getTrasladoJustificacion() ==  null) {
                throw  new GeneralInventoryException("No se permiten detalle de traslado sin justificacion!");
            }

            if (detalle.getTrasladoJustificacion().getEsDesecho()){
                bodegaDestino = this.getBodegaDesecho(BODEGA_DESECHO);
                if (bodegaDestino == null){
                    throw  new GeneralInventoryException("No se pudo obtener la bodega de desecho!");
                }
            }
            Inventario inventario = this.contextoEntradaService.aplicarEntrada(bodegaDestino.getTienda(),
                    detalle.getTraslado().getId(),
                    detalle.getId(),
                    bodegaDestino,
                    detalle.getInventario().getProducto(),
                    detalle.getInventario().getBarcode(),
                    detalle.getCantidadTraslado(),
                    detalle.getInventario().getCostoEntrada(),
                    false,
                    "");


            if (inventario == null) {
                throw new GeneralInventoryException("Ocurrió un error, no se pudo crear/actualizar el inventario para el producto: " + detalle.getInventario().getProducto().getDescripcionEspanol() + ", Bodega: " + bodegaDestino.getName());
            }
            this.nuevaEntradaInventario(inventario, detalle.getCostCenter().getId(),
                    detalle.getTraslado().getId(), tipoDocumento, detalle.getCantidadTraslado(),
                    detalle.getInventario().getCostoEntrada(), detalle.getId());
            this.actualizarInventarioBodegaEntrada(bodegaDestino, detalle.getInventario().getProducto(), detalle.getCantidadTraslado(), false,"");

        }

        traslado.setEstado(TRASLADO_STATUS_TRASLADADO);

        return this.trasladoRepository.save(traslado);
    }


    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  initializerBodega(InitInventario initInventario) throws Exception{
        boolean exitoso = false;
        List<InitInventarioDetalle> initInventarioDetalle = initInventario.getDetalles();
        GeneralParameter parameterBodega = generalParameterService.getByCode(PARAMETER_IMPORT_BODEGA);
        GeneralParameter parameterCentroCostoImportacion = generalParameterService.getByCode(PARAMETER_IMPORT_CENTRO_COSTO);

        if (parameterBodega == null || parameterBodega.getIntVal() == null){
            throw new GeneralInventoryException("Parametro de bodega de importacion no definido");
        }

        if (parameterCentroCostoImportacion == null || parameterCentroCostoImportacion.getIntVal() == null){
            throw new GeneralInventoryException("Parametro de centro de costo de importacion no definido");
        }



        Bodega bodega = this.bodegaRepository.getById(parameterBodega.getIntVal());

        if (bodega == null){
            throw new GeneralInventoryException("Bodega de importación no ha sido encontrada");
        }

        CostCenter costCenter = this.costCenterRepository.findCostCenterByid(parameterCentroCostoImportacion.getIntVal());

        if (costCenter == null){
            throw new GeneralInventoryException("Centro de costo de importación no ha sido encontrado");
        }



        TipoDocumento tipoDocumento = this.tipoDocumentoRepository.findbyTipo(TIPO_DOCUMENTO_INITIALIZER);
        //Obtener de parametros generales la estrategia para actualizar el producto
        GeneralParameter generalParameterCargaInventario = this.generalParameterService.getByCode(PARAMETER_IMPORT_ACTUALIZA_INVENTARIO);


        if (generalParameterCargaInventario == null) {
            throw new GeneralInventoryException("Error Inventario: Parametro de actualizacion de inventario no definido : " + PARAMETER_IMPORT_ACTUALIZA_INVENTARIO );
        }

        if (initInventarioDetalle == null) {
            throw new GeneralInventoryException("Ocurrió un error, el initializador del inventario  no tiene detalle!");
        }

        if (bodega == null) {
            throw new GeneralInventoryException("Se requiere una bodega default para inicializar el inventario!");
        }

        if (costCenter == null) {
            throw new GeneralInventoryException("Se requiere un centro de costo default para inicializar el inventario!");
        }

        if (tipoDocumento == null) {
            throw new GeneralInventoryException("Se requiere un Tipo de documento default para inicializar!");
        }


        //Se recorre el detalle de la compra recibo
        for (InitInventarioDetalle detalle: initInventarioDetalle) {
            //Solo se procesan productos
            if (detalle.getTipo().equals(TIPO_INIT_INVENTARIO_DETALLE_VARIATION) || detalle.getTipo().equals(TIPO_INIT_INVENTARIO_DETALLE_VARIATION)) {
//                if (detalle.getCantidadInventario() != null && detalle.getCantidadInventario()> 0d){
                if (detalle.getCantidadInventario() != null){ // Que pasa si es 0 ?
                    //Asignar el inventario id al detalle del init_inventario
                    Inventario inventario;
                    Producto p = this.productoRepository.findProductoById(detalle.getProductoId());

                    if (p == null) {
                        throw new GeneralInventoryException("No fué posible cargar el producto: " + detalle.getCodigo() + ", Sku: " + detalle.getSku());
                    }
                    inventario = this.contextoEntradaService.aplicarEntrada(bodega.getTienda(),
                            detalle.getInitInventario().getId(),
                            detalle.getId(),
                            bodega,
                            p,
                            detalle.getCodigo(),
                            detalle.getCantidadInventario(),
                            detalle.getPrecioCosto(),
                            false,
                            generalParameterCargaInventario.getVal());




                    this.nuevaEntradaInventario(inventario, costCenter.getId(),
                            detalle.getInitInventario().getId(), tipoDocumento, detalle.getCantidadInventario(),
                            detalle.getPrecioList(), detalle.getId());
                    this.actualizarInventarioBodegaEntrada(bodega, p, detalle.getCantidadInventario(), false, generalParameterCargaInventario.getVal());

                }
            }
        }

        initInventario.setEstado(INIT_INVENTARIO_ESTADO_IMPORTADO);
        this.initInventarioRepository.save(initInventario);

        return true;

    }


    }
