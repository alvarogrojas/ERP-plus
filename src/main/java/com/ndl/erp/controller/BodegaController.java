package com.ndl.erp.controller;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.BodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/bodega")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @Deprecated
    @PostMapping(path="/save-item")
    public @ResponseBody
    InventarioItem create(@RequestBody InventarioItem c) {
        return bodegaService.saveItem(c);
    }

    @PostMapping(path="/save")
    public @ResponseBody
    Bodega saveBodega(@RequestBody Bodega b) throws Exception {
        return bodegaService.save(b);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    BodegaDTO getNewBodega() {
        return this.bodegaService.getBodega();
    }


    @GetMapping(path = "/get")
    public @ResponseBody
    BodegaDTO getBodega(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.bodegaService.getBodegaData(id);
    }

    @GetMapping(path = "/list-page")
    public @ResponseBody
    BodegasDTO getList(@RequestParam(value = "estado", required = false) String estado,
                                @RequestParam Integer pageNumber,
                                @RequestParam Integer pageSize,
                                @RequestParam String sortDirection,
                                @RequestParam String sortField) {
        return bodegaService.getBodegas(estado, pageNumber, pageSize, sortDirection, sortField);
    }


    @PostMapping(path="/add-inventario")
    public @ResponseBody
    ProductsPendingsInDTO addInventario(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam (value="startBillPayDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startBillPayDate,
            @RequestParam (value="endBillPayDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date  endBillPayDate,
            @RequestParam(value = "bodegaId", required = false) Integer bodegaId
            ) throws Exception {
        return bodegaService.addInventory(type, id, startBillPayDate, endBillPayDate, bodegaId);
    }

    @PostMapping(path="/salida-inventario")
    public @ResponseBody
    ProductsPendingsOutDTO salidaInventario(
            @RequestParam(value = "invoiceId", required = true) Integer invoiceId,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date  endDate,
            @RequestParam(value = "bodegaId", required = false) Integer bodegaId) throws Exception {
        return bodegaService.decreaseInventory(type, invoiceId, startDate, endDate, bodegaId);
    }


    @GetMapping(path="/producto")
    public @ResponseBody
    ProductosDTO
    getProductos(@RequestParam(value = "bodegaId", required = false) Integer bodegaId,
                 @RequestParam(value = "filter", required = true) String filter,
                 @RequestParam(value = "esEntrada", required = false) Boolean esEntrada) {
        return bodegaService.getProductos(bodegaId, filter, esEntrada);
    }

    @GetMapping(path="/productos-list")
    public @ResponseBody
    ProductosDTO
    getProductosList(
                 @RequestParam(value = "filter", required = true) String filter
                 ) {
        return bodegaService.getProductos(filter);
    }

    @GetMapping(path="/inventario")
    public @ResponseBody
    InventarioDTO
    getInventarios(@RequestParam(value = "filter", required = true) String filter,
                 @RequestParam(value = "bodegaId", required = false) Integer bodegaId,
                 @RequestParam(value = "productoId", required = false) Integer productoId) {
        return bodegaService.getProductosInventario(filter, bodegaId, productoId);

    }



    @GetMapping(path="/producto-descuento-list")
    public @ResponseBody
    ProductoDescuentoListDTO
    getProductoDescuentosList(@RequestParam(value = "productoId", required = true) Integer productoId) {
        return bodegaService.getDescuentosByProducto(productoId);
    }



    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    BodegaInventarioDTO getBodegaList(@RequestParam(value = "filter", required = false) String filter,
                                      @RequestParam Integer bodegaId,
                                      @RequestParam Integer pageNumber,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortDirection,
                                      @RequestParam String sortField) {
        return bodegaService.getItems(filter,bodegaId, pageNumber, pageSize, sortDirection, sortField);
    }



    @GetMapping(path = "/entradas-detalles-pendientes")
    public @ResponseBody
      InPendingsDTO getEntradasPendientes(@RequestParam(value = "type", required = true) String type,
                                          @RequestParam(value = "id", required = true) Integer id){

        return this.bodegaService.getProductsPendingsInFromBillPay(type, id);

    }

    @GetMapping(path = "/devolucion-detalles-pendientes")
    public @ResponseBody
      InPendingsDTO getDevolucionesPendientes(@RequestParam(value = "type", required = true) String type,
                                          @RequestParam(value = "id", required = true) Integer id){

        return this.bodegaService.getProductsPendingsFromDevoluciones(type, id);

    }


    @GetMapping(path = "/salidas-detalles-pendientes")
    public @ResponseBody
    OutPendingsDTO getSalidasPendientes(@RequestParam(value = "type", required = true) String type,
                                        @RequestParam(value = "id", required = true) Integer id){

        return this.bodegaService.getProductDetailPendingsOutFromInvoice(type, id);

    }


    @GetMapping(path = "/entradas-pendientes")
    public @ResponseBody
    ProductsPendingsInDTO getEntradasPendientes( @RequestParam (value="startBillPayDate",required=false)
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy")  Date startBillPayDate,
                                                 @RequestParam (value="endBillPayDate",required=false)
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy")  Date  endBillPayDate,
                                                 @RequestParam (value="bodegaId",required=false) Integer bodegaId) {


        return this.bodegaService.getProductsPendingsInFromBillPay(startBillPayDate, endBillPayDate, bodegaId);

    }


    @GetMapping(path = "/salidas-pendientes")
    public @ResponseBody
    ProductsPendingsOutDTO getSalidasPendientes( @RequestParam (value="startInvoiceDate",required=false)
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") Date startInvoiceDate,
                                                 @RequestParam (value="endInvoiceDate",required=false)
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") Date  endInvoiceDate,
                                                 @RequestParam (value="bodegaId",required=false) Integer bodegaId) {

        return this.bodegaService.getProductsPendingsOutFromInvoice(startInvoiceDate, endInvoiceDate, bodegaId);

    }

    //Mapping para devoluciones de bodega

    @GetMapping(path = "/devoluciones-pendientes")
    public @ResponseBody
    ProductsPendingReturnDTO getDevolucionesPendientes(@RequestParam (value="startDate",required=false)
                                                       @DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                                                       @RequestParam (value="endDate",required=false)
                                                       @DateTimeFormat(pattern = "dd-MM-yyyy")  Date  endDate,
                                                       @RequestParam (value="bodegaId",required=false) Integer bodegaId) {


        return this.bodegaService.getProductsReturnPendingsInFromInvoiceNotaCredito(startDate, endDate, bodegaId);

    }

    @PostMapping(path="/set-rechazo-entrada")
    public @ResponseBody
//    RechazoEntradaDTO rechazoEntradaBodega(@RequestParam(value = "id", required = true) Integer id,
    Boolean rechazoEntradaBodega(@RequestParam(value = "id", required = true) Integer id,
                                           @RequestParam(value = "type", required = true) String type) throws Exception {
        return bodegaService.rechazoEntrada(id, type);
    }



}
