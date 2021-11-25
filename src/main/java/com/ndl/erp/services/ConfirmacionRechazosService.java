package com.ndl.erp.services;

import com.ndl.erp.domain.ConfirmaRechazaDocumento;
import com.ndl.erp.dto.ConfirmaRechazaDTO;
import com.ndl.erp.dto.FacturaElectronicaDTO;
import com.ndl.erp.dto.HaciendaMensajeDTO;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.core.BillConfigurationData;
import com.ndl.erp.fe.core.impl.BillConfigurationDataImpl;
import com.ndl.erp.fe.dtos.Emisor;
import com.ndl.erp.fe.dtos.Receptor;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.repository.ConfirmaRechazaDocumentoRepository;

import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ConfirmacionRechazosService {
    @Autowired
    private FacturaElectronicaUnmarshaller fu;

    @Autowired
    private ConfirmaRechazaDocumentoRepository confirmaRechazaDocumento;

    private final Path rootConfirmationLocation = Paths.get("confirm-dir");

    @Autowired
    private FacturaElectronicaUnmarshaller feu;

    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private UserService usuarioService;


//    private BillM billManagerService;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    private BillTaskService billTaskService;

    @Autowired
    private BillConfigurationDataImpl billConfigurationData;

    public ConfirmaRechazaDTO getConfirmacionRechazosList(String emisor, String estado,
            Date fechaInicio, Date fechaFinal,
              Integer pageNumber,
              Integer pageSize,
              String sortDirection,
              String sortField) {
        ConfirmaRechazaDTO result = new ConfirmaRechazaDTO();
        Integer t = 0;
        Page<ConfirmaRechazaDocumento> list = null;
        if (fechaInicio == null && fechaFinal == null) {
            Date[] dates = initDateRango(fechaInicio, fechaFinal);
            fechaFinal = dates[1];
            fechaInicio = dates[0];
        }

        if(emisor != null && estado != null ){
             list = this.confirmaRechazaDocumento.getConfirmaRechazaDocumentoEmisorAndEstado(emisor, estado, fechaInicio, fechaFinal,
                     createPageable(pageNumber, pageSize, sortDirection, sortField));
             t = this.confirmaRechazaDocumento.countConfirmaRechazaDocumentooEmisorAndEstado(emisor, estado, fechaInicio, fechaFinal);
//            result.setConfirmRechazos(list);
//            result.setTotal(t);
        } else if(emisor == null && estado != null){
             list = this.confirmaRechazaDocumento.getConfirmaRechazaDocumentoEstado( estado, fechaInicio, fechaFinal,
                     createPageable(pageNumber, pageSize, sortDirection, sortField));
            t = this.confirmaRechazaDocumento.countConfirmaRechazaDocumentoEstado(estado, fechaInicio, fechaFinal);
//            result.setConfirmRechazos(list);
//            result.setTotal(t);

        } else if(estado == null && emisor != null){
             list = this.confirmaRechazaDocumento.getConfirmaRechazaDocumentoEmisor( emisor, fechaInicio, fechaFinal,
                     createPageable(pageNumber, pageSize, sortDirection, sortField));

            t = this.confirmaRechazaDocumento.countConfirmaRechazaDocumentoEmisor(emisor, fechaInicio, fechaFinal);
//            result.setConfirmRechazos(list);
//            result.setTotal(t);

        } else{
            list = this.confirmaRechazaDocumento.getConfirmaRechazaDocumentoFechas(fechaInicio, fechaFinal,
                    createPageable(pageNumber, pageSize, sortDirection, sortField));
            t = this.confirmaRechazaDocumento.countConfirmaRechazaDocumentooFechas(fechaInicio, fechaFinal);




        }

        result.setConfirmRechazos(list);
        result.setTotal(t);

        if (result.getTotal()>0) {
            result.setPagesTotal((result.getTotal() /pageSize) + 1);
        } else {
            result.setPagesTotal(0);
        }


        result.setFechaInicio(fechaInicio);
        result.setFechaFinal(fechaFinal);

        List<String> states = new ArrayList<String>();
        states.add("CONFIRMADO");
        states.add("RECHAZADO");

        result.setEstados(states);

        return result;
    }

    public HaciendaMensajeDTO loadDataFromFile(MultipartFile file) throws JAXBException, IOException {

        return storeConfirmation(file);

    }

    private HaciendaMensajeDTO storeConfirmation(MultipartFile file) throws IOException, JAXBException {
        String fullFileName = "";
        Calendar calendar = new GregorianCalendar();

        HaciendaMensajeDTO dto = null;

        String path = this.billConfigurationData.getBillClientPath() + calendar.get(Calendar.YEAR) + File.separator + (calendar.get(Calendar.MONTH) + 1) + File.separator;

        try {
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

//            try {
//                FacturaElectronica fe = this.fu.readXMLFile(fullFileName);
//                dto = new HaciendaMensajeDTO(fe);
//                                    dto.setFe(fe);
//                //BeanUtils.copyProperties(fe,dto);
//                dto.setClave(fe.getClave());
//                dto.setConsecutivo(fe.getNumeroConsecutivo());
//                dto.setFechaEmision(fe.getFechaEmision().toString());
//                if (fe.getEmisor()!=null) {
//                    dto.setNombreEmisor(fe.getEmisor().getNombre());
//                    if (fe.getEmisor().getIdentificacion()!=null) {
//                        dto.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
//                        dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
//                    }
//                }
//                if (fe.getReceptor()!=null) {
//                    dto.setNombreReceptor(fe.getReceptor().getNombre());
//                    if (fe.getReceptor().getIdentificacion()!=null) {
//                        dto.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
//
//                        dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
//                    }
//                }
//                dto.setTipo("FE");
////                dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
////                dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
//                dto.setFileFormatIndefine(true);
//
//
//
//
//
//            } catch (UnmarshalException e) {
//                NotaCreditoElectronica fe = this.fu.readXMLNCFile(fullFileName);
//                dto = new HaciendaMensajeDTO(fe);
//                dto.setNc(fe);
//                //BeanUtils.copyProperties(fe,dto);
//                dto.setClave(fe.getClave());
//                dto.setConsecutivo(fe.getNumeroConsecutivo());
//                dto.setFechaEmision(fe.getFechaEmision().toString());
//                if (fe.getEmisor()!=null) {
//                    dto.setNombreEmisor(fe.getEmisor().getNombre());
//                    if (fe.getEmisor().getIdentificacion()!=null) {
//                        dto.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
//                        dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
//                    }
//                }
//                if (fe.getReceptor()!=null) {
//                    dto.setNombreReceptor(fe.getReceptor().getNombre());
//                    if (fe.getReceptor().getIdentificacion()!=null) {
//                        dto.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
//
//                        dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
//                    }
//                }
//
//                dto.setTipo("NC");
//
//
//                dto.setFileFormatIndefine(true);
//


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FAIL! " + e.getMessage());
        }
            return getHaciendaMensajeDTO(fullFileName, true);
//        return dto;
    }

    public HaciendaMensajeDTO getHaciendaMensajeDTO(String fullFileName, boolean isFullPath) throws JAXBException {
        if (!isFullPath)
            fullFileName = this.rootConfirmationLocation.toString() + File.separator + fullFileName;
        HaciendaMensajeDTO dto = null;
        try {
            FacturaElectronica fe = this.fu.readXMLFile(fullFileName);
            dto = new HaciendaMensajeDTO(fe);
            dto.setFe(fe);
            //BeanUtils.copyProperties(fe,dto);
            dto.setClave(fe.getClave());
            dto.setConsecutivo(fe.getNumeroConsecutivo());
            dto.setFechaEmision(fe.getFechaEmision().toString());
            if (fe.getEmisor()!=null) {
                dto.setNombreEmisor(fe.getEmisor().getNombre());
                if (fe.getEmisor().getIdentificacion()!=null) {
                    dto.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
                    dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
                }
            }
            if (fe.getReceptor()!=null) {
                dto.setNombreReceptor(fe.getReceptor().getNombre());
                if (fe.getReceptor().getIdentificacion()!=null) {
                    dto.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());

                    dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
                }
            }
            dto.setTipo("FE");
//                dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
//                dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
            dto.setFileFormatIndefine(true);





        } catch (UnmarshalException e) {
            NotaCreditoElectronica fe = this.fu.readXMLNCFile(fullFileName);
            dto = new HaciendaMensajeDTO(fe);
            dto.setNc(fe);
            //BeanUtils.copyProperties(fe,dto);
            dto.setClave(fe.getClave());
            dto.setConsecutivo(fe.getNumeroConsecutivo());
            dto.setFechaEmision(fe.getFechaEmision().toString());
            if (fe.getEmisor()!=null) {
                dto.setNombreEmisor(fe.getEmisor().getNombre());
                if (fe.getEmisor().getIdentificacion()!=null) {
                    dto.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
                    dto.setTipoIdentificacionEmisor(fe.getEmisor().getIdentificacion().getTipo());
                }
            }
            if (fe.getReceptor()!=null) {
                dto.setNombreReceptor(fe.getReceptor().getNombre());
                if (fe.getReceptor().getIdentificacion()!=null) {
                    dto.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());

                    dto.setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
                }
            }

            dto.setTipo("NC");


            dto.setFileFormatIndefine(true);


    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("FAIL! " + e.getMessage());
    }
        return dto;
    }

    public FacturaElectronicaDTO getFacturaElectronica(String fileName) throws JAXBException {
        FacturaElectronicaDTO result = new FacturaElectronicaDTO();
        String fullFileName = this.rootConfirmationLocation.toString() + File.separator + fileName;
        result.setFacturaElectronica(feu.readXMLFile(fullFileName));
        result.setFullPathFileName(fileName);
        return result;
    }

    public FacturaElectronicaDTO getNotaCreditoElectronica(String fileName) throws JAXBException {
        FacturaElectronicaDTO result = new FacturaElectronicaDTO();
        String fullFileName = this.rootConfirmationLocation.toString() + File.separator + fileName;
        result.setNotaCreditpElectronica(feu.readXMLNCFile(fullFileName));
        result.setFullPathFileName(fileName);
        return result;
    }


    private Date[] initDateRango(Date fechaInicio, Date fechaFinal) {

        if (fechaInicio == null || fechaFinal == null) {
            fechaFinal = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinal);
            
            cal.add(Calendar.WEEK_OF_YEAR, -1); // Fecha semana pasada
            fechaInicio = cal.getTime();
        }

        return new Date[]{fechaInicio, fechaFinal};
    }

