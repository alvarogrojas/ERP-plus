package com.ndl.erp.controller;

import com.ndl.erp.domain.SessionPos;
import com.ndl.erp.dto.SesionesDTO;
import com.ndl.erp.dto.SessionPosDTO;
import com.ndl.erp.services.pos.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/session-pos")
public class SessionPosController {

    @Autowired
    PosService posService;

    @PostMapping(path="/open")
    public @ResponseBody
    SessionPos create(@RequestBody SessionPos s) throws Exception{
        return posService.open(s);
    }

    @PostMapping(path="/close")
    public @ResponseBody
    SessionPos close(@RequestBody SessionPos s) throws Exception{
        return posService.closeSession(s);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    SessionPosDTO get(
            @RequestParam(value = "id", required = false) Integer id
    )throws Exception{
        return this.posService.getSessionPos(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    SessionPosDTO getNew(
    ) {
        return this.posService.getSessionPos();
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    SesionesDTO getList(@RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                        @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                        @RequestParam (value="estado",required=false) String estado,
                        @RequestParam (value="userId",required=false) Integer cajeroId,
                        @RequestParam (value="bodegaId",required=false) Integer bodegaId,
                        @RequestParam Integer pageNumber,
                        @RequestParam Integer pageSize,
                        @RequestParam String sortDirection,
                        @RequestParam String sortField) {

        return this.posService.getSesiones(startDate,
                                           endDate,
                                           estado,
                                           cajeroId,
                                           bodegaId,
                                           pageNumber,
                                           pageSize,
                                           sortDirection,
                                           sortField);
    }


}
