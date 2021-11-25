package com.ndl.erp.controller;


import com.ndl.erp.domain.MonthlyClosure;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.MonthlyClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;



@RestController
@RequestMapping("/api/closure")
public class MonthlyClosureController {

    @Autowired
    private MonthlyClosureService service;

    @PostMapping(path="/save")
    public @ResponseBody
    MonthlyClosure create(@RequestBody MonthlyClosure c) {

        return service.save(c);
    }

    @PostMapping(path="/save/update")
    public @ResponseBody
    MonthlyClosureGenDTO update(@RequestBody MonthlyClosureGenDTO c) throws Exception {

        return service.update(c);
    }


    @PostMapping(path="/delete")
    public @ResponseBody
    Result delete(@RequestBody MonthlyClosure c) {
        return service.delete(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    MonthlyClosuresDTO getList(

                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getClosures( pageNumber, pageSize, sortDirection, sortField);
    }



    @GetMapping(path = "/get")
    public @ResponseBody
    MonthlyClosureDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.get(id);
    }

    @GetMapping(path = "/analisys/get")
    public @ResponseBody
    AnalysisDTO getAnalisy(
            @RequestParam Integer id
    ) {
        return this.service.getClosureAnalysis(id);
    }

    @GetMapping(path = "/monthly-closure-gen")
    public @ResponseBody
    MonthlyClosureGenDTO getMonthlyClosureGenData(@RequestParam(value = "id", required = false) Integer id) {
        return this.service.getMonthlyClosureGenData(id);
    }

    @GetMapping(path = "/approve")
    public @ResponseBody
    Result approve(@RequestParam(value = "id", required = true) Integer id) {
        return this.service.approveMonthlyClosure(id);
    }

    @GetMapping(path = "/pending/bill-pay")
    public @ResponseBody
    MonthlyClosureGenDTO getPendingBillPay(@RequestParam (value="start",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                   Date start,
                                           @RequestParam (value="end",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
                                           @RequestParam(value = "id", required = true) Integer id) {
        return this.service.getMonthlyClosureBillData(start, end, id, "CXP");
    }

    @GetMapping(path = "/pending/bill-collect")
    public @ResponseBody
    MonthlyClosureGenDTO getPendingBillCollect(@RequestParam (value="start",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                   Date start,
                                           @RequestParam (value="end",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
                                           @RequestParam(value = "id", required = true) Integer id) {
        return this.service.getMonthlyClosureBillData(start, end, id, "CXC");
    }



}
