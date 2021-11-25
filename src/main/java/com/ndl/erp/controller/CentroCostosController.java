package com.ndl.erp.controller;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.dto.CentroCostoDTO;
import com.ndl.erp.dto.CentroCostosDTO;
import com.ndl.erp.dto.CostCenterListNoPODTO;
import com.ndl.erp.dto.CostCenterNoPODTO;
import com.ndl.erp.services.CentroCostosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/centro-costos")
public class CentroCostosController {

    @Autowired
    private CentroCostosService centroCostosService;


    @PostMapping(path="/save")
    public @ResponseBody
    CostCenter create(@RequestBody CostCenter c) {
        return centroCostosService.save(c);
    }



    @GetMapping(path = "/list")
    public @ResponseBody
    CentroCostosDTO getCentroCostosList(
                              @RequestParam(value = "filter", required = false) String filter
    ) {
        return this.centroCostosService.getCentroCostos(filter);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CentroCostosDTO getCCList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField
    ) {
        return centroCostosService.getCentroCostos(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get-filter")
    public @ResponseBody
    CentroCostosDTO getCCList(@RequestParam(value = "filter", required = false) String filter

    ) {
        return centroCostosService.getCentroCostos(filter);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    CentroCostoDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
//                              @RequestParam Integer pageNumber,
//                              @RequestParam Integer pageSize
    ) {
        return this.centroCostosService.getCostCenter(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    CentroCostoDTO getNewCentroCostos(
                              @RequestParam(value = "filter", required = false) String filter

    ) {
        return this.centroCostosService.getCostCenter();
    }

    @GetMapping(path = "/projects-no-oc")
    public @ResponseBody
    CostCenterListNoPODTO getProjectsNoOC(
    ) {
        return this.centroCostosService.getProjectsNoOC();
    }

}
