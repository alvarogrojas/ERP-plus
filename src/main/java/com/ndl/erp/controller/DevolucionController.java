package com.ndl.erp.controller;

import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.DevolucionDetalle;
import com.ndl.erp.dto.DevolucionDTO;
import com.ndl.erp.dto.DevolucionListDTO;
import com.ndl.erp.dto.DevolucionRequisicionDTO;
import com.ndl.erp.dto.ProductsPendingReturnDTO;
import com.ndl.erp.services.BodegaService;
import com.ndl.erp.services.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/devolucion")
public class DevolucionController {


    @Autowired
    DevolucionService devolucionService;

    @Autowired
    BodegaService bodegaService;
    @GetMapping(path = "/new-form")
    public @ResponseBody
    DevolucionDTO getNew(
    ) {
        return this.devolucionService.getDevolucion();
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    DevolucionDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception {
        return this.devolucionService.getDevolucion(id);
    }


    @PostMapping(path="/save")
    public @ResponseBody
    Devolucion create(@RequestBody Devolucion devolucion) throws Exception {
        return devolucionService.save(devolucion);
    }

    @PostMapping(path="/requisicion/save")
    public @ResponseBody
    Devolucion save(@RequestBody DevolucionRequisicionDTO devolucion) throws Exception {
        return devolucionService.saveDevolucionRequisicion(devolucion);
    }

    @GetMapping(path="/change-status")
    public @ResponseBody
    Devolucion changeStatus(
            @RequestParam (value="id",required=false) Integer id,
            @RequestParam (value="status",required=false) String status
    ) throws Exception {
        return devolucionService.updateStatus(id, status);
    }


    @PostMapping(path="/create-devolucion")
    public @ResponseBody
    Devolucion createDevolucion(@RequestParam(value = "id", required = true) Integer id,
                                @RequestParam(value = "type", required = true) String type) throws Exception {
        //return devolucionService.save(devolucion);
        return this.devolucionService.createDevolucion(id, type);

    }

//    @PostMapping(path="/data/list-page")
//    public @ResponseBody
//    Devolucion getDevoluciones(@RequestParam (value="startDate",required=false)
//                                @DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
//                                @RequestParam (value="endDate",required=false)
//                                @DateTimeFormat(pattern = "dd-MM-yyyy")  Date  endDate,
//                                @RequestParam (value="bodegaId",required=false) Integer bodegaId,
//                               @RequestParam (value="estado",required=false) String estado,
//                               @RequestParam Integer pageNumber,
//                               @RequestParam Integer pageSize,
//                               @RequestParam String sortDirection,
//                               @RequestParam String sortField) throws Exception {
//        //return devolucionService.save(devolucion);
//        return null; // DTO que contiene una lista con el listado de devoluciones, obtiene devoluciones por fecha de creacion de
//                    // la devolucion, por bodega y por estado y que sea paginable
//    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    DevolucionListDTO getList(
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam (value="status",required=false) String status,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,

            @RequestParam String sortDirection,
            @RequestParam String sortField
    ) {
        return devolucionService.getDevolucionList(startDate,
                endDate,
                status,
                pageNumber,
                pageSize,
                sortDirection,
                sortField
        );
    }

    @GetMapping(path = "/nc")
    public @ResponseBody
    ProductsPendingReturnDTO getDevolucionesPendientes(@RequestParam (value="startDate",required=false)
                                                       @DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                                                       @RequestParam (value="endDate",required=false)
                                                       @DateTimeFormat(pattern = "dd-MM-yyyy")  Date  endDate,
                                                       @RequestParam (value="bodegaId",required=false) Integer bodegaId) {


        return this.bodegaService.getProductsReturnPendingsInFromInvoiceNotaCredito(startDate, endDate, bodegaId);

    }


    @GetMapping(path = "/set/inventory")
    public @ResponseBody
    DevolucionDetalle get(@RequestParam(value = "devolucionId", required = true) Integer devolucionId,
                          @RequestParam(value = "devolucionDetalleId", required = true) Integer devolucionDetalleId,
                          @RequestParam(value = "motivoId", required = true) Integer motivoId
    ) throws Exception {
        return this.devolucionService.procesarDevolucion(devolucionId,
                devolucionDetalleId,
                motivoId);
    }


}
