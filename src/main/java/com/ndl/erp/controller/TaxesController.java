package com.ndl.erp.controller;

import com.ndl.erp.domain.Taxes;
import com.ndl.erp.dto.TaxesDTO;
import com.ndl.erp.dto.TaxesListDTO;
import com.ndl.erp.services.TaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/taxes")
public class TaxesController {

    @Autowired
    private TaxesService taxesService;


    @PostMapping(path="/save")
    public @ResponseBody
    Taxes create(@RequestBody Taxes c) {
        return taxesService.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    TaxesListDTO getTaxeseList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return taxesService.getTaxesList(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    TaxesDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.taxesService.getTaxes(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    TaxesDTO getNewCentroCostos(


    ) {
        return this.taxesService.getTaxes(null);
    }


}
