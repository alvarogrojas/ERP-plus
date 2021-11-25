package com.ndl.erp.controller;

import com.ndl.erp.domain.ContactClient;
import com.ndl.erp.dto.ContactClientDTO;
import com.ndl.erp.dto.ContactClientsDTO;
import com.ndl.erp.dto.Result;
import com.ndl.erp.services.ContactClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts-client")
public class ContactClientsController {

    @Autowired
    private ContactClientService service;


    @PostMapping(path="/save")
    public @ResponseBody
    ContactClient create(@RequestBody ContactClient c) {
        return service.save(c);
    }



    @GetMapping(path = "/list")
    public @ResponseBody
    ContactClientsDTO getCentroCostosList(
                              @RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "clientId", required = false) Integer clientId

    ) {
        return this.service.getContactClients(filter);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    ContactClientsDTO getContactClienteList(@RequestParam(value = "filter", required = false) String filter,
                                            @RequestParam(value = "clientId", required = false) Integer clientId,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getContactClients(filter, clientId, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/active/list")
    public @ResponseBody
    ContactClientsDTO getContactClienteList(@RequestParam(value = "id", required = false) Integer id) {
        return service.getActiveContactClients(id);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ContactClientDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "clientId", required = false) Integer clientId
    ) {
        return this.service.getContactClient(id, clientId);
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    Result delete(@RequestBody ContactClient c) {
        return service.delete(c);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    ContactClientDTO getNewCentroCostos(
        @RequestParam(value = "clientId", required = false) Integer clientId

    ) {
        return this.service.getContactClient(null, clientId);
    }


}
