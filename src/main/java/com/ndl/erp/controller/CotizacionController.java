package com.ndl.erp.controller;

import com.ndl.erp.domain.Cotizacion;
import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.dto.CotizacionDTO;
import com.ndl.erp.dto.CotizacionListDTO;
import com.ndl.erp.services.CotizacionService;
import com.ndl.erp.views.CotizacionViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cotizacion")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping(path="/save")
    public @ResponseBody
    Cotizacion create(@RequestBody Cotizacion c) throws Exception {
        return cotizacionService.save(c);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    CotizacionListDTO getList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "startFecha", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startFecha,
                              @RequestParam(value = "endFecha", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endFecha,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return cotizacionService.getCotizacionList(filter,startFecha,endFecha, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    CotizacionDTO getCotizacion(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception {
            return this.cotizacionService.getCotizacion(id);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    CotizacionDTO getNewCotizacionDTO(


    ) throws Exception {
        return this.cotizacionService.getCotizacion();
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView download(
            @RequestParam Integer id

    ) throws Exception {
        Map<String, Object> model1 = new HashMap<String, Object>();
        Map data = this.cotizacionService.getDataForPdf(id);

        CotizacionViewBuilder view = new CotizacionViewBuilder();
        view.setApplicationContext(applicationContext);

        return new ModelAndView(view, data);
    }

    @GetMapping(path="/change-status")
    public @ResponseBody
    Cotizacion changeStatus(
            @RequestParam (value="id",required=false) Integer id,
            @RequestParam (value="status",required=false) String status
    ) throws Exception {
        return cotizacionService.updateStatus(id, status);
    }


}