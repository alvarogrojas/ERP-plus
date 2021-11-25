package com.ndl.erp.controller;


import com.ndl.erp.domain.Recurso;
import com.ndl.erp.dto.RecursosDTO;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.services.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    @Autowired
    private RecursoService service;

    @GetMapping(path = "/get")
    public @ResponseBody
    RecursosDTO get (
            @RequestParam(value = "referenciaId", required = false) Integer referenciaId,
            @RequestParam(value = "tipoReferencia", required = false) String tipoReferencia
    ) throws Exception {
        return this.service.getRecursoInmuebleById(referenciaId,tipoReferencia);
    }

    @PostMapping("/upload")
    public @ResponseBody
    Recurso handleFileUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "referenciaId", required = false) Integer referenciaId,
                             @RequestParam(value = "tipoReferencia", required = false) String tipoReferencia

    ) {
        String message = "";

        Recurso result = null;
        try {
            result = service.loadDataFromFile(file, referenciaId, tipoReferencia);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    boolean delete(@RequestParam(required = true) Integer id)
    {
        return service.delete(id);
    }


    @GetMapping("/download")
    public ResponseEntity<Resource>
    downloadFile(@RequestParam(required = true) Integer id,
                 HttpServletRequest request) throws Exception {
        // Load file as Resource
        String fileName = this.service.getFileName(id);
        if (fileName==null) {

            throw new NotFoundException("Id del recurso no econtrado!");
        }
        Resource resource = this.service.loadFileAsResource(id,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

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
}
