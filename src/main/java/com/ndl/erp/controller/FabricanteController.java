package com.ndl.erp.controller;

import com.ndl.erp.domain.Fabricante;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fabricante")
public class FabricanteController {

    @Autowired
    private FabricanteService fabricanteService;


    @PostMapping(path="/save")
    public @ResponseBody
    Fabricante create(@RequestBody Fabricante f) throws Exception{
        return this.fabricanteService.save(f);
    }

    @GetMapping(path = "/data/list")
    public @ResponseBody
    FabricantesListDTO getListByFilter(
            @RequestParam (value="filter",required=true) String filter
    ) {
        return this.fabricanteService.getListByFilter(filter);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    FabricantesDTO getList(
//                                 @RequestParam (value="filter",required=false) String filter,
                                 @RequestParam (value="estado",required=false) String estado,
                                 @RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String sortDirection,
                                 @RequestParam String sortField
    ) {
        return this.fabricanteService.getFabricantes("",
                                                    estado,
                                                    pageNumber,
                                                    pageSize,
                                                    sortDirection,
                                                    sortField);
                                        }




    @GetMapping(path = "/get")
    public @ResponseBody
    FabricanteDTO getFabricante(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.fabricanteService.getFabricanteData(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    FabricanteDTO getNewFabricante() {
        return this.fabricanteService.getFabricante();
    }




}