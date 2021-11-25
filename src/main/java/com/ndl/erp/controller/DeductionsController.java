package com.ndl.erp.controller;

import com.ndl.erp.domain.Deductions;

import com.ndl.erp.dto.DeductionDTO;
import com.ndl.erp.dto.DeductionsDTO;

import com.ndl.erp.services.DeductionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deductions")
public class DeductionsController {

    @Autowired
    private DeductionService deductionService;


    @PostMapping(path="/save")
    public @ResponseBody
    Deductions create(@RequestBody Deductions c) {
        return deductionService.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    DeductionsDTO getList(@RequestParam(value = "filter", required = false) String filter,
                          @RequestParam Integer pageNumber,
                          @RequestParam Integer pageSize,
                          @RequestParam String sortDirection,
                          @RequestParam String sortField) {
        return deductionService.getDeductions(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    DeductionDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.deductionService.getDeduction(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    DeductionDTO getNewDeduction(


    ) {
        return this.deductionService.getDeduction(null);
    }


}
