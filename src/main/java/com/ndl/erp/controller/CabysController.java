package com.ndl.erp.controller;

import com.ndl.erp.domain.Cabys;
import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ServiceCabys;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.CabysService;
import com.ndl.erp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/cabys")
public class CabysController {

    @Autowired
    private CabysService cabysService;


    @PostMapping(path="/service/save")
    public @ResponseBody
    Boolean creatCabysService(@RequestBody SubmitCabys c) {
        return cabysService.saveService(c);
    }

    @PostMapping(path="/inventory/save")
    public @ResponseBody
    Boolean createInventoryItem(@RequestBody SubmitCabys c) {
        return cabysService.saveInventoryItem(c);
    }

    @GetMapping(path = "/service/list")
    public @ResponseBody
    ServiceCabysDTO getCabys(
            @RequestParam(value = "filter", required = false) String filter

    ) {
        return cabysService.getServiceCabys(filter);
    }


    @GetMapping(path = "/inventario/list")
    public @ResponseBody
     InventarioItemsDTO getItems(
            @RequestParam(value = "filter", required = false) String filter

    ) {
        return cabysService.getItems(filter);
    }


    @GetMapping(path = "/service/get")
    public @ResponseBody
    ServiceCabys getCabys(
            @RequestParam(value = "id", required = false) Integer id

    ) {
        return cabysService.getServiceCabys(id);
    }





    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CabysesDTO getList(
            @RequestParam (value="filter",required=false) String filter,
            @RequestParam (value="categoria1",required=false) String categoria1,
            @RequestParam (value="descripcion1",required=false) String descripcion1,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String sortDirection,
            @RequestParam String sortField
    ) {
        return cabysService.getCabyses(
                filter, categoria1, descripcion1,
                pageNumber,pageSize,sortDirection, sortField
        );
    }

    @GetMapping(path = "/data")
    public @ResponseBody
    CabysFilteredDTO getList(
            @RequestParam (value="filter",required=false) String filter,
            @RequestParam (value="categoria1",required=false) String categoria1

    ) {
        return cabysService.getCabys(
                filter, categoria1
        );
    }

    @GetMapping(path = "/new-caby")
    public @ResponseBody
    CabysDTO getNewCabys(


    ) {
        return this.cabysService.getCabyDTO();
    }


}
