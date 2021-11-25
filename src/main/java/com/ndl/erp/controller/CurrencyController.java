package com.ndl.erp.controller;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.dto.CurrenciesDTO;
import com.ndl.erp.dto.CurrencyDTO;
//import com.ndl.erp.dto.CurrencysDTO;
import com.ndl.erp.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @PostMapping(path="/save")
    public @ResponseBody
    Currency create(@RequestBody Currency c) {
        return currencyService.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CurrenciesDTO getCurrencyeList(@RequestParam(value = "filter", required = false) String filter,
                                   @RequestParam Integer pageNumber,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String sortDirection,
                                   @RequestParam String sortField) {
        return currencyService.getCurrencys(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    CurrencyDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.currencyService.getCurrency(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    CurrencyDTO getNewCentroCostos(


    ) {
        return this.currencyService.getCurrency();
    }


}
