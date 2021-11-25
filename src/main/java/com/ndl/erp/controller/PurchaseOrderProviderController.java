package com.ndl.erp.controller;

import com.ndl.erp.domain.PurchaseOrderProvider;
import com.ndl.erp.dto.PurchaseOrderProviderDTO;
import com.ndl.erp.dto.PurchaseOrderProvidersDTO;
import com.ndl.erp.services.PurchaseOrderProviderService;
import com.ndl.erp.views.POPViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/po-provider")
public class PurchaseOrderProviderController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PurchaseOrderProviderService service;


    @PostMapping(path="/save")
    public @ResponseBody
    PurchaseOrderProvider create(@RequestBody PurchaseOrderProvider c) {

        return service.save(c);
    }



    @GetMapping(path = "/list")
    public @ResponseBody
    PurchaseOrderProvidersDTO getCentroCostosList(
                              @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.service.getPurchaseOrderProviders(filter);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    PurchaseOrderProvidersDTO getPurchaseOrderProvidereList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return service.getPurchaseOrderProviders(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    PurchaseOrderProviderDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception{
        return this.service.getPurchaseOrderProvider(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    PurchaseOrderProviderDTO getNewCentroCostos(


    ) throws Exception {
        return this.service.getPurchaseOrderProvider();
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView download(
            @RequestParam Integer id

    ) throws Exception {
        Map<String, Object> model1 = new HashMap<String, Object>();
        Map data = this.service.getDataForPdf(id);

//        model1.put("blDTO",blDTO);
        POPViewBuilder view = new POPViewBuilder();
        view.setApplicationContext(applicationContext);

        return new ModelAndView(view, data);
    }


}
