package com.ndl.erp.controller;

import com.ndl.erp.dto.ExchangeRateDTO;
import com.ndl.erp.dto.ExchangeRatesDTO;
import com.ndl.erp.domain.ExchangeRate;
import com.ndl.erp.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;


    @PostMapping(path="/save")
    public @ResponseBody
    ExchangeRate create(@RequestBody ExchangeRate er) {
        return exchangeRateService.save(er);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    ExchangeRatesDTO getExchangerates(@RequestParam (value="start",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
                                      @RequestParam (value="end",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam Integer  pageNumber,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortDirection,
                                      @RequestParam String sortField) {
        return this.exchangeRateService.getExchangeRates(start, end, status, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ExchangeRateDTO getExchangeRate(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.exchangeRateService.getExhangeRate(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    ExchangeRateDTO getNewExchangeRate(


    ) {
        return this.exchangeRateService.getExchangeRate();
    }


}