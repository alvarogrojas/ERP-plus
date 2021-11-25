package com.ndl.erp.controller;


import com.ndl.erp.dto.*;

import com.ndl.erp.services.ConfirmacionRechazosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;

@RestController
@RequestMapping("/api/confirma-rechaza")
public class ConfirmaRechazaController {

    @Autowired
    private ConfirmacionRechazosService service;


    @GetMapping(path = "/data")
    public @ResponseBody
    HaciendaMensajeDTO getFacturaElectronica(@RequestParam String fileName) throws JAXBException {

        return this.service.getHaciendaMensajeDTO(fileName, false);
    }

    @GetMapping(path="/fe/confirm")
    public @ResponseBody
    ResultFe confirmFe(
                   @RequestParam String fileName,
                   @RequestParam Boolean isConfirm,
                   @RequestParam String mensaje) throws JAXBException, DatatypeConfigurationException {
        //return service.save(c);
        return service.confirmFacturaElectronica(fileName, mensaje,  isConfirm);
    }

    @GetMapping(path = "/nc/confirm")
    public @ResponseBody
    ResultFe confirmarNc(@RequestParam String fileName,
                         @RequestParam Boolean isConfirm,
                       @RequestParam String mensaje) throws JAXBException, DatatypeConfigurationException {

        return service.confirmNotaCreditoElectronica(fileName, mensaje, isConfirm);
    }

    @PostMapping("/upload")
    public @ResponseBody
    HaciendaMensajeDTO handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";

        HaciendaMensajeDTO result = null;
        try {
            result = service.loadDataFromFile(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(path = "/data/list")
    public @ResponseBody
    ConfirmaRechazaDTO list(
                                      @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
                                      @RequestParam(value = "fechaFinal", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
                                      @RequestParam(value = "emisor", required = false) String emisor,
                                      @RequestParam(value = "estado", required = false) String estado,
                                      @RequestParam Integer pageNumber,
                                      @RequestParam Integer pageSize,

                                      @RequestParam String sortDirection,
                                      @RequestParam String sortField
    ) {
        return service.getConfirmacionRechazosList(emisor, estado, fechaInicio, fechaFinal,
                pageNumber, pageSize, sortDirection, sortField);
    }


}
