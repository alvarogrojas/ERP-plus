package com.ndl.erp.controller;

import com.ndl.erp.domain.BillPayNotaCredito;
import com.ndl.erp.dto.BillPayNotaCreditoDTO;
import com.ndl.erp.dto.BillPayNotaCreditosDTO;
import com.ndl.erp.services.BillPayNotaCreditoService;
import com.ndl.erp.services.BillPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;




@RestController
@RequestMapping("/api/bp-nota-credito")
public class BillPayNotaCreditoController {

    @Autowired
    private BillPayNotaCreditoService billPayNotaCreditoService;

    @Autowired
    private BillPayService billPayService;



    @PostMapping(path="/save")
    public @ResponseBody
    BillPayNotaCredito create(@RequestBody BillPayNotaCredito billPayNotaCredito) throws Exception {
        return this.billPayNotaCreditoService.save(billPayNotaCredito);
    }




    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    BillPayNotaCreditosDTO getList(
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam (value="status",required=false) String status,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,

            @RequestParam String sortDirection,
            @RequestParam String sortField
    ) {
        return this.billPayNotaCreditoService.getInvoiceNotaCreditoList(startDate,
                endDate,
                status,
                pageNumber,
                pageSize,
                sortDirection,
                sortField
        );
    }




    @GetMapping(path = "/get")
    public @ResponseBody
    BillPayNotaCreditoDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception {
        return this.billPayNotaCreditoService.getBillPayeNotaCredito(id);
    }


    @GetMapping(path="/change-status")
    public @ResponseBody
    BillPayNotaCredito changeStatus(
            @RequestParam (value="id",required=false) Integer id,
            @RequestParam (value="status",required=false) String status
    ) throws Exception{
        return billPayNotaCreditoService.updateStatus(id, status);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    BillPayNotaCreditoDTO getNew(
    )

            throws Exception {
        return this.billPayNotaCreditoService.BillPayNotaCredito(null, null);
    }


}

