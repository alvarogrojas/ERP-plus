package com.ndl.erp.controller;

import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.dto.InvoiceNotaCreditoDTO;
import com.ndl.erp.dto.InvoiceNotaCreditoListDTO;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.services.InvoiceNotaCreditoService;
import com.ndl.erp.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;




@RestController
@RequestMapping("/api/invoice-nota-credito")
public class InvoiceNotaCreditoController {

    @Autowired
    private InvoiceNotaCreditoService invoiceNotaCreditoService;

    @Autowired
    private InvoiceService invoiceService;



    @PostMapping(path="/save")
    public @ResponseBody
    InvoiceNotaCredito create(@RequestBody InvoiceNotaCredito invoiceNotaCredito) throws Exception {
        return invoiceNotaCreditoService.save(invoiceNotaCredito);
    }

    @GetMapping(path="/send-nc")
    public @ResponseBody
    ResultFe sendCreateNc(@RequestParam(required = true) Integer id) throws  Exception {
        return invoiceNotaCreditoService.sendNotaCredito(id);
    }



    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    InvoiceNotaCreditoListDTO getList(
            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam (value="status",required=false) String status,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,

            @RequestParam String sortDirection,
            @RequestParam String sortField
    ) {
        return invoiceNotaCreditoService.getInvoiceNotaCreditoList(startDate,
                endDate,
                status,
                pageNumber,
                pageSize,
                sortDirection,
                sortField
        );
    }


    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPdf(@RequestParam(required = true) Integer id, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Path p = this.invoiceNotaCreditoService.getFacturaBasePdfPath(id);
        if (p==null) {
//            System.out.println("FACTURA NO ENCONTRADA");
            throw new Exception("Not found bill id");
        }
        Resource resource = this.invoiceNotaCreditoService.loadFileAsResource(id, p);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            System.out.println("Could not determine file type.");
            //throw new Exception("Could not determine file type.);

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }





    @GetMapping(path = "/get")
    public @ResponseBody
    InvoiceNotaCreditoDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception {
        return this.invoiceNotaCreditoService.getInvoiceNotaCredito(id);
    }


    @GetMapping(path="/change-status")
    public @ResponseBody
    InvoiceNotaCredito changeStatus(
            @RequestParam (value="id",required=false) Integer id,
            @RequestParam (value="status",required=false) String status
    ) {
        return invoiceNotaCreditoService.updateStatus(id, status);
    }


    @GetMapping(path = "/new-form")
    public @ResponseBody
    InvoiceNotaCreditoDTO getNew(
    )

            throws Exception {
        return this.invoiceNotaCreditoService.invoiceNotaCredito(null, null);
    }


}

