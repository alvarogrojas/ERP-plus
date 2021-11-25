package com.ndl.erp.controller;

import com.ndl.erp.domain.FeeVehiculeFuel;
import com.ndl.erp.domain.FeeVehiculeFuel;
import com.ndl.erp.dto.FeeVehiculeFuelDTO;
import com.ndl.erp.dto.FeeVehiculeFuelsDTO;
import com.ndl.erp.services.FeeVehiculeFuelService;
import com.ndl.erp.services.FeeVehiculeFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fee-vehicule-fuel")
public class FeeVehiculeFuelController {

    @Autowired
    private FeeVehiculeFuelService service;


    @GetMapping(path = "/get")
    public @ResponseBody
    FeeVehiculeFuel get(
            @RequestParam(value = "vehiculeId", required = false) Integer vehiculeId,
            @RequestParam(value = "fuelId", required = false) Integer fuelId,
            @RequestParam(value = "old", required = false) Integer old
    ) {
        return this.service.getFeeVehiculeFuel(vehiculeId, fuelId, old);
    }

    @PostMapping(path="/save")
    public @ResponseBody
    FeeVehiculeFuel create(@RequestBody FeeVehiculeFuel c) {
        return service.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    FeeVehiculeFuelsDTO getFeeVehiculeFueleList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getFeeVehiculeFuels(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get/id")
    public @ResponseBody
    FeeVehiculeFuelDTO getFeeVehiculeFuel(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getFeeVehiculeFuel(id);
    }





}
