package com.ndl.erp.controller;

import com.ndl.erp.dto.EntradasSalidasInventarioDTO;
import com.ndl.erp.services.EntradaSalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/entrada-salida/")
public class EntradaSalidaController {

    @Autowired
    private EntradaSalidaService entradaSalidaService;


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    EntradasSalidasInventarioDTO getMovimientosEntradaSalidaList(
            @RequestParam (value="costCenterId",required=false) Integer costCenterId,
            @RequestParam (value="start",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date start,
            @RequestParam (value="end",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date end

    ) {
        return this.entradaSalidaService.getMovimientosInventario(costCenterId,
                                                                  start,
                                                                  end);

    }




}