//    public ResultFe confirmFacturaElectronica(FacturaElectronica fe, String fileName, String mensaje, Boolean isConfirm) throws JAXBException, DatatypeConfigurationException {
    public ResultFe confirmFacturaElectronica( String fileName, String mensaje, Boolean isConfirm) throws JAXBException, DatatypeConfigurationException {
        ResultFe r = null;

        if (mensaje==null || mensaje.equals("") || mensaje.length()>80) {
            r = new ResultFe();
            r.setResult(-1);
            r.setResultStr("Comprobante Electronico no enviado. El mensaje es requerido y debe ser menor de 80 caracteres");
            r.setEstado(BillHelper.ENVIANDO);
            return r;
        }
        FacturaElectronicaDTO fedto = getFacturaElectronica(fileName);

        ConfirmaRechazaDocumento c = saveConfirmacionRechazo(fileName, fedto, (isConfirm?"CONFIRMADO":"RECHAZADO"), mensaje);

        if (c!=null) {
            // fedto.set
            r =  this.confirmarRechazarFe(fedto.getFacturaElectronica(), isConfirm, mensaje);
            //r = new ResultFe();
            if (r==null) {
                r = new ResultFe();
            }
           // r.setResult(-1);
            if (r.getResultStr()==null || r.getResultStr().equals(""))
                r.setResultStr("Factura Electronica se esta enviando. El estado del envio será actualizado automaticamente. ");

            if (r.getEstado()==null || r.getEstado().equals("")) {
                r.setEstado("pendiente");
            }

            if (r.getResult()==null ) {
                Integer i = -1;
                r.setResult(i);
            }

        } else {
            r = new ResultFe();
            r.setResult(0);
            r.setResultStr("Comprobante no fue enviado. Error al intentar guardar en la base de datos la confirmacion");
            r.setEstado(BillHelper.NO_ENVIADA);
        }

        return r;

    }

    public ResultFe confirmNotaCreditoElectronica( String fileName, String mensaje, Boolean isConfirm) throws JAXBException, DatatypeConfigurationException {
        ResultFe r = null;

        if (mensaje==null || mensaje.equals("") || mensaje.length()>80) {
            r = new ResultFe();
            r.setResult(-1);
            r.setResultStr("Comprobante Electronico no enviado. El mensaje es requerido y debe ser menor de 80 caracteres");
            r.setEstado(BillHelper.ENVIANDO);
            return r;
        }
        FacturaElectronicaDTO fedto = getNotaCreditoElectronica(fileName);

        ConfirmaRechazaDocumento c = saveConfirmacionRechazoNc(fileName, fedto, (isConfirm?"CONFIRMADO":"RECHAZADO"), mensaje);

        if (c!=null) {
            // fedto.set
            r =  this.confirmarRechazarNc(fedto.getNotaCreditpElectronica(), isConfirm, mensaje);
            //r = new ResultFe();
            if (r==null) {
                r = new ResultFe();
            }
           // r.setResult(-1);
            if (r.getResultStr()==null || r.getResultStr().equals(""))
                r.setResultStr("Factura Electronica se esta enviando. El estado del envio será actualizado automaticamente. ");

            if (r.getEstado()==null || r.getEstado().equals("")) {
                r.setEstado("pendiente");
            }

            if (r.getResult()==null ) {
                Integer i = -1;
                r.setResult(i);
            }

        } else {
            r = new ResultFe();
            r.setResult(0);
            r.setResultStr("Comprobante no fue enviado. Error al intentar guardar en la base de datos la confirmacion");
            r.setEstado(BillHelper.NO_ENVIADA);
        }

        return r;

    }

    private ConfirmaRechazaDocumento saveConfirmacionRechazo(String fileName, FacturaElectronicaDTO fedto, String estado, String mensaje) {
        FacturaElectronica fe = fedto.getFacturaElectronica();
        ConfirmaRechazaDocumento cd = null;
        try {
            cd = this.confirmaRechazaDocumento.findByClave(fe.getClave());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cd==null)
            cd = new ConfirmaRechazaDocumento();
        cd.setClave(fe.getClave());
//        cd.setFechaEmision(fedto.getFacturaElectronica().getFechaEmision().toGregorianCalendar().getTime());
//        java.sql.Date emisionDate = new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())
        cd.setFechaEmision(new java.sql.Date((fe.getFechaEmision().toGregorianCalendar().getTime().getTime())));


        cd.setConsecutivo(fe.getNumeroConsecutivo());

        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
            cd.setMoneda("CRC");
        } else {
            cd.setMoneda(fe.getResumenFactura().getCodigoTipoMoneda().getCodigoMoneda());

        }

        cd.setEmisor(fe.getEmisor().getNombre());
        cd.setIdentificacionEmisor(fe.getEmisor().getIdentificacion().getNumero());

        cd.setTotalVenta(getValue(fe.getResumenFactura().getTotalVenta()));
        cd.setTotalImpuesto(getValue(fe.getResumenFactura().getTotalImpuesto()));
        cd.setTotalComprobante(getValue(fe.getResumenFactura().getTotalComprobante()));

        cd.setPathOriginalFile(this.rootConfirmationLocation.toString() + File.separator + fileName);
        cd.setEstado(estado);

        cd.setTipo("FE");
        cd.setFechaUltimoCambio(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        Long u = this.usuarioService.getCurrentLoggedUser().getId();
        cd.setUltimoCambioPor(u.intValue());

        cd.setEstadoEnviadoHacienda("enviando");
        cd.setMensaje(mensaje);

        ConfirmaRechazaDocumento c = this.confirmaRechazaDocumento.save(cd);
        fe.setId(c.getId());
        return cd;
    }

    private ConfirmaRechazaDocumento saveConfirmacionRechazoNc(String fileName, FacturaElectronicaDTO fedto, String estado, String mensaje) {
        NotaCreditoElectronica fe = fedto.getNotaCreditpElectronica();
        ConfirmaRechazaDocumento cd = null;
        try {
            cd = this.confirmaRechazaDocumento.findByClave(fe.getClave());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cd==null)
            cd = new ConfirmaRechazaDocumento();
        cd.setClave(fe.getClave());

        cd.setFechaEmision(new java.sql.Date((fe.getFechaEmision().toGregorianCalendar().getTime().getTime())));


        cd.setConsecutivo(fe.getNumeroConsecutivo());

        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
            cd.setMoneda("CRC");
        } else {
            cd.setMoneda(fe.getResumenFactura().getCodigoTipoMoneda().getCodigoMoneda());

        }

        cd.setEmisor(fe.getEmisor().getNombre());
        cd.setIdentificacionEmisor(fe.getEmisor().getIdentificacion().getNumero());

        cd.setTotalVenta(getValue(fe.getResumenFactura().getTotalVenta()));
        cd.setTotalImpuesto(getValue(fe.getResumenFactura().getTotalImpuesto()));
        cd.setTotalComprobante(getValue(fe.getResumenFactura().getTotalComprobante()));

        cd.setPathOriginalFile(this.rootConfirmationLocation.toString() + File.separator + fileName);
        cd.setEstado(estado);
        cd.setTipo("NC");
        cd.setFechaUltimoCambio(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        Long u = this.usuarioService.getCurrentLoggedUser().getId();
        cd.setUltimoCambioPor(u.intValue());

        cd.setEstadoEnviadoHacienda("enviando");
        cd.setMensaje(mensaje);

        ConfirmaRechazaDocumento c = this.confirmaRechazaDocumento.save(cd);
        fe.setId(c.getId());
        return cd;
    }


    public ResultFe confirmarRechazarFe(FacturaElectronica fe, boolean confirmar, String mensaje) throws DatatypeConfigurationException {
        MensajeReceptor mensajeReceptor = new MensajeReceptor();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new java.util.Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        mensajeReceptor.setFechaEmisionDoc(date2);
        mensajeReceptor.setClave(fe.getClave());
        mensajeReceptor.setId(fe.getId());
        if (fe.getReceptor()!=null && fe.getReceptor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaReceptor("");
        }
        if (fe.getEmisor()!=null && fe.getEmisor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaEmisor("");

        }
        String tipo = BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO;
        if (confirmar) {
            tipo = BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO;
            mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_ACEPTADO));
        } else {
            tipo = BillHelper.CONFIRMACION_RECHAZO_COMPROBANTE;
            mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_RECHAZADO));
        }
        mensajeReceptor.setDetalleMensaje(mensaje);
        mensajeReceptor.setMontoTotalImpuesto(fe.getResumenFactura().getTotalImpuesto());
        mensajeReceptor.setTotalFactura(fe.getResumenFactura().getTotalVentaNeta());
        mensajeReceptor.setNumeroConsecutivoFe(globalDataManager.getConfirmacionNext());
