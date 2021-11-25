package com.ndl.erp.controller;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.PurchaseOrderClient;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.BillPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/bill-pay")
public class BillPayController {

    @Autowired
    private BillPayService service;


    @PostMapping(path="/save")
    public @ResponseBody
    BillPay create(@RequestBody BillPay c)  throws Exception{
        return service.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    BillPaysDTO getList(
            @RequestParam (value="providerId",required=false) Integer providerId,
            @RequestParam (value="consecutive",required=false) String consecutive,
            @RequestParam (value="type",required=false) String type,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam (value="expireStartDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date expireStartDate,
            @RequestParam (value="expireEndDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date expireEndDate,
            @RequestParam (value="state",required=false) String state,
            @RequestParam (value="currencyId",required=false) Integer currencyId,
            @RequestParam Boolean isLoadedCombos,
                          @RequestParam Integer pageNumber,
                          @RequestParam Integer pageSize,

                          @RequestParam String sortDirection,
                          @RequestParam String sortField) {
        return service.getBillPay(providerId,
                startDate,endDate, expireStartDate, expireEndDate, consecutive, type,
                currencyId, state, isLoadedCombos, pageNumber, pageSize, sortDirection, sortField);
    }


    @GetMapping(path = "/data/list-bp")
    public @ResponseBody
    BillPaysNcDTO getBillPaysNcList(
            @RequestParam (value="filter",required=true) String filter
            ) {
        return service.getBillPaysNcList(filter
                );
    }



    @GetMapping(path = "/get")
    public @ResponseBody
    BillPayDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception{
        return this.service.getBillPay(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    BillPayDTO getNew(
    )throws  Exception {
        return this.service.getBillPay(null);
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    Result delete(@RequestBody BillPay c) {
        return service.delete(c);
    }

    @PostMapping("/upload")
    public @ResponseBody
    HaciendaMensajeDTO handleFileUpload(@RequestParam("file") MultipartFile file)  throws JAXBException, IOException  {
        String message = "";

        HaciendaMensajeDTO result = null;

            return this.service.loadDataFromFile(file);


    }


    @PostMapping(path="/change-status")
    public @ResponseBody
    BillPay changeStatus(
            @RequestBody BillPay c ) throws Exception{

        return service.chageStatus(c);
    }





}
