package com.ndl.erp.controller;

import com.ndl.erp.domain.ClienteDescuento;
import com.ndl.erp.dto.ClienteDescuentoDTO;
import com.ndl.erp.dto.ClienteDescuentoListDTO;
import com.ndl.erp.services.ClienteDescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/descuento")
public class ClienteDescuentoController {

    @Autowired
    private ClienteDescuentoService clienteDescuentoService;


    @PostMapping(path="/save")
    public @ResponseBody
    ClienteDescuento create(@RequestBody ClienteDescuento c) {
        return this.clienteDescuentoService.save(c);
    }


    @GetMapping(path = "/data/list")
    public @ResponseBody
    ClienteDescuentoListDTO getClienteList(@RequestParam(value = "clienteId", required = true) Integer clienteId,
                                           @RequestParam(value = "productoId", required = true) Integer productoId) {
        return this.clienteDescuentoService.getClientDescuentoList(clienteId, productoId);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ClienteDescuentoDTO getClienteDescuento(
            @RequestParam(value = "id", required = false) Integer id) {
        return this.clienteDescuentoService.getClienteDescuento(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    ClienteDescuentoDTO getNewClienteDescuentoDTO() {
        return this.clienteDescuentoService.getClienteDescuento();
    }


}