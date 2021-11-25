package com.ndl.erp.controller;

import com.ndl.erp.dto.Result;
import com.ndl.erp.services.PayrollGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@RestController
@RequestMapping("/api/pay-roll-gen")
public class PayRollGeneratorController {

    @Autowired
    private PayrollGeneratorService service;


    @GetMapping(path="/generate")
    public @ResponseBody
    Result generate(@RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                          Date startDate,
                  @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {

        return service.generatePayRoll(startDate, endDate);
    }

    @GetMapping(path="/regenerate")
    public @ResponseBody
    Result regenerate(
            @RequestParam Integer id,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                          Date startDate,
                  @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {

        return service.regeneratePayRoll(id, startDate, endDate);
    }





}
