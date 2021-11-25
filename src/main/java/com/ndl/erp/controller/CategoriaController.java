package com.ndl.erp.controller;

import com.ndl.erp.domain.Categoria;
import com.ndl.erp.domain.Refundable;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

   @Autowired
   CategoriaService categoriaService;



    @PostMapping(path="/save")
    public @ResponseBody
    Categoria create(@RequestBody Categoria c) throws Exception {
        return this.categoriaService.save(c);
    }

    @PostMapping(path="/save-productos-categorias")
    public @ResponseBody
    ProductosCategoriaDTO create(@RequestBody ProductosCategoriaDTO c) throws Exception {
        return this.categoriaService.saveProductosCategorias(c);
    }

    @PostMapping(path="/producto-categoria/delete")
    public @ResponseBody
    Result delete(
            @RequestParam(value = "productoId", required = true) Integer productoId,
            @RequestParam(value = "categoriaId", required = true) Integer categoriaId
    ) {
        return categoriaService.deleteProductoCategoria(productoId,categoriaId);
    }

    @GetMapping(path = "/productos/get")
    public @ResponseBody
    ProductoCategoriaListDTO getProductoCategoriaList(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.categoriaService.getProductosCategoria(id);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    CategoriaDTO getCategoria(
            @RequestParam(value = "id", required = true) Integer id
    ) {
        return this.categoriaService.getCategoriaData(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    CategoriaDTO getNewCategoria() {
        return this.categoriaService.getCategoria();
    }



    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CategoriasDTO getList(@RequestParam (value="filter",required=true) String filter,
                          @RequestParam(value="estado",required=false) String estado,
                          @RequestParam Integer pageNumber,
                          @RequestParam Integer pageSize,
                          @RequestParam String sortDirection,
                          @RequestParam String sortField
    ) {
        return this.categoriaService.getCategorias(filter,
                estado,
                pageNumber,
                pageSize,
                sortDirection,
                sortField);
    }

}
