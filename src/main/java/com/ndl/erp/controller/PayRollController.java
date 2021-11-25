package com.ndl.erp.controller;

import com.ndl.erp.dto.NominaDetailDTO;
import com.ndl.erp.dto.PayRollDTO;
import com.ndl.erp.dto.PayrollsDTO;

import com.ndl.erp.dto.Result;
import com.ndl.erp.services.PayrollGeneratorService;
import com.ndl.erp.services.PayrollService;
import com.ndl.erp.views.PDFPlanillaViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;



@RestController
@RequestMapping("/api/pay-roll")
public class PayRollController {

    @Autowired
    private PayrollService service;


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PayrollGeneratorService payrollGeneratorService;


    @GetMapping(path="/generate")
    public @ResponseBody
    Result generate(@RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                            Date startDate,
                    @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {

        return payrollGeneratorService.generatePayRoll(new java.sql.Date(startDate.getTime()),
                new java.sql.Date(endDate.getTime()));
//        return payrollGeneratorService.generatePayRoll(startDate, endDate);
    }

    @GetMapping(path="/regenerate")
    public @ResponseBody
    Result regenerate(
            @RequestParam Integer id,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {

        return payrollGeneratorService.regeneratePayRoll(id, new java.sql.Date(startDate.getTime()),
                new java.sql.Date(endDate.getTime()));
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    PayrollsDTO getList(
            @RequestParam (value="collaboratorId",required=false) Integer collaboratorId,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    java.util.Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getPayrolls(collaboratorId, startDate, endDate, pageNumber, pageSize, sortDirection, sortField);
    }



    @GetMapping(path = "/get")
    public @ResponseBody
    PayRollDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.get(id);
    }

    @GetMapping(path = "/get/by-dates")
    public @ResponseBody
    PayRollDTO getByDates(
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    java.util.Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getByDates(startDate, endDate, id);
    }

    @GetMapping(path = "/get/new")
    public @ResponseBody
    PayRollDTO getByDates(

    ) {
        return this.service.getNewPayroll();
    }

    @DeleteMapping(path = "/delete1")
    public @ResponseBody
    Result delete(@RequestParam(value = "id", required = false) Integer id

    ) {
        return this.service.delete(id);
    }

    @GetMapping(path = "/delete")
    public @ResponseBody
    Result delete1(@RequestParam(value = "id", required = false) Integer id

    ) {
        return this.service.delete(id);
    }

    @GetMapping(path = "/approve")
    public @ResponseBody
    Result approve(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.approvePayRoll(id);
    }


    @RequestMapping(value = "/nomina", method = RequestMethod.GET)
    public ModelAndView downloadNomina(
            @RequestParam Integer payRollId,
            @RequestParam Integer collaboratorId
    ) {
        Map<String, Object> model1 = new HashMap<String, Object>();
        Map data = this.service.getDataForNomina(payRollId, collaboratorId);

//        model1.put("blDTO",blDTO);
        PDFPlanillaViewBuilder view = new PDFPlanillaViewBuilder();
        view.setApplicationContext(applicationContext);

        return new ModelAndView(view, data);
    }

    @RequestMapping(value = "/nomina-data", method = RequestMethod.GET)
    public NominaDetailDTO getNominaData(
            @RequestParam Integer payRollId,
            @RequestParam Integer collaboratorId
    ) {


        return this.service.getPayRollGetDetail(payRollId, collaboratorId);
    }





}
