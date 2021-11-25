package com.ndl.erp.controller;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.dto.CentroCostoDTO;
import com.ndl.erp.dto.CentroCostosDTO;
import com.ndl.erp.dto.ClientDTO;
import com.ndl.erp.dto.ClientsDTO;
import com.ndl.erp.services.CentroCostosService;
import com.ndl.erp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    @Autowired
    private ClientService clientService;


    @PostMapping(path="/save")
    public @ResponseBody
    Client create(@RequestBody Client c) {
        return clientService.save(c);
    }



//    @GetMapping(path = "/list")
//    public @ResponseBody
//    ClientsDTO getCentroCostosList(
//                              @RequestParam(value = "filter", required = false) String filter
//
//    ) {
//        return this.clientService.getClients(filter);
//    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    ClientsDTO getClienteList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return clientService.getClients(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/list")
    public @ResponseBody
    ClientsDTO getList(
            @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.clientService.getActiveClient(filter);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ClientDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.clientService.getClient(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    ClientDTO getNewCentroCostos(


    ) {
        return this.clientService.getClient();
    }


}
