package com.ndl.erp.controller;

import com.ndl.erp.domain.LaborCost;

import com.ndl.erp.dto.LaborCostDTO;
import com.ndl.erp.dto.LaborCostDetailDTO;
import com.ndl.erp.dto.LaborCostsDTO;

import com.ndl.erp.dto.Result;
import com.ndl.erp.services.LaborCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/labor-cost")
public class LaborCostController {

    @Autowired
    private LaborCostService service;


    @PostMapping(path="/save")
    public @ResponseBody
    LaborCost create(@RequestBody LaborCost c) {
        return service.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    LaborCostsDTO getList(
            @RequestParam (value="costCenterId",required=false) Integer costCenterId,
            @RequestParam (value="collaboratorId",required=false) Integer collaboratorId,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                  Date startDate,
                          @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                          @RequestParam Integer pageNumber,
                          @RequestParam Integer pageSize,
                          @RequestParam Boolean isLoadedCombos,
                          @RequestParam String sortDirection,
                          @RequestParam String sortField) {
        return service.getLaborCost(collaboratorId,costCenterId, startDate,endDate, isLoadedCombos, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get/extra-hours")
    public @ResponseBody
    LaborCostDetailDTO getDetail(
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate
    ) {
        return this.service.getLaborCostDetail(startDate, endDate);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    LaborCostDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getLaborCost(id);
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    Result delete(@RequestBody LaborCost c) {
        return service.delete(c);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    LaborCostDTO getNew(


    ) {
        return this.service.getLaborCost(null);
    }


}
