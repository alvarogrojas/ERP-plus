package com.ndl.erp.controller;

import com.ndl.erp.domain.RequisicionBodega;
import com.ndl.erp.dto.RequisicionBodegaDTO;
import com.ndl.erp.dto.RequisicionBodegaListDTO;
import com.ndl.erp.services.RequisicionBodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/requisicion")
public class RequisicionBodegaController {

        @Autowired
        private RequisicionBodegaService requisicionBodegaService;

        @Autowired
        private ApplicationContext applicationContext;

        @PostMapping(path="/save")
        public @ResponseBody
        RequisicionBodega create(@RequestBody RequisicionBodega rb) throws Exception {
            return requisicionBodegaService.save(rb);
        }


        @GetMapping(path = "/data/list-page")
        public @ResponseBody
        RequisicionBodegaListDTO getList(@RequestParam(value = "filter", required = false) String filter,
                                         @RequestParam(value = "consecutivo", required = false) String consecutivo,
                                         @RequestParam(value = "bodegaId", required = false) Integer bodegaId,
                                         @RequestParam(value = "startFecha", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startFecha,
                                         @RequestParam(value = "endFecha", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endFecha,
                                         @RequestParam(value = "estado", required = false) String estado,
                                         @RequestParam Integer pageNumber,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String sortDirection,
                                         @RequestParam String sortField) {
            return requisicionBodegaService.getRequisicionBodegaList(filter,consecutivo, bodegaId, startFecha, endFecha, estado, pageNumber, pageSize, sortDirection, sortField);
        }

        @GetMapping(path = "/data")
        public @ResponseBody
        RequisicionBodegaListDTO getList(@RequestParam(value = "filter", required = false) String filter
                                         ) {
            return requisicionBodegaService.getRequisicion(filter);
        }


        @PostMapping(path="/change-status")
        public @ResponseBody
        RequisicionBodega changeStatus(
                @RequestBody RequisicionBodega rb
        ) throws Exception {
            return this.requisicionBodegaService.changeRequisicionStatus(rb);
        }

        @GetMapping(path = "/get")
        public @ResponseBody
        RequisicionBodegaDTO getRequisicionBodega(
                @RequestParam(value = "id", required = false) Integer id
        ) throws Exception {
            return this.requisicionBodegaService.getRequisicionBodega(id);
        }

        @GetMapping(path = "/new-form")
        public @ResponseBody
        RequisicionBodegaDTO getNewRequisicionBodegaDTO(

        ) throws Exception {
            return this.requisicionBodegaService.getRequisicionBodega();
        }

        @PostMapping(path="/set-rechazo")
        public @ResponseBody
        RequisicionBodega rechazoRequisicionBodega(
                @RequestParam(value = "id", required = true) Integer id
        ) throws Exception {
            return this.requisicionBodegaService.rechazarBodegaRequisicion(id);
        }



    }
