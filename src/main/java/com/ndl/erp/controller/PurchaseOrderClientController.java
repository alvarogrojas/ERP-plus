package com.ndl.erp.controller;

import com.ndl.erp.domain.PurchaseOrderClient;
import com.ndl.erp.dto.PurchaseOrderClientDTO;
import com.ndl.erp.dto.PurchaseOrderClientsDTO;
import com.ndl.erp.services.PurchaseOrderClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/po-client")
public class PurchaseOrderClientController {

    @Autowired
    private PurchaseOrderClientService service;


    @PostMapping(path="/save")
    public @ResponseBody
    PurchaseOrderClient create(@RequestBody PurchaseOrderClient c) throws Exception{

        return service.save(c);
    }

    @PostMapping(path="/change-status")
    public @ResponseBody
    PurchaseOrderClient changeStatus(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "status", required = true) String status ) throws Exception{

        return service.chageStatus(id, status);
    }



    @GetMapping(path = "/list")
    public @ResponseBody
    PurchaseOrderClientsDTO getCentroCostosList(
                              @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.service.getPurchaseOrderClients(filter);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    PurchaseOrderClientsDTO getPurchaseOrderClienteList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getPurchaseOrderClients(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    PurchaseOrderClientDTO getPurchaseOrder(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getPurchaseOrderClient(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    PurchaseOrderClientDTO getNewPurchaseOrder(


    ) {
        return this.service.getPurchaseOrderClient();
    }


}
