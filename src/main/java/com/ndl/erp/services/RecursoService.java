package com.ndl.erp.services;


import com.ndl.erp.domain.Recurso;
import com.ndl.erp.domain.User;
import com.ndl.erp.dto.RecursosDTO;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.RecursoRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.ndl.erp.constants.RecursoConstants.RECURSO_TIPO_REFERENCIA_PRODUCTO;


@Component
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;


    private final Path rootConfirmationLocation = Paths.get("content");



    private Path fileStorageLocation;

    @Autowired
    private UserServiceImpl usuarioService;



    public RecursosDTO getRecursoInmuebleById(Integer referenciaId, String tipoReferencia) {

        RecursosDTO d = new RecursosDTO();

        d.setRecursos(this.recursoRepository.findByReferenciaIdAndTipoReferencia(referenciaId,tipoReferencia));
        if (tipoReferencia.equals(RECURSO_TIPO_REFERENCIA_PRODUCTO)) {
            d.setTipoReferencia(RECURSO_TIPO_REFERENCIA_PRODUCTO);
        } else {
            throw new NotFoundException("Tipo de referencia a recurso desconocida");
        }

        return d;
    }



    public Recurso save(Recurso r) {

        return this.recursoRepository.save(r);
    }


    public Recurso loadDataFromFile(MultipartFile file, Integer referenciaId, String tipoReferencia) throws Exception {

        return storeConfirmation(file, referenciaId, tipoReferencia);
    }



    private Recurso storeConfirmation(MultipartFile file, Integer referenciaId, String tipoReferencia) throws Exception {

        String contentType = file.getContentType();
        String fullFileName = "";
        String path = "";
        try {
            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectories(rootConfirmationLocation);

                }
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!");
            }

            //String file

            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectories(rootConfirmationLocation);

                }
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!");
            }
            Path p = this.rootConfirmationLocation.resolve(file.getOriginalFilename());
            if (Files.exists(p)) {
                Files.delete(p);
            }

            Files.copy(file.getInputStream(), this.rootConfirmationLocation.resolve(file.getOriginalFilename()));
            fullFileName = this.rootConfirmationLocation.toString() + File.separator + file.getOriginalFilename();

        } catch (IOException e) {
            throw new RuntimeException("Could not delete or copy the file");
        }

        return addNewRecurso(fullFileName, path + file.getOriginalFilename(), referenciaId, tipoReferencia, contentType);
    }


    private Recurso addNewRecurso(String fullFileName, String path, Integer referenciaId, String tipoReferencia, String contentType) throws Exception {

        Date date = new Date(DateUtil.getCurrentCalendar().getTime().getTime());
        Recurso recurso = new Recurso();
        recurso.setReferenciaId(referenciaId);
        recurso.setTipoReferencia(tipoReferencia);
        recurso.setTipoRecurso(contentType);
        recurso.setUriRecurso(fullFileName);
        recurso.setNombreRecurso(path);

        recurso.setFechaAgregado(date);
        User u = this.usuarioService.getCurrentLoggedUser();

        recurso.setUserAgregadoPor(u);
        recurso.setFechaUltimaActualizacion(date);

        return this.recursoRepository.save(recurso);

    }

    public String getFileName(Integer id) {

        Recurso ri = this.recursoRepository.getOne(id);
        if (ri!=null) {
            Path p = this.rootConfirmationLocation.resolve(ri.getNombreRecurso());
            return p.getFileName().toString();

        }
        return null;
    }

    public Resource loadFileAsResource(Integer id, String fileName) throws Exception {
        try {
            //String fileName = getXmlFileName(id);
            initPath(id);
            if (fileStorageLocation==null){
                throw new Exception("Error al obtener el archivo XML");
            }
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Path filePath = this.fileStorageLocation.normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + id, ex);
        }
    }

    public void initPath(Integer id) {
        Recurso ri = this.recursoRepository.getById(id);
        if (ri!=null) {

            this.fileStorageLocation = Paths.get(ri.getUriRecurso())
                    .toAbsolutePath().normalize();

        }

    }

    public boolean delete(Integer id) {
        Recurso r = this.recursoRepository.getById(id);

        deleteFile(r);
        this.recursoRepository.delete(r);
        return true;
    }

    public void  deleteFile(Recurso r)  {
        try {
            //String fileName = getXmlFileName(id);
            this.fileStorageLocation = Paths.get(r.getUriRecurso());
            if (fileStorageLocation==null){
                System.out.println("*****Error al obtener el archivo ");
                return;
            }
            Path filePath = this.fileStorageLocation.normalize();
            Files.delete(filePath);




        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
