package com.ndl.erp.controller;

import com.ndl.erp.domain.Familia;
import com.ndl.erp.dto.FabricantesListDTO;
import com.ndl.erp.dto.FamiliaDTO;
import com.ndl.erp.dto.FamiliasDTO;
import com.ndl.erp.services.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/familia")
public class FamiliaController {

    @Autowired
    private FamiliaService familiaService;


    @PostMapping(path="/save")
    public @ResponseBody
    Familia create(@RequestBody Familia f) throws Exception{
        return this.familiaService.save(f);
    }

    @GetMapping(path = "/data/list")
    public @ResponseBody
    FamiliasDTO getListByFilter(
            @RequestParam (value="filter",required=true) String filter
    ) {
        return this.familiaService.getListByFilter(filter);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    FamiliasDTO getList(
            @RequestParam (value="filter",required=false) String filter,
            @RequestParam (value="estado",required=false) String estado,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String sortDirection,
            @RequestParam String sortField
    ) {
        return this.familiaService.getFamilias(filter,
                estado,
                pageNumber,
                pageSize,
                sortDirection,
                sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    FamiliaDTO getFamilia(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.familiaService.getFamiliaData(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    FamiliaDTO getNewFamilia() {
        return this.familiaService.getFamilia();
    }




}
