package com.ndl.erp.controller;

import com.ndl.erp.dto.BalanceDTO;
import com.ndl.erp.dto.InvoiceFlatDTO;
import com.ndl.erp.services.BalanceService;
import com.ndl.erp.services.FamiliaService;
import com.ndl.erp.services.InvoiceService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//import java.sql.Date;

@RestController
@RequestMapping("/factura-qr")
public class FacturaQrController {

    @Autowired
    private InvoiceService invoiceService;



    @GetMapping(path = "/get")
    public @ResponseBody
    InvoiceFlatDTO get(
            @RequestParam(required = true) Integer id
            ) throws Exception {
        return invoiceService.getInvoiceById(id);
    }
}
