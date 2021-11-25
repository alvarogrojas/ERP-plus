package com.ndl.erp.controller;

import com.ndl.erp.domain.Producto;
import com.ndl.erp.dto.CatalogoProductosDTO;
import com.ndl.erp.dto.DescuentosDTO;
import com.ndl.erp.dto.ProductoDTO;
import com.ndl.erp.dto.ProductosDTO;
import com.ndl.erp.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @PostMapping(path="/save")
    public @ResponseBody
    Producto create(@RequestBody Producto c) throws Exception{
        return this.productoService.save(c);
    }


    @GetMapping(path = "/list")
    public @ResponseBody
    ProductosDTO getProductoList(
            @RequestParam(value = "bodegaId", required = false) Integer bodegaId,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "esEntrada", required = false) Boolean esEntrada


    ) {
        return this.productoService.getProductosListDTO(bodegaId,filter, esEntrada);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CatalogoProductosDTO getList(@RequestParam (value="filter",required=true) String filter,
                                     @RequestParam(value="estado",required=false) String estado,
                                     @RequestParam Integer pageNumber,
                                     @RequestParam Integer pageSize,
                                     @RequestParam String sortDirection,
                                     @RequestParam String sortField
    ) {
        return this.productoService.getCatalogoProductos(filter,
                estado,

                pageNumber,
                pageSize,
                sortDirection,
                sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ProductoDTO getProducto(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.productoService.getProductoData(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    ProductoDTO getNewProducto() {
        return this.productoService.getProducto();
    }


    @GetMapping(path = "/descuentos")
    public @ResponseBody
    DescuentosDTO getDescuentos(@RequestParam (value="productoId",required=true) Integer productoId,
                                @RequestParam(value="clienteId",required=false) Integer clienteId

                              ) {
        return this.productoService.getDescuentosDTO(productoId);
    }


}