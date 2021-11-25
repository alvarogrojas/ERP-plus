package com.ndl.erp.controller;

import com.ndl.erp.domain.Kilometer;
import com.ndl.erp.dto.KilometerDTO;
import com.ndl.erp.dto.KilometersDTO;
import com.ndl.erp.dto.Result;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.services.KilometerService;

import com.ndl.erp.views.KmViewBuilder;
import com.ndl.erp.views.POPViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/kilometer")
public class KilometerController {

    @Autowired
    private KilometerService service;

    @Autowired
    private ApplicationContext applicationContext;


    @PostMapping(path="/save")
    public @ResponseBody
    ResultBase create(@RequestBody Kilometer c) {
        return service.save(c);
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    Result delete(@RequestBody Kilometer c) {
        return service.delete(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    KilometersDTO getList(
            @RequestParam (value="collaboratorId",required=false) Integer collaboratorId,
            @RequestParam (value="consecutive",required=false) String consecutive,
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam (value="state",required=false) String state,
            @RequestParam (value="costCenterId",required=false) Integer costCenterId,
            @RequestParam (value="currencyId",required=false) Integer currencyId,
            @RequestParam Boolean isLoadedCombos,
                          @RequestParam Integer pageNumber,
                          @RequestParam Integer pageSize,

                          @RequestParam String sortDirection,
                          @RequestParam String sortField) {
        return service.getKilometer(collaboratorId,costCenterId, startDate,endDate, consecutive, currencyId, state,
                isLoadedCombos, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    KilometerDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getKilometer(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    KilometerDTO getNew(


    ) {
        return this.service.getKilometer(null);
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView download(
            @RequestParam Integer id

    ) {
        Map<String, Object> model1 = new HashMap<String, Object>();
        Map data = this.service.getDataForPdf(id);

//        model1.put("blDTO",blDTO);
        KmViewBuilder view = new KmViewBuilder();
        view.setApplicationContext(applicationContext);

        return new ModelAndView(view, data);
    }


}
