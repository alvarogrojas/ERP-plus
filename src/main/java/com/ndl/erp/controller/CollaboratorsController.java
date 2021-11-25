package com.ndl.erp.controller;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.dto.CollaboratorDTO;
import com.ndl.erp.dto.CollaboratorsDTO;
import com.ndl.erp.dto.CountryDataDTO;
import com.ndl.erp.services.CollaboratorService;
import com.ndl.erp.services.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorsController {

    @Autowired
    private CollaboratorService service;


    @PostMapping(path="/save")
    public @ResponseBody
    Collaborator create(@RequestBody Collaborator c) {
        return service.save(c);
    }





    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CollaboratorsDTO getCollaboratoreList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getCollaborators(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    CollaboratorDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getCollaborator(id);
    }

    @GetMapping(path = "/cantones")
    public @ResponseBody
    CountryDataDTO getCantones(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getCantones(id);
    }

    @GetMapping(path = "/districts")
    public @ResponseBody
    CountryDataDTO getDistricts(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getDistricts(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    CollaboratorDTO getNewCentroCostos(


    ) {
        return this.service.getCollaborator(null);
    }


}
