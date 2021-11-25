package com.ndl.erp.controller;

import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.Descuentos;
import com.ndl.erp.dto.DeductionDTO;
import com.ndl.erp.dto.DeductionsDTO;
import com.ndl.erp.dto.DescuentosDTO;
import com.ndl.erp.services.DeductionService;
import com.ndl.erp.services.DescuentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentosController {

    @Autowired
    private DescuentosService service;


    @PostMapping(path="/save")
    public @ResponseBody
    Descuentos create(@RequestBody Descuentos c) {
        return service.save(c);
    }

//    @GetMapping(path = "/data/list-page")
//    public @ResponseBody
//    DeductionsDTO getList(@RequestParam(value = "filter", required = false) String filter,
//                          @RequestParam Integer pageNumber,
//                          @RequestParam Integer pageSize,
//                          @RequestParam String sortDirection,
//                          @RequestParam String sortField) {
//        return deductionService.getDeductions(filter, pageNumber, pageSize, sortDirection, sortField);
//    }


    @GetMapping(path = "/get")
    public @ResponseBody
    DescuentosDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getDescuentos(id);
    }

    @GetMapping(path = "/get/tipo/referencia")
    public @ResponseBody
    DescuentosDTO getCentroCostos(
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "referenciaId", required = false) Integer referenciaId
    ) {
        return this.service.getDescuentosByTipoAndReferenciaId(tipo, referenciaId);
    }

    @GetMapping(path = "/get/by-tipo")
    public @ResponseBody
    DescuentosDTO getByTipo(
            @RequestParam(value = "tipo", required = false) String tipo
    ) {
        return this.service.getDescuentosByTipo(tipo);
    }

@GetMapping(path = "/new-form")
    public @ResponseBody
    DescuentosDTO getNewDeduction(


    ) {
        return this.service.getDescuentos();
    }


}
