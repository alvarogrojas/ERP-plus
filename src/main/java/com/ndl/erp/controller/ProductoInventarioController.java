package com.ndl.erp.controller;

import com.ndl.erp.dto.InitInventarioDTO;
import com.ndl.erp.dto.ProductoInventarioDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.services.ProductoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/producto-import")
public class ProductoInventarioController {

    @Autowired
    ProductoInventarioService productoInventarioService;

    @PostMapping("/upload")
    public @ResponseBody
    ProductoInventarioDTO handleFileUpload(@RequestParam("file") MultipartFile file

    ) {
        String message = "";

        ProductoInventarioDTO result = null;
        try {
            result = productoInventarioService.loadDataFromFile(file);

        } catch (GeneralInventoryException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException(e.getMessage());
        }
        return result;
    }

    @PostMapping("/import")
    public @ResponseBody
    Boolean importData(@RequestBody ProductoInventarioDTO c)throws Exception{
        return this.productoInventarioService.initializerInventario(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    InitInventarioDTO getList(
    ) {
        return this.productoInventarioService.getInits();
    }
}
