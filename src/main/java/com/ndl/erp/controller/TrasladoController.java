package com.ndl.erp.controller;

import com.ndl.erp.domain.DevolucionDetalle;
import com.ndl.erp.domain.Traslado;
import com.ndl.erp.dto.TrasladoDTO;
import com.ndl.erp.dto.TrasladosDTO;
import com.ndl.erp.services.TrasladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traslado")
public class TrasladoController {


        @Autowired
        private TrasladoService trasladoService;

        @Autowired
        private ApplicationContext applicationContext;

        @PostMapping(path="/save")
        public @ResponseBody
        Traslado create(@RequestBody Traslado tr) throws Exception {
            return trasladoService.save(tr);
        }


        @GetMapping(path = "/data/list-page")
        public @ResponseBody
        TrasladosDTO getList(@RequestParam(value = "filter", required = false) String filter,
                             @RequestParam(value = "estado", required = false) String estado,
                             @RequestParam Integer pageNumber,
                             @RequestParam Integer pageSize,
                             @RequestParam String sortDirection,
                             @RequestParam String sortField) {
            return trasladoService.getTraslados(filter, estado, pageNumber, pageSize, sortDirection, sortField);
        }


        @GetMapping(path = "/get")
        public @ResponseBody
        TrasladoDTO getTraslado(
                @RequestParam(value = "id", required = false) Integer id
        ) throws Exception {
            return this.trasladoService.getTraslado(id);
        }


        @GetMapping(path = "/new-form")
        public @ResponseBody
        TrasladoDTO getNewTrasladoDTO(

        ) throws Exception {
            return this.trasladoService.getTraslado();
        }



        @GetMapping(path = "/aplicar")
        public @ResponseBody
        Traslado apply(@RequestParam(value = "trasladoId", required = true) Integer trasladoId

        ) throws Exception {
                return this.trasladoService.procesarTrasladoBodega(trasladoId);
        }

        @GetMapping(path = "/cancelar")
        public @ResponseBody
        Traslado cancelar(@RequestParam(value = "trasladoId", required = true) Integer trasladoId

        ) throws Exception {
                return this.trasladoService.cancelarTraslado(trasladoId);
        }


    }