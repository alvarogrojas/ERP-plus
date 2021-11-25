package com.ndl.erp.controller;

import com.ndl.erp.domain.Deductions;
import com.ndl.erp.domain.DeductionsRefunds;
import com.ndl.erp.dto.DeductionDTO;
import com.ndl.erp.dto.DeductionRefundDTO;
import com.ndl.erp.dto.DeductionsDTO;
import com.ndl.erp.dto.DeductionsRefundsDTO;
import com.ndl.erp.services.DeductionService;
import com.ndl.erp.services.DeductionsRefundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deductions-refunds")
public class DeductionsRefundsController {

    @Autowired
    private DeductionsRefundsService service;


    @PostMapping(path="/save")
    public @ResponseBody
    DeductionsRefunds create(@RequestBody DeductionsRefunds c) {
        return service.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    DeductionsRefundsDTO getList(@RequestParam(value = "filter", required = false) String filter,
                                     @RequestParam Integer pageNumber,
                                     @RequestParam Integer pageSize,
                                     @RequestParam String sortDirection,
                                     @RequestParam String sortField) {
        return service.getDeductionsRefunds(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    DeductionRefundDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getDeductionsRefunds(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    DeductionRefundDTO getNewDeduction(


    ) {
        return this.service.getDeductionsRefunds(null);
    }


}
