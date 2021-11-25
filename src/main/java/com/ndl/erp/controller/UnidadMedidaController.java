package com.ndl.erp.controller;

import com.ndl.erp.domain.UnidadMedida;
import com.ndl.erp.dto.UnidadMedidaDTO;
import com.ndl.erp.dto.UnidadesMedidaDTO;
import com.ndl.erp.services.UnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unidades")
public class UnidadMedidaController {

    @Autowired
    private UnidadMedidaService unidadMedidadService;


    @PostMapping(path="/save")
    public @ResponseBody
    UnidadMedida create(@RequestBody UnidadMedida u) throws Exception{
        return this.unidadMedidadService.save(u);
    }





    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    UnidadesMedidaDTO getList(
                              @RequestParam (value="estado",required=true) String estado,
                                 @RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String sortDirection,
                                 @RequestParam String sortField
    ) {
        return this.unidadMedidadService.getUnidadesMedida(estado,

                pageNumber,
                pageSize,
                sortDirection,
                sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    UnidadMedidaDTO getUnidadMedida(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.unidadMedidadService.getUnidadMedida(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    UnidadMedidaDTO getNewUnidadMedida() {
        return this.unidadMedidadService.getUnidadMedida();
    }




}
