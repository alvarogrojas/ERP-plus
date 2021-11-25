package com.ndl.erp.controller;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.Inventario;
import com.ndl.erp.dto.InventariosDTO;
import com.ndl.erp.services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    InventarioService inventarioService;

    @PostMapping(path="/save")
    public @ResponseBody
    Inventario create(@RequestBody Inventario c)  throws Exception{
        return inventarioService.saveInventario(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    InventariosDTO getList( @RequestParam (value="filter",required=false) String filter,
                            @RequestParam(value="bodegaId",required=false) Integer bodegaId,
                            @RequestParam(value="existentes",required=false) Integer existentes,
                            @RequestParam(value="disponible",required=false) Integer disponible,
                            @RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize,
                            @RequestParam String sortDirection,
                            @RequestParam String sortField
    ) {
        return this.inventarioService.getInventariosDTO(filter,
                                                        bodegaId ,
                                                        existentes,
                                                        disponible,
                                                        pageNumber,
                                                        pageSize,
                                                        sortDirection,
                                                        sortField);
    }

}
