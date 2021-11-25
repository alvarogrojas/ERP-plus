package com.ndl.erp.controller;

import com.ndl.erp.domain.Invoice;
import com.ndl.erp.dto.*;
import com.ndl.erp.services.InvoiceService;
import com.ndl.erp.views.ExcelVentasView;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService service;


    @PostMapping(path="/save")
    public @ResponseBody
    Invoice create(@RequestBody Invoice c) throws Exception{
        return service.save(c);
    }

    @GetMapping(path="/change-status")
    public @ResponseBody
    Invoice changeStatus(
                    @RequestParam (value="id",required=false) Integer id,
                    @RequestParam (value="status",required=false) String status
            ) throws Exception {
        return service.updateStatus(id, status);
    }

    @GetMapping(path="/send-hacienda-exportacion")
    public @ResponseBody
    ResultFe sendExportacion (@RequestParam(required = true) Integer id) throws  Exception {
        return service.sendExportacionToHacienda(id, false);
    }

    @GetMapping(path="/resend-hacienda-exportacion")
    public @ResponseBody
    ResultFe resendExportacion (@RequestParam(required = true) Integer id) throws  Exception {
        return service.sendExportacionToHacienda(id, true);
    }

    @GetMapping(path="/send-hacienda")
    public @ResponseBody
    ResultFe send (@RequestParam(required = true) Integer id) throws  Exception {
        return service.sendToHacienda(id, false);
    }

    @GetMapping(path="/resend-hacienda")
    public @ResponseBody
    ResultFe resend (@RequestParam(required = true) Integer id) throws  Exception {
        return service.sendToHacienda(id, true);
    }

    @GetMapping(path="/create-send-nc")
    public @ResponseBody
    ResultFe sendCreateNc(@RequestParam(required = true) Integer id) throws  Exception {
        return service.sendToHaciendaNotaCredito(id);
    }

    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    InvoicesDTO getList(
                @RequestParam (value="client",required=false) String client,
        @RequestParam (value="currencyId",required=false) Integer currencyId,
    @RequestParam (value="purchaseOrderNumber",required=false) String purchaseOrderNumber,
    @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date startDate,
    @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
    @RequestParam (value="number",required=false) Integer number,
    @RequestParam (value="total",required=false) Double total,
    @RequestParam (value="state",required=false) String state,
    @RequestParam (value="estadoHacienda",required=false) String estadoHacienda,
    @RequestParam Boolean isLoadedCombos,
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize,

    @RequestParam String sortDirection,
    @RequestParam String sortField
    ) {
        return service.getInvoice(
                client, currencyId, purchaseOrderNumber,
                startDate, endDate, number, total, state,estadoHacienda, isLoadedCombos, pageNumber,pageSize,sortDirection, sortField
        );
    }

    @GetMapping(path = "/data-list")
    public @ResponseBody
    InvoicesListDTO getNotEditableList(
                @RequestParam (value="filter",required=true) String filter

    ) {

        return service.getInvoices(
            filter
        );
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @RequestMapping(value="/download-pdf-iv")
    public void downloadPdf(@RequestParam String clave, HttpSession session, HttpServletResponse response) throws Exception {
        try {

            String fileName = this.service.getPdfFileName(clave);
            if (fileName==null) {
                throw new Exception("No se encontro el comprobante");
            }
            File fileToDownload = new File(fileName);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename="+ clave + ".pdf");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception exception){
//            System.out.println(exception.getMessage());
        }
    }

    @RequestMapping(value="/download-xml-iv")
    public void downloadXml(@RequestParam String clave, HttpSession session, HttpServletResponse response) throws Exception {
        try {

            String fileName = this.service.getXmlFile(clave);
            if (fileName==null) {
                throw new Exception("No se encontro el comprobante");
            }
            File fileToDownload = new File(fileName);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename="+ clave + ".xml");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception exception){
//            System.out.println(exception.getMessage());
        }
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    InvoiceDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getInvoice(id);
    }

    @GetMapping(path = "/get/pocs")
    public @ResponseBody
    InvoiceDTO getPocs(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.service.getPurchaseOrders(id);
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPdf(@RequestParam(required = true) Integer id, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Path p = this.service.getFacturaBasePdfPath(id);
        if (p==null) {
//            System.out.println("FACTURA NO ENCONTRADA");
            throw new Exception("Not found bill id");
        }
        Resource resource = this.service.loadFileAsResource(id, p);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            System.out.println("Could not determine file type.");
            //throw new Exception("Could not determine file type.);

        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/download-xml")
    public ResponseEntity<Resource> downloadFile(@RequestParam(required = true) Integer id, HttpServletRequest request) throws Exception {
        // Load file as Resource
//        String fileName = this.service.getXmlFileName(id);
        Path p = this.service.getFacturaPdfPath(id);
        if (p==null) {
//            System.out.println("FACTURA NO ENCONTRADA");
            throw new Exception("Not found bill id");
        }
        Resource resource = this.service.loadFileAsResource(id, p);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//            System.out.println("Could not determine file type.");
            //throw new Exception("Could not determine file type.);

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    @GetMapping(path = "/new-form-poc")
    public @ResponseBody
    InvoiceDTO getNew(
            @RequestParam(value = "pocId", required = false) Integer id
    ) {
        return this.service.getNewInvoiceByPocId(id);
    }

    @GetMapping(path = "/new-form")
    public @ResponseBody
    InvoiceDTO getNew(
    ) {
        return this.service.getInvoice(null);
    }
    @PostMapping(path="/transaction-save")
    public @ResponseBody
    Invoice savePosTransaction(@RequestBody Invoice i) throws Exception{
        return service.savePosTransaction(i);

    }

    @GetMapping(path = "/data/sales")
    public @ResponseBody
    SalesDetailInfoDTO getSalesDetailInfo(
            @RequestParam (value="startDocDate",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDocDate,
            @RequestParam (value="endDocDate",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDocDate

    ) {
        return service.getSalesDetailInfo(startDocDate, endDocDate);
    }


    @RequestMapping(path = "/sales/excel", method= RequestMethod.GET)
    public ModelAndView downloadExcelVentas(@RequestParam (value="startDocDate",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                        Date startDocDate,
                                            @RequestParam (value="endDocDate",required=true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDocDate){
        SalesDetailInfoDTO salesDetailInfoDTO = service.getSalesDetailInfo(startDocDate, endDocDate);
       Map<String, Object> modeloVentas = new HashMap<>();
       modeloVentas.put("salesDetailInfoDTO", salesDetailInfoDTO);
       return new ModelAndView(new ExcelVentasView(), modeloVentas);

    }


}