//        mensajeReceptor.setClave(fe.getClave());

        mensajeReceptor.setEmisor(new Emisor(fe.getEmisor()));
        mensajeReceptor.setReceptor(new Receptor(fe.getReceptor()));
        mensajeReceptor.setNumeroConsecutivoReceptor(fe.getNumeroConsecutivo());
        mensajeReceptor.setNumeroConsecutivoComprobante(fe.getNumeroConsecutivo());
        return billTaskService.emitConfirmation(mensajeReceptor, mensajeReceptor.getNumeroConsecutivoFe(), fe.getEmisor().getCorreoElectronico(), fe.getEmisor().getNombre(), fe.getReceptor().getNombre(), BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO);
//        ResultFe rfe = new ResultFe();
//        rfe.setResult(-1);
//        rfe.setResultStr("La confirmación/rechazo de la factura electronica se esta enviando. La respuesta se obtendrá  automaticamente.");
//        rfe.setEstado("pendiente");
//        return rfe;
    }


    public ResultFe confirmarRechazarNc(NotaCreditoElectronica fe, boolean confirmada, String mensaje) throws DatatypeConfigurationException {
        MensajeReceptor mensajeReceptor = new MensajeReceptor();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new java.util.Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        mensajeReceptor.setFechaEmisionDoc(date2);
        mensajeReceptor.setClave(fe.getClave());
        mensajeReceptor.setId(fe.getId());
        if (fe.getReceptor()!=null && fe.getReceptor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaReceptor("");
        }
        if (fe.getEmisor()!=null && fe.getEmisor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaEmisor("");

        }
        String tipo = BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO;
        if (confirmada) {
            tipo = BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO;
            mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_ACEPTADO));
        } else {
            tipo = BillHelper.CONFIRMACION_RECHAZO_COMPROBANTE;
            mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_RECHAZADO));
        }
        mensajeReceptor.setDetalleMensaje(mensaje);
        mensajeReceptor.setMontoTotalImpuesto(fe.getResumenFactura().getTotalImpuesto());
        mensajeReceptor.setTotalFactura(fe.getResumenFactura().getTotalVentaNeta());
        mensajeReceptor.setNumeroConsecutivoFe(globalDataManager.getConfirmacionNext());
//        mensajeReceptor.setClave(fe.getClave());

        mensajeReceptor.setEmisor(new Emisor(fe.getEmisor()));
        mensajeReceptor.setReceptor(new Receptor(fe.getReceptor()));
        mensajeReceptor.setNumeroConsecutivoReceptor(fe.getNumeroConsecutivo());
        mensajeReceptor.setNumeroConsecutivoComprobante(fe.getNumeroConsecutivo());
        return billTaskService.emitConfirmation(mensajeReceptor,mensajeReceptor.getNumeroConsecutivoFe(),fe.getEmisor().getCorreoElectronico(),fe.getEmisor().getNombre(), fe.getReceptor().getNombre(), tipo);
//        ResultFe rfe = new ResultFe();
//        rfe.setResult(-1);
//        rfe.setResultStr("La confirmación/rechazo de la nota de crédito electrónica se esta enviando. La respuesta se obtendrá  automaticamente.");
//        rfe.setEstado("pendiente");
//        return rfe;
    }

    private double getValue(BigDecimal v) {
        if (v==null){
            return 0d;
        }
        return v.doubleValue();
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
