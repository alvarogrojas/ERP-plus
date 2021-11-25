package com.ndl.erp.controller;

import com.ndl.erp.domain.ProductoDescuento;
import com.ndl.erp.dto.ProductoDescuentoDTO;
import com.ndl.erp.dto.ProductoDescuentoListDTO;
import com.ndl.erp.services.ProductoDescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto-descuento")
public class ProductoDescuentoController {

    @Autowired
    private ProductoDescuentoService productoDescuentoService;


    @PostMapping(path="/save")
    public @ResponseBody
    ProductoDescuento create(@RequestBody ProductoDescuento pd) {
        return this.productoDescuentoService.save(pd);
    }


    @GetMapping(path = "/data/descuento-producto-list")
    public @ResponseBody
    ProductoDescuentoListDTO getProductoDescuentoList( @RequestParam(value = "productoId", required = true) Integer productoId) {
        return this.productoDescuentoService.getProductoDescuentoList(productoId);
    }

    @GetMapping(path = "/data/list")
    public @ResponseBody
    ProductoDescuentoListDTO getProductoDescuentoList() {
        return this.productoDescuentoService.getProductoDescuentoList();
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ProductoDescuentoDTO getProductoDescuento(
            @RequestParam(value = "id", required = false) Integer id) {
        return this.productoDescuentoService.getProductoDescuento(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    ProductoDescuentoDTO getNewProductoDescuentoDTO() {
        return this.productoDescuentoService.getProductoDescuento();
    }


}