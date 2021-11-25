package com.ndl.erp.controller;

import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.Terminal;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.pos.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pos")
public class PosController {

    @Autowired
    PosService posService;


    @GetMapping(path="/get-inventario")
    public @ResponseBody
    InventarioDTO getInventario(@RequestParam(value = "codigo", required = true) String codigo,
                                @RequestParam(value = "bodegaId", required = true) Integer bodegaId) throws Exception {
        return posService.getInventarioByCodigo(codigo, bodegaId);
    }

    @GetMapping(path="/get-inventario-flat")
    public @ResponseBody
    InventarioDTO getInventarioFlat(@RequestParam(value = "codigo", required = true) String codigo,
                                @RequestParam(value = "bodegaId", required = true) Integer bodegaId) throws Exception {
        return posService.getInventarioFlatByCodigo(codigo, bodegaId);
    }

    @GetMapping(path="/terminales")
    public @ResponseBody
    TerminalesDTO getTerminales(@RequestParam(value = "estado", required = true) String estado,
                                @RequestParam(value = "bodegaId", required = true) Integer bodegaId) throws Exception {
        return posService.getTerminales(estado, bodegaId);
    }


    @GetMapping(path="/terminal/new-form")
    public @ResponseBody
    TerminalDTO newTerminalForm() throws Exception {

        return posService.getNewTerminalForm();
    }

    @GetMapping(path="/terminal/get")
    public @ResponseBody
    TerminalDTO get(@RequestParam(value = "id", required = true) Integer id) throws Exception {

        return posService.getTerminal(id);
    }
    @PostMapping(path="/terminal/save")
    public @ResponseBody
    Terminal create(@RequestBody TerminalDTO c) throws Exception{
        return posService.saveTerminal(c);
    }

//    @PostMapping(path="/terminal/save")
//    public @ResponseBody
//    Terminal create(@RequestBody Terminal c) throws Exception{
//        return posService.saveTerminal(c);
//    }


    @GetMapping(path="/get-by-id")
    public @ResponseBody
    InventarioDTO getInventario(@RequestParam(value = "id", required = true) Integer id
                                ) throws Exception {
        return posService.getInventarioById(id);
    }

    @GetMapping(path="/transactions")
    public @ResponseBody
    TransactionsDTO getTransactions(@RequestParam(value = "id", required = false) Integer id
                                ) throws Exception {
        return posService.getTransactionsById(id);
    }

    @GetMapping(path="/new-form")
    public @ResponseBody
    PosDTO newForm(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        //DTO nuevo: TaxesList y que tenga el centroCostoDefault
        return posService.getNewPos(id);
    }


    @PostMapping(path="/save")
    public @ResponseBody
    Invoice create(@RequestBody Invoice c) throws Exception{
        return posService.save(c);
    }

}
