package com.ndl.erp.controller;

import com.ndl.erp.domain.Bank;
import com.ndl.erp.domain.FixedCost;
import com.ndl.erp.domain.Various;
import com.ndl.erp.dto.*;

import com.ndl.erp.services.CashFlowService;

import com.ndl.erp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash-flow")
public class CashFlowController {

    @Autowired
    private CashFlowService chashFlowService;

    @Autowired
    private ScheduleService scheduleService;


    @PostMapping(path="/bank/save")
    public @ResponseBody
    Bank createBank(@RequestBody Bank c) {
        return chashFlowService.save(c);
    }



    @GetMapping(path = "/bank/list")
    public @ResponseBody
    CashFlowDTO getBankList(
            @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.chashFlowService.getBanks(filter);
    }
    

    @GetMapping(path = "/bank/get")
    public @ResponseBody
    BankDTO getBank(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.chashFlowService.getBank(id);
    }

    @GetMapping(path = "/schedule")
    public @ResponseBody
    ScheduleDTO getSchedule(

    ) {
        return this.scheduleService.getSchedule();
    }


    @GetMapping(path = "/bank/new-form")
        public @ResponseBody
        BankDTO getNewBank(


    ) {
        return this.chashFlowService.getBank();
    }
    
    @PostMapping(path="/various/save")
    public @ResponseBody
    Various createVarious(@RequestBody Various c) {
        return chashFlowService.save(c);
    }



    @GetMapping(path = "/various/list")
    public @ResponseBody
    CashFlowDTO getVariousList(
            @RequestParam(value = "filter", required = false) String filter


    ) {
        return this.chashFlowService.getVariousList(filter);
    }
    

    @GetMapping(path = "/various/get")
    public @ResponseBody
    VariousDTO getVarious(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.chashFlowService.getVarious(id);
    }


    @GetMapping(path = "/various/new-form")
        public @ResponseBody
        VariousDTO getNewVarious(


    ) {
        return this.chashFlowService.getVarious();
    }

    @PostMapping(path="/fixed-cost/save")
    public @ResponseBody
    FixedCost createFixedCost(@RequestBody FixedCost c) {
        return chashFlowService.save(c);
    }



    @GetMapping(path = "/fixed-cost/list")
    public @ResponseBody
    CashFlowDTO getFixedCostList(
            @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.chashFlowService.getFixedCosts(filter);
    }


    @GetMapping(path = "/fixed-cost/get")
    public @ResponseBody
    FixedCostDTO getFixedCost(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.chashFlowService.getFixedCost(id);
    }


    @GetMapping(path = "/fixed-cost/new-form")
    public @ResponseBody
    FixedCostDTO getNewFixedCost(


    ) {
        return this.chashFlowService.getFixedCost();
    }



}
