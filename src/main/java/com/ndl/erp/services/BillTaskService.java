package com.ndl.erp.services;


import com.google.common.base.Strings;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.fe.v43.te.TiqueteElectronico;
import com.ndl.erp.repository.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.apache.commons.lang3.StringUtils;


@Service
@Transactional
public class BillTaskService {

    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    private ErrorEnvioRepository errorEnvioRepository;

    private Logger logger = Logger.getLogger("BillTaskServiceImpl");

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    @Lazy
    private InvoiceService invoiceService;

    private static final String PAIS_CODE = "506";
    private static final String SITUATION_FE = "1";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private EmailSenderService emailSenderService;


    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ConfirmaRechazaDocumentoRepository confirmacionRechazoRepository;

    @Autowired
    private InvoiceNotaCreditoRepository invoiceNotaCreditoRepository;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private PendienteMensajeHaciendaRepository pendienteMensajeHaciendaRepository;

    @Autowired
    private GeneralParameterService  generalParameterService;

    @Transactional
    @Async
    public void emitFactura(FacturaElectronica fe) {
        ResultFe result = new ResultFe();
        Integer totalSent = 0;

        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), fe.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return;
            }

            result = enviarFacturaElectronica(fe);
//            System.out.println("** RESULTADO " + result.getResultStr());

        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + e.getMessage());

            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            enviarCorreoProceso(fe,totalSent,result, result.getBillSenderDetail());
            billEmitter.desconectar();
        }

    }

    @Transactional
    @Async
    public void emitTiquete(TiqueteElectronico te) {
        ResultFe result = new ResultFe();
        Integer totalSent = 0;

        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), te.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return;
            }

            result = enviarTiqueteElectronico(te);
//            System.out.println("** RESULTADO " + result.getResultStr());

        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + e.getMessage());

            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //enviarCorreoProceso(fe,totalSent,result, result.getBillSenderDetail());
            billEmitter.desconectar();
        }

    }

    @Transactional
    @Async
    public void emitFactura(FacturaElectronicaExportacion fe) {
        ResultFe result = new ResultFe();
        Integer totalSent = 0;

        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), fe.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return;
            }

            result = enviarFacturaElectronicaExportacion(fe);
//            System.out.println("** RESULTADO " + result.getResultStr());

        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + e.getMessage());

            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            enviarCorreoProceso(fe,totalSent,result, result.getBillSenderDetail());
            billEmitter.desconectar();
        }

    }


    @Transactional
    @Async
    public void emitNotaCredito(NotaCreditoElectronica nc, Integer id, String correoDestino, String emisor, String receptor) {
        ResultFe result = new ResultFe();
        try {

            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA_NC",this.billEmitter.getMessageError(), nc.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return;
            }
            result = enviarNotaCredito(nc, id, correoDestino, emisor, receptor,nc.getTipoDocumento());
//            System.out.println("** RESULTADO " + result.getResultStr());
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        } finally {
            enviarCorreoProceso(nc, 0, result, result.getBillSenderDetail());
            billEmitter.desconectar();

        }
    }

    @Transactional
    @Async
    public ResultFe emitConfirmation(com.ndl.erp.fe.v43.mr.MensajeReceptor fe, Integer consecutivo,
                                     String correoDestino, String emisor, String receptor, String tipo) {

        ResultFe result = new ResultFe();
        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), fe.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return result;
            }
            result = enviarConfirmacion(fe, consecutivo, correoDestino, emisor, receptor,tipo);
//            System.out.println("** RESULTADO " + result.getResultStr());

        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + e.getMessage());

            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }  finally {
            enviarCorreoProcesoConfirmacion(fe, emisor, consecutivo,tipo, result, result.getBillSenderDetail());

            billEmitter.desconectar();
        }
        return result;
    }

    private ResultFe enviarConfirmacion(MensajeReceptor mr, Integer consecutivoDE, String correoDestino, String emisor, String receptor, String tipo) throws JAXBException {
        String consecutive;
        String fileName;
        OptionSet options;
        ResultFe result = new ResultFe();
        Integer totalSent = 0;
        BillSenderDetail pefd = createProcesoEmisionFacturaDetalle(consecutivoDE, getConfirmacionRechazoCode(tipo));
        generarConsecutivo(mr,tipo,consecutivoDE);
        billConfigService.initBasePath(consecutivoDE, tipo);
        pefd.setPath(billConfigService.getCompletePath());
        pefd.setClave(mr.getClave());
        fileName = getXmlFileName(mr.getClave());
        mr = fem.writeXMLFile(mr,billConfigService.getCompletePath() + fileName);
        options = billConfigService.initOptionsValues(fileName);
        if (!firmarFactura(options, BillHelper.MENSAJE_RECEPTOR_NAMESPACE_V43, mr.getId(), tipo, pefd)) {
            result.setResultStr("Hubo un error al firmar comprobante a confirmar o rechazar");
            result.setResult(-1);
            return result;
        }
        result = sendComprobante(mr, options,  pefd, tipo);
        Integer enviada = result.getResult();
        Integer validada =  billEmitter.validate(mr);
        String observaciones = result.getResultStr();
        String validadaLabel = " SI ";

        agregarPendienteMensajeHacienda(mr, pefd,tipo);

        logger.info("Factura enviada " + mr.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
//        System.out.println("Factura enviada " + mr.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(mr.getNumeroConsecutivoComprobante());
        createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,"pendiente", getConfirmacionRechazoCode(tipo));

        updateConfirmacionRechazo(mr.getId(),null,"pendiente", pefd.getId(), mr.getNumeroConsecutivoReceptor());
        result.setBillSenderDetail(pefd);
        return result;

    }


    private ResultFe enviarNotaCredito(NotaCreditoElectronica nce, Integer id, String correoDestino, String emisor, String receptor, String tipo) throws Exception {
        ResultFe result = new ResultFe();
        String consecutive;
        String fileName;
        OptionSet options;
        BillSenderDetail pefd = obtenerBillSender(nce.getId(), BillHelper.TIPO_NOTA_CREDITO_FE, nce.getClave());
        consecutive = nce.getNumeroConsecutivo();
        options = billConfigService.initOptionsValues(nce.getClave() + ".xml");
        billConfigService.initBasePath(consecutive);
        signIfNeed(options, BillHelper.NOTA_CREDITO_NAMESPACE_V43, nce.getId(), BillHelper.TIPO_FACTURA_FE, pefd);

        result = sendNC(nce, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2 || result.getResult()==-3) {

        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();
        String status = result.getEstado();
        String validadaLabel = "";
        agregarPendienteMensajeHacienda(nce, pefd);
        Integer enviadaCliente = 0;
        String filename = this.billConfigService.getPdfFileAndFullPath(nce.getClave());

        logger.info("Nota Credito enviada " + nce.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ enviadaCliente);
//        System.out.println("Nota Credito enviada " + nce.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ enviadaCliente);
        pefd.setConsecutivo(nce.getNumeroConsecutivo());
        createProcesoEmisionDetalle(nce.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, enviadaCliente, validada, observaciones,status, BillHelper.TIPO_NOTA_CREDITO_FE);
        updateNotaCredito(nce.getId(),  1, null, status,nce.getClave(),nce.getNumeroConsecutivo(),pefd.getId(), nce.getFacturaId(),-5);

        result.setBillSenderDetail(pefd);
        return result;

    }

    public void updateNotaCredito(Integer id, Integer enviadaHaciendaStatus, String estado, String estadoHacienda,
                                  String clave, String consecutivo, Integer billSenderId, Integer facturaId, Integer enviadaHaciendaFactura) {
        try {
            Optional<InvoiceNotaCredito> o = this.invoiceNotaCreditoRepository.findById(id);

            if (o==null) {
                throw new RuntimeException("Not found");
            }
            InvoiceNotaCredito notaCredito = o.get();
            notaCredito.setEnviadaHacienda(enviadaHaciendaStatus);
//            factura.setStatus(estado);
           // this.notaCreditoRepository.save(factura);

            if (estado!=null && !estado.equals("")) {
                notaCredito.setStatus(estado);
            }
            if (enviadaHaciendaStatus!=null && !enviadaHaciendaStatus.equals("")) {

                notaCredito.setEnviadaHacienda(enviadaHaciendaStatus);
            }

            if (estadoHacienda!=null && !estadoHacienda.equals("")) {

                notaCredito.setEstadoHacienda(estadoHacienda);
            }

            if (clave!=null && !clave.equals("")) {

                notaCredito.setClave(clave);
            }

            if (consecutivo!=null && !consecutivo.equals("")) {
                notaCredito.setConsecutivo(consecutivo);
            }

            if (billSenderId!=null) {

                notaCredito.setBillSenderId(billSenderId);
            }

            if (estadoHacienda.equals(BillHelper.RESPUESTA_ACEPTADA)) {
                // updateFactura(notaCredito.getInvoice().getId(),enviadaHaciendaFactura,"Anulada",estadoHacienda,null,null,null,null);
            } else {
               // updateFactura(notaCredito.getInvoice().getId(),1,"Pendiente", BillHelper.RESPUESTA_ACEPTADA,null,null,null,null);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private ResultFe enviarFacturaElectronica(FacturaElectronica fe) throws JAXBException {
        String consecutive;
        String fileName;
        ResultFe result = new ResultFe();
        OptionSet options;
        BillSenderDetail pefd = obtenerBillSender(fe.getId(), BillHelper.TIPO_FACTURA_FE, fe.getClave());
        consecutive = fe.getNumeroConsecutivo();
        options = billConfigService.initOptionsValues(fe.getClave() + ".xml");
        billConfigService.initBasePath(consecutive);
        signIfNeed(options, BillHelper.FACTURA_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_FACTURA_FE, pefd);
        result = sendFacturaElectronica(fe, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2 || result.getResult()==-3) {
            //return result;
        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();
        String status = result.getEstado();
        String validadaLabel = "";
        agregarPendienteMensajeHacienda(fe, pefd);
        String filename = this.billConfigService.getPdfFileAndFullPath(fe);
        //this.invoiceService.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), filename);
        logger.info("Factura enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
//        System.out.println("Factura enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,status, BillHelper.TIPO_FACTURA_FE);
        updateFactura(fe.getId(),  1, null, status,fe.getClave(),pefd.getConsecutivo(),pefd.getId(), true);
        result.setBillSenderDetail(pefd);
        return result;

    }

    private ResultFe enviarTiqueteElectronico(TiqueteElectronico fe) throws JAXBException {
        String consecutive;
        String fileName;
        ResultFe result = new ResultFe();
        OptionSet options;
        BillSenderDetail pefd = obtenerBillSender(fe.getId(), BillHelper.TIPO_TIQUETE_E, fe.getClave());
        consecutive = fe.getNumeroConsecutivo();
        options = billConfigService.initOptionsValues(fe.getClave() + ".xml");
        billConfigService.initBasePath(consecutive);
        signIfNeed(options, BillHelper.TIQUETE_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_TIQUETE_E, pefd);
        result = sendTiqueteElectronico(fe, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2 || result.getResult()==-3) {
            //return result;
        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();
        String status = result.getEstado();
        String validadaLabel = "";
        agregarPendienteMensajeHacienda(fe, pefd);
        //String filename = this.billConfigService.getPdfFileAndFullPath(fe);
        //this.invoiceService.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), filename);
        logger.info("Tiquete enviado " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
//        System.out.println("Factura enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,status, BillHelper.TIPO_FACTURA_FE);
        updateFactura(fe.getId(),  1, null, status,fe.getClave(),pefd.getConsecutivo(),pefd.getId(), true);
        result.setBillSenderDetail(pefd);
        return result;

    }

    private ResultFe enviarFacturaElectronicaExportacion(FacturaElectronicaExportacion fe) throws JAXBException {
        String consecutive;
        String fileName;
        ResultFe result = new ResultFe();
        OptionSet options;
        BillSenderDetail pefd = obtenerBillSender(fe.getId(), BillHelper.TIPO_FACTURA_FEE, fe.getClave());
        consecutive = fe.getNumeroConsecutivo();
        options = billConfigService.initOptionsValues(fe.getClave() + ".xml");
        billConfigService.initBasePath(consecutive);
        signIfNeed(options, BillHelper.FACTURA_ELECTRONICA_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_FACTURA_FEE, pefd);
        result = sendFacturaElectronicaExportacion(fe, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2 || result.getResult()==-3) {
            //return result;
        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();
        String status = result.getEstado();
        String validadaLabel = "";
        agregarPendienteMensajeHacienda(fe, pefd);
        String filename = this.billConfigService.getPdfFileAndFullPath(fe);
        //this.invoiceService.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), filename);
        logger.info("Factura Exportacion enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
//        System.out.println("Factura  Exportacionenviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,status, BillHelper.TIPO_FACTURA_FEE);
        updateFactura(fe.getId(),  1, null, status,fe.getClave(),pefd.getConsecutivo(),pefd.getId(), true);
        result.setBillSenderDetail(pefd);
        return result;

    }

    public void agregarError(String proceso, String mensaje, Integer facturaId) {

        try {
            if (mensaje!=null && mensaje.length()>512) {
                mensaje = mensaje.substring(0,511);
            }
            if (proceso!=null && proceso.length()>32) {
                proceso = proceso.substring(0,31);
            }
            ErrorEnvio ev = new ErrorEnvio();
            ev.setFechaIngreso(new Date());
            //ev.setIngresadaPor(((GaeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
            ev.setIngresadaPor(billConfigService.getCurrentUser());
            ev.setMensaje(mensaje);
            ev.setFacturaId(facturaId);
            ev.setProceso(proceso);
            errorEnvioRepository.save(ev);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL, "ERROR persistiendo el error " + e.getMessage());
        }


    }

    public void generarClave(NotaCreditoElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public void generarClave(FacturaElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public ResultFe getComprobanteResultAuthenticated(String clave, Integer id) {
        try {

            billEmitter.authenticate();
            ResultFe r = validateMensajeHacienda(clave,-1, id);
            if (r!=null) {

                return r;


            } else {
                return null;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage()!=null?e.getMessage():"");
        } finally {

            billEmitter.desconectar();
        }
    }

    private String generarSeguridad() {
        Random rnd = new Random();
        Integer n = 10000000 + rnd.nextInt(90000000);
        return StringUtils.leftPad(n.toString(), 8, "0");
    }

    private String generarCedula(String cedula) {
        cedula = cedula.replaceAll("-","");

        StringBuilder stringBuilder = new StringBuilder(cedula);
        while (stringBuilder.length() < 12) {
            stringBuilder.insert(0, Integer.toString(0));
        }
        return stringBuilder.toString();
    }

    private String generarConsecutivoFactura(String consecutivoFactura, String tipoDocumento) {
        String consecutivoStr =  StringUtils.leftPad(consecutivoFactura, 10, "0");
        return  "001" + "00001" + tipoDocumento + consecutivoStr;
    }

    private String crearFechaClave() {
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        Integer month = c.get(Calendar.MONTH) + 1;
        Integer ano = getAno();
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        String fechaStr = (day < 10 ? "0" + day.toString(): day.toString()) + (month < 10 ? "0" + month.toString(): month.toString()) + ano.toString();
        return fechaStr;
    }

    private int getAno() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());
        return Integer.parseInt(formattedDate);
    }

    public String getXmlFileName(String clave) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = billConfigService.getCompletePath() + fileName;
        return fileName;
    }

    private ResultFe validateMensajeHacienda(String clave, Integer validada, Integer id) throws JAXBException {
        Integer trying = 1;
        ResultFe vr = new ResultFe();
        if (clave==null) {
            vr.setResult(-2);
            vr.setResultStr("NO TIENE UNA CLAVE LA FACTURA ELECTRONICA");
            return vr;
        }
            try {
                vr = this.billEmitter.getComprobante(clave);
                if (vr==null || vr.getResult()==null) {
                    validada = -1;
                } else {
//                    System.out.println(" Estado " + vr.getEstado());

                    validada = vr.getResult();
                }
            } catch (Exception e) {
                e.printStackTrace();
                vr = new ResultFe();
                vr.setResult(-2);
                vr.setResultStr("EXCEPTION " + e.getMessage());
            }

//            trying++;
//        }
        if (validada==-1) {
            vr.setResult(-1);
            vr.setResultStr("Mensaje Hacienda no obtenido");
            agregarError("OBTENIENDO_MENSAJE",vr.getResultStr(), id);
        } else if (vr.getResult()==-99) {
//            System.out.println(vr.getResultStr());
            logger.info(vr.getResultStr());
            if (vr.getResultStr()==null) {
                vr.setResultStr("No fue recibido el comprobante");
            }
            agregarError("VALIDA_MENSAJE", vr.getResultStr(), id);

        }
//        System.out.println(" RESPUESTA de get comprobante " + validada + " " + vr.getResultStr() + " " + vr.getEstado());
        return vr;
    }

    public void notificarClienteConFactura(FacturaElectronica fe, BillSenderDetail pefd, String status, String obs, Integer validada, ResultFe result, GeneralParameter nombreEmpresaParameter) throws Exception {
//        System.out.println("*** notificarClienteConFactura status:" +  status  + "  validada " + validada + " getResult " + result.getResult());
        Integer enviadaCliente = 0;
        if (result.getResult() == 1) {
            boolean existsFile = false;
            String pdfFile = "";
            try {
                pdfFile = this.billConfigService.getPdfFileAndFullPath(pefd.getClave());
                existsFile = new File(pdfFile).isFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!existsFile)
                this.invoiceService.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);
//                billPdfGenerator.generatePdf(fe);
            String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(), fe.getClave(),
                    "FACTURA ELECTRONICA");

            enviadaCliente = enviarCorreo(fe.getClave(), fe.getCorreo(), pefd.getConsecutivo(),
                    "Comprobante Factura Electrónica " + pefd.getConsecutivo(),
                    fe.getTipoDocumento(), fe.getReceptor().getNombre(), fe.getEmisor().getNombre(), message, pefd);
            logger.info("Factura enviada " + fe.getId() + " enviada cliente ="+ enviadaCliente);
//            System.out.println("Factura enviada " + " enviada cliente ="+ enviadaCliente);
            this.enviarCorreoATenantComprobante(fe.getClave(),"Factura Electronica", result, pefd, nombreEmpresaParameter);
        } else if (result.getResult() == 3) {
            this.enviarCorreoATenantRechazo(fe.getClave(),"Factura Electronica", result, pefd, nombreEmpresaParameter);

        }
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, null,
                pefd.getEnviadaHacienda(), enviadaCliente, pefd.getValidada(), obs, status,
                BillHelper.TIPO_FACTURA_FE);
        if (validada==1 ) {
            updateFactura(fe.getId(), validada, null, status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(),null);

        } else if (status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            updateFactura(fe.getId(), 0, null, status, fe.getClave(), fe.getNumeroConsecutivo(),pefd.getId(), null);


        } else {
            updateFactura(fe.getId(), validada, null, status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(),null);

        }

    }

    public void notificarClienteConFactura(FacturaElectronicaExportacion fe, BillSenderDetail pefd, String status, String obs, Integer validada, ResultFe result, GeneralParameter nombreEmpresaParameter) throws  Exception{
//        System.out.println("*** notificarClienteConFactura status:" +  status  + "  validada " + validada + " getResult " + result.getResult());
        Integer enviadaCliente = 0;
        if (result.getResult() == 1) {
            boolean existsFile = false;
            String pdfFile = "";
            try {
                pdfFile = this.billConfigService.getPdfFileAndFullPath(pefd.getClave());
                existsFile = new File(pdfFile).isFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!existsFile)
                this.invoiceService.saveInvoice(fe.getId(), fe.getNumeroConsecutivo(), fe.getClave(), pdfFile);

            String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(), fe.getClave(),
                    "FACTURA ELECTRONICA EXPORTACION");

            enviadaCliente = enviarCorreo(fe.getNumeroConsecutivo(), fe.getCorreo(), pefd.getConsecutivo(),
                    "Comprobante Factura Electrónica Exportacion " + pefd.getConsecutivo(),
                    fe.getTipoDocumento(), fe.getReceptor().getNombre(), fe.getEmisor().getNombre(), message, pefd);
            logger.info("Factura enviada " + fe.getId() + " enviada cliente ="+ enviadaCliente);

            this.enviarCorreoATenantComprobante(fe.getNumeroConsecutivo(),"Factura Electronica Exportacion", result, pefd, nombreEmpresaParameter);

        } else if (result.getResult() == 3) {
            this.enviarCorreoATenantRechazo(fe.getClave(),"Factura Electronica Exportacion", result, pefd, nombreEmpresaParameter);

        }
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, null,
                pefd.getEnviadaHacienda(), enviadaCliente, pefd.getValidada(), obs, status,
                BillHelper.TIPO_FACTURA_FE);
        if (validada==1 ) {
            updateFactura(fe.getId(), validada, null, status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(),null);

        } else if (status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            updateFactura(fe.getId(), 0, null, status, fe.getClave(), fe.getNumeroConsecutivo(),pefd.getId(), null);


        } else {
            updateFactura(fe.getId(), validada, null, status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(),null);

        }

    }

    public void notificarClienteConNC(NotaCreditoElectronica fe, BillSenderDetail pefd, String status, String obs, Integer validada, ResultFe result, GeneralParameter nombreEmpresaParameter) {
        try {
            Integer enviadaCliente = 0;
            if (result.getResult() == 1) {
                String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(), fe.getClave(), "NOTA CREDITO ELECTRONICA");

//                String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(), "NOTA CREDITO ELECTRONICA", fe.getNumeroConsecutivo(), fe.getEmisor().getNombre());
                enviadaCliente = enviarCorreo(fe.getClave(), fe.getCorreo(), pefd.getConsecutivo(),
                        "Comprobante Nota Crédito Electrónica " + pefd.getConsecutivo(), fe.getTipoDocumento(),
                        fe.getReceptor().getNombre(), fe.getEmisor().getNombre(), message, pefd);
                logger.info("Nota Credito enviada " + fe.getId() + " enviada cliente =" + enviadaCliente);
//                System.out.println("Nota Credito enviada " + " enviada cliente =" + enviadaCliente);
                this.enviarCorreoATenantComprobante(fe.getClave(),"Nota Credito Electronica", result, pefd, nombreEmpresaParameter);

            } else if (result.getResult() == 3) {
                this.enviarCorreoATenantRechazo(fe.getClave(),"Nota Credito Electronica", result, pefd, nombreEmpresaParameter);

            }
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(),
                    pefd.getEnviadaHacienda(), enviadaCliente, pefd.getValidada(), obs, status,
                    BillHelper.TIPO_NOTA_CREDITO_FE);
            if (validada == 1 && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
                updateNotaCredito(fe.getId(),  1, null,status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(), fe.getFacturaId(),2);



            } else if (status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
                updateNotaCredito(fe.getId(), 0, null, status, fe.getClave(), fe.getNumeroConsecutivo(),pefd.getId(), fe.getFacturaId(), 1);


            } else {
                updateNotaCredito(fe.getId(),  1, null,status, fe.getClave(), fe.getNumeroConsecutivo(), pefd.getId(), fe.getFacturaId(),-5);


            }
        }
        catch(Exception e) {
            e.printStackTrace();
            logger.info("ERROR ENVIANDO CORREO AL CLIENTE " + e.getMessage());
        }
    }

    public void notificarConfirmacionRechazo(MensajeReceptor fe, BillSenderDetail pefd, String status, String obs, Integer validada, ResultFe result, String tipo, Integer enviada, GeneralParameter nombreEmpresaParameter) {
//        System.out.println("*** notificarClienteConFactura status:" +  status  + "  validada " + validada + " getResult " + result.getResult());
        Integer enviadaCliente = 0;
        if (result.getResult() == 1) {
            logger.info("Comprobante enviado " + fe.getId() + " enviada cliente ="+ enviadaCliente);
//            System.out.println("Comprobante enviado " + " enviada cliente ="+ enviadaCliente);
            this.enviarCorreoConfirmaRechazoComprobanteAceptado(fe.getClave(),"Comprobante Confirmado/Rechazado", result, pefd, nombreEmpresaParameter);
        } else if (result.getResult() == 3) {
            this.enviarCorreoConfirmaRechazoComprobanteRechazo(fe.getClave(),"Comprobante Confirmado/Rechazado", result, pefd, nombreEmpresaParameter);

        }
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, enviadaCliente, validada, obs, status, tipo);

        if (validada==1 || validada==-99) {
            updateConfirmacionRechazo(fe.getId(), null, status, pefd.getId(), null);

        } else {
            updateConfirmacionRechazo(fe.getId(), null, status, pefd.getId(), null);

        }

    }

    public Integer enviarCorreo(String clave, String correo, String consecutive, String subject, String tipo, String receptorName, String emisorName, String message, BillSenderDetail b) {
        try {

            if (Strings.isNullOrEmpty(correo)) {
                correo = "facturacion@ingpro-tec.com";
            }
            String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave);
            String pdfFile = this.billConfigService.getPdfFileAndFullPath(clave);
            String respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave);

            //TODO: cambiar
            return this.emailSenderService.sendMessageWithAttachment(correo,subject, message, xmlFile, pdfFile, respuestaFile, consecutive,tipo);
            //return this.emailSenderService.sendMessageWithAttachment("aagonzalezrojas@gmail.com", subject, message, xmlFile, pdfFile, respuestaFile, consecutive, tipo);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;

    }

    public Integer enviarDevCorreo(String clave, String consecutive, String subject, String message) {
        //correo = "facturacion@ingpro-tec.com";

        String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave);
        String pdfFile = this.billConfigService.getPdfFileAndFullPath(clave);
        String respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave);

        return this.emailSenderService.sendMessageWithAttachment("aagonzalezrojas@gmail.com",subject, message, null, null, null, consecutive, null);

    }


    private String getValidCorreoForClient(String correo) {
        //return "aagonzalezrojas@gmail.com";

        if (Strings.isNullOrEmpty(correo)) {
            return "facturacion@ingpro-tec.com";
        }
        String[] correos = correo.split(";");
        String validCorreo = "";
        String correo1="";
        boolean isInitialized = false;
        boolean isValid = false;
        for (int i = 0;i < correos.length; i++) {
            isValid = false;
            if(Strings.isNullOrEmpty(correos[i]) || !isValidEmailAddress(correos[i])) {
                correo1 = "facturacion@ingpro-tec.com";

            } else {
                correo1 = correos[i].trim().toLowerCase();
                isValid = true;

            }
            if (isInitialized && isValid) {
                validCorreo = validCorreo + "," +correo1;
            } else if (isValid) {

                validCorreo = correo1;
                isInitialized = true;
            }
        }

        return validCorreo;
    }

    public boolean isValidEmailAddress(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return false;
        }
        email = email.trim().toLowerCase();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public void updateFactura(Integer id, Integer enviadaHaciendaStatus) {
        try {
            Optional<Invoice> o = this.invoiceRepository.findById(id);
            if (o==null) {
                throw new RuntimeException("Invoice not found");
            }
            Invoice factura = o.get();
            factura.setEnviadaHacienda(enviadaHaciendaStatus);
            this.invoiceRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void agregarPendienteMensajeHacienda(MensajeReceptor fe, BillSenderDetail pefd, String tipo) {
        String tipoCR = getConfirmacionRechazoCode(tipo);
        if (this.pendienteMensajeHaciendaRepository.getPendienteMensajeHaciendaByIdAndTipoComprobante(fe.getId(), tipoCR)!=null) {
            return;
        }
        PendienteMensajeHacienda pendiente = new PendienteMensajeHacienda();
        pendiente.setBillSenderId(pefd.getId());
        pendiente.setComprobanteId(fe.getId());
        pendiente.setTipoComprobante(tipoCR);
        pendiente.setEstado(BillHelper.ESPERA);
        pendiente.setFechaIngreso(new Date());
        pendiente.setIntentos(0);


        this.pendienteMensajeHaciendaRepository.save(pendiente);
    }

    private void agregarPendienteMensajeHacienda(FacturaElectronica fe, BillSenderDetail pefd) {
        if (this.pendienteMensajeHaciendaRepository.getPendienteMensajeHaciendaByIdAndTipoComprobante(fe.getId(), BillHelper.TIPO_FACTURA_FE)!=null) {
            return;
        }
        PendienteMensajeHacienda pendiente = new PendienteMensajeHacienda();
        pendiente.setBillSenderId(pefd.getId());
        pendiente.setComprobanteId(fe.getId());
        pendiente.setTipoComprobante(BillHelper.TIPO_FACTURA_FE);
        pendiente.setEstado(BillHelper.ESPERA);
        pendiente.setFechaIngreso(new Date());
        pendiente.setIntentos(0);


        this.pendienteMensajeHaciendaRepository.save(pendiente);
    }

    private void agregarPendienteMensajeHacienda(TiqueteElectronico fe, BillSenderDetail pefd) {
        if (this.pendienteMensajeHaciendaRepository.getPendienteMensajeHaciendaByIdAndTipoComprobante(fe.getId(), BillHelper.TIPO_TIQUETE_E)!=null) {
            return;
        }
        PendienteMensajeHacienda pendiente = new PendienteMensajeHacienda();
        pendiente.setBillSenderId(pefd.getId());
        pendiente.setComprobanteId(fe.getId());
        pendiente.setTipoComprobante(BillHelper.TIPO_TIQUETE_E);
        pendiente.setEstado(BillHelper.ESPERA);
        pendiente.setFechaIngreso(new Date());
        pendiente.setIntentos(0);


        this.pendienteMensajeHaciendaRepository.save(pendiente);
    }

    private void agregarPendienteMensajeHacienda(NotaCreditoElectronica fe, BillSenderDetail pefd) {
        if (this.pendienteMensajeHaciendaRepository.getPendienteMensajeHaciendaByIdAndTipoComprobante(fe.getId(), BillHelper.TIPO_NOTA_CREDITO_FE)!=null) {
            return;
        }
        PendienteMensajeHacienda pendiente = new PendienteMensajeHacienda();
        pendiente.setBillSenderId(pefd.getId());
        pendiente.setComprobanteId(fe.getId());
        pendiente.setTipoComprobante(BillHelper.TIPO_NOTA_CREDITO_FE);
        pendiente.setEstado(BillHelper.ESPERA);
        pendiente.setFechaIngreso(new Date());
        pendiente.setIntentos(0);


        this.pendienteMensajeHaciendaRepository.save(pendiente);
    }

    private void agregarPendienteMensajeHacienda(FacturaElectronicaExportacion fe, BillSenderDetail pefd) {
        if (this.pendienteMensajeHaciendaRepository.getPendienteMensajeHaciendaByIdAndTipoComprobante(fe.getId(), BillHelper.TIPO_FACTURA_FEE)!=null) {
            return;
        }
        PendienteMensajeHacienda pendiente = new PendienteMensajeHacienda();
        pendiente.setBillSenderId(pefd.getId());
        pendiente.setComprobanteId(fe.getId());
        pendiente.setTipoComprobante(BillHelper.TIPO_FACTURA_FEE);
        pendiente.setEstado(BillHelper.ESPERA);
        pendiente.setFechaIngreso(new Date());
        pendiente.setIntentos(0);


        this.pendienteMensajeHaciendaRepository.save(pendiente);
    }

    public void updateConfirmacionRechazo(Integer id, String estado, String estadoHacienda, Integer billSenderId, String consecutivoConfirma) {
        try {
            if (id==null) {
                return;
            }
            Optional<ConfirmaRechazaDocumento> o = this.confirmacionRechazoRepository.findById(id);
            if (o==null) {
                throw new RuntimeException("Confirmacion not found");
            }
            ConfirmaRechazaDocumento factura = o.get();
            if (estadoHacienda!=null && !estadoHacienda.equals("")) {

                factura.setEstadoEnviadoHacienda(estadoHacienda);
            }


            if (billSenderId!=null) {

                factura.setBillSenderId(billSenderId);
            }

            if (consecutivoConfirma!=null && !consecutivoConfirma.equals("")) {
                factura.setNumeroConsecutivoReceptor(consecutivoConfirma);
            }


            this.confirmacionRechazoRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void updateFactura(Integer id, Integer enviadaHaciendaStatus, String estado, String estadoHacienda, String clave, String consecutivo, Integer billSenderId, Boolean hacienda) {
        try {
            if (id==null) {
                return;
            }
            Optional<Invoice> o = this.invoiceRepository.findById(id);
            if (o==null) {
                throw new RuntimeException("Not found invoice");
            }
            Invoice factura = o.get();
//            if (estado!=null && !estado.equals("")) {
//                factura.setE(estado);
//            }
            if (enviadaHaciendaStatus!=null && !enviadaHaciendaStatus.equals("")) {

                factura.setEnviadaHacienda(enviadaHaciendaStatus);
            }

            if (estado!=null && !estado.equals("")) {

                factura.setStatus(estado);
            }

            if (estadoHacienda!=null && !estadoHacienda.equals("")) {

                factura.setEstadoHacienda(estadoHacienda);
            }

            if (clave!=null && !clave.equals("")) {

                factura.setClave(clave);
            }

            if (consecutivo!=null && !consecutivo.equals("")) {
                factura.setConsecutivo(consecutivo);
            }

            if (billSenderId!=null) {

                factura.setBillSenderId(billSenderId);
            }
            if (hacienda!=null)
                factura.setHacienda(hacienda);

            this.invoiceRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private ResultFe sendFacturaElectronica(FacturaElectronica fe, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        ResultFe result = billEmitter.send(options, BillHelper.FACTURA_ELECTRONICA_BASE_XML,fe.getEsClienteInternacional());
        if (result.getResult()==-2) {
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_FACTURA", BillHelper.TIPO_FACTURA_FE);
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE);
            enviarDevCorreo(fe.getClave(), fe.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", result.getResultStr());
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {
            result.setResult(-1);
            result.setResultStr("Factura electrónica enviada exitósamente" + fe.getClave() + ".La respuesta será obtenida automáticamente en unos minutos.");
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);


            agregarError("ENVIANDO_FACTURA", BillHelper.RESPUESTA_PENDIENTE, fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE);

        }

        return result;
    }

    private ResultFe sendTiqueteElectronico(TiqueteElectronico fe, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        ResultFe result = billEmitter.send(options, BillHelper.TIQUETE_NAMESPACE_V43,fe.getEsClienteInternacional());
        if (result.getResult()==-2) {
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_FACTURA", BillHelper.TIPO_TIQUETE_E);
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_TIQUETE_E);
            enviarDevCorreo(fe.getClave(), fe.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", result.getResultStr());
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {
            result.setResult(-1);
            result.setResultStr("Tiquete electrónica enviada exitósamente" + fe.getClave() + ".La respuesta será obtenida automáticamente en unos minutos.");
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);


            agregarError("ENVIANDO_FACTURA", BillHelper.RESPUESTA_PENDIENTE, fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_TIQUETE_E);

        }

        return result;
    }

    private ResultFe sendFacturaElectronicaExportacion(FacturaElectronicaExportacion fe, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        ResultFe result = billEmitter.send(options, BillHelper.FACTURA_ELECTRONICA_EXPORTACION_BASE_XML,fe.getEsClienteInternacional());
        if (result.getResult()==-2) {
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_FACTURA", BillHelper.TIPO_FACTURA_FE);
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE);
            enviarDevCorreo(fe.getClave(), fe.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", result.getResultStr());
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {
            result.setResult(-1);
            result.setResultStr("Factura electrónica enviada exitósamente" + fe.getClave() + ".La respuesta será obtenida automáticamente en unos minutos.");
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);


            agregarError("ENVIANDO_FACTURA", BillHelper.RESPUESTA_PENDIENTE, fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE);

        }

        return result;
    }

    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd) {
        if (!billEmitter.sign(options, facturaNamespaceV42)) {
            agregarError("FIRMA", billEmitter.getMessageError(), id);
            createProcesoEmisionDetalle(id, new Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo);

            return false;
        }
        return true;
    }

    private void signIfNeed(OptionSet o, String namespace, Integer id, String tipo, BillSenderDetail pefd) {
        if (!this.existsFile(o.valueOf(BillConfigService.OUTPUT_FILE))) {
            firmarFactura(o, namespace, id, tipo, pefd);
        }
    }

    public BillSenderDetail createProcesoEmisionFacturaDetalle(Integer facturaId, String tipo) {
        Date currentDate = new Date();
        BillSenderDetail pef = new BillSenderDetail();
        Integer usuarioId = this.billConfigService.getCurrentUser();
        pef.setDateSent(currentDate);
        pef.setFechaIngreso(currentDate);
        pef.setIngresadoPor(usuarioId);
        if (tipo!=null && tipo!="") {
            pef.setTipo(tipo);
        } else {
            tipo = BillHelper.TIPO_FACTURA_FE;
            pef.setTipo(tipo);

        }

        pef = createProcesoEmisionDetalle(facturaId, currentDate, pef, usuarioId,0,0,0,"", BillHelper.PENDIENTE, tipo);
        return pef;
    }

    public BillSenderDetail createProcesoEmisionDetalle(Integer facturaId, Date currentDate, BillSenderDetail pef, Integer usuarioId, Integer enviado, Integer enviadoCliente, Integer validado, String o, String status, String tipo) {
        if (status==null || status.equals("")) {
            logger.info("No se pudo ingresar datos con status vacio");
            status = BillHelper.PENDIENTE;
        }
        pef.setFechaUltimoCambio(currentDate);
        if (usuarioId!=null) {
            pef.setUltimoCambioId(usuarioId);
        }
        if (facturaId!=null) {
            pef.setBillId(facturaId);
        }
        if (enviado!=null) {
            pef.setEnviadaHacienda(enviado);
        }
        if (enviadoCliente!=null) {
            pef.setEnviadaCliente(enviadoCliente);
        }
        if (validado!=null) {
            pef.setValidada(validado);
        }
        if (o!=null && o.length()>256) {
            o =  o.substring(0,255);
        }
        pef.setObservaciones(o);
        if (status!=null) {
            pef.setStatus(status);
        }
        if (tipo!=null) {
            pef.setTipo(tipo);
        }
        pef = billSenderDetailRepository.save(pef);

        return pef;
    }

    private ResultFe sendComprobante(MensajeReceptor mr, OptionSet options, BillSenderDetail pefd, String tipo) throws JAXBException {
        ResultFe result = billEmitter.sendConfirmacionRechazo(options, BillHelper.MENSAJE_RECEPTOR_BASE_XML, mr);
        if (result.getResult()==-2) {
            agregarError("CONFIRMA_RECHAZO",billEmitter.getMessageError(),mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "CONFIRMA_RECHAZO", tipo);
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", tipo);
            enviarDevCorreo(mr.getClave(), mr.getNumeroConsecutivoComprobante(), "NO RESPUESTA DE HACIENDA", result.getResultStr());
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {

            result.setResult(-1);

            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);

            result.setResultStr("El confirmación/rechazo de comprobante electrónico enviada exitósamente. La respuesta será obtenida automáticamente en unos minutos.");

            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);
            agregarError("CONFIRMA_RECHAZO", result.getResultStr(), mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", tipo);


        }
        return result;
    }

    private ResultFe sendNC(NotaCreditoElectronica nc, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        ResultFe result = billEmitter.send(options, BillHelper.NOTA_CREDITO_ELECTRONICA_BASE_XML, nc.getEsClienteInternacional());
        if (result.getResult()==-2) {
            agregarError("ENVIO",billEmitter.getMessageError(),nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_NC", BillHelper.TIPO_NOTA_CREDITO_FE);
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_NOTA_CREDITO_FE);
            enviarDevCorreo(nc.getClave(), nc.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", result.getResultStr());
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {

            result.setResult(-1);
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);

            result.setResultStr("Nota de Crédito enviada exitósamente con la clave " + nc.getClave() + ". La respuesta será obtenida automáticamente en unos minutos.");
            agregarError("ENVIANDO_NC", result.getResultStr(), nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_NC", BillHelper.TIPO_NOTA_CREDITO_FE);
        }

        return result;


    }

    private String getConfirmacionRechazoCode(String tipo) {
        if (tipo!=null && tipo.equals("05")) {
            return BillHelper.TIPO_CONFIRMACION_FE;
        } else {
            return BillHelper.TIPO_RECHAZO_FE;
        }
    }

    public void generarConsecutivo(MensajeReceptor fe, String tipoDocumento, Integer consecutivo) {
        fe.setNumeroConsecutivoReceptor(generarConsecutivoFactura(consecutivo.toString(), tipoDocumento));
    }

    private Integer enviarCorreoProcesoConfirmacion(MensajeReceptor fe, String emisor, Integer consecutivo, String tipo, ResultFe r, BillSenderDetail b) {
        try {
            String message = generarMensajeProceso(fe, null, r) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;

            return this.emailSenderService.sendMessageProcessResult("ENVIO DE CONFIRMACION/RECHAZO COMPROBANTE ELECTRONICO A HACIENDA", message);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;
    }

    private Integer enviarCorreoProceso(FacturaElectronica fe, Integer totalSent, ResultFe r, BillSenderDetail b) {

        String message = generarMensajeProceso(fe, totalSent, r) ;
        String xmlFile = null;
        String pdfFile = null;
        String respuestaFile = null;
        if (totalSent>0) {
            xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(fe.getClave());
            pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            respuestaFile = this.billConfigService.getRespuestaHaciendaPath(fe.getClave());
        }
        return this.emailSenderService.sendMessageProcessResult("ENVIO DE FACTURA ELECTRONICA A HACIENDA", message);
    }

    private Integer enviarCorreoProceso(FacturaElectronicaExportacion fe, Integer totalSent, ResultFe r, BillSenderDetail b) {

        String message = generarMensajeProceso(fe, totalSent, r) ;
        String xmlFile = null;
        String pdfFile = null;
        String respuestaFile = null;
        if (totalSent>0) {
            xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(fe.getClave());
            pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
            respuestaFile = this.billConfigService.getRespuestaHaciendaPath(fe.getClave());
        }
        return this.emailSenderService.sendMessageProcessResult("ENVIO DE FACTURA ELECTRONICA EXPORTACION A HACIENDA", message);
    }

    private Integer enviarCorreoProceso(NotaCreditoElectronica fe, Integer totalSent, ResultFe r, BillSenderDetail b) {
        try {

            String message = generarMensajeProceso(fe, totalSent, r) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;
            if (totalSent>0) {
                xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(fe.getClave());
                pdfFile = this.billConfigService.getPdfFileAndFullPath(fe.getClave());
                respuestaFile = this.billConfigService.getRespuestaHaciendaPath(fe.getClave());
            }

            return this.emailSenderService.sendMessageProcessResult("ENVIO DE NOTA CREDITO ELECTRONICA A HACIENDA", message);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;

    }

    private Integer enviarCorreoConfirmaRechazoComprobanteAceptado(String clave, String tipoComprobanteLabel, ResultFe r, BillSenderDetail b, GeneralParameter nombreEmpresaParameter) {
        try {

            String message = generarMensajeComprobante(tipoComprobanteLabel, clave, r, nombreEmpresaParameter) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;

            return this.emailSenderService.sendMessageProcessResultWithAttachments("Respuesta Hacienda Aceptada " + tipoComprobanteLabel, message,xmlFile,pdfFile,respuestaFile,tipoComprobanteLabel,clave);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;

    }

    private Integer enviarCorreoConfirmaRechazoComprobanteRechazo(String clave, String tipoComprobanteLabel, ResultFe r, BillSenderDetail b, GeneralParameter nombreEmpresaParameter) {
        try {

            String message = generarMensajeComprobante(tipoComprobanteLabel, clave, r, nombreEmpresaParameter) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;

            return this.emailSenderService.sendMessageProcessResultWithAttachments("Respuesta Hacienda Rechazada " + tipoComprobanteLabel, message,xmlFile,pdfFile,respuestaFile,tipoComprobanteLabel,clave);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
            return 0;

    }

    private Integer enviarCorreoATenantComprobante(String clave, String tipoComprobanteLabel, ResultFe r, BillSenderDetail b, GeneralParameter nombreEmpresaParameter) {
            try {
            String message = generarMensajeComprobante(tipoComprobanteLabel, clave, r, nombreEmpresaParameter) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;

            xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave);
            pdfFile = this.billConfigService.getPdfFileAndFullPath(clave);
            respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave);

            return this.emailSenderService.sendMessageProcessResultWithAttachments("Respuesta Hacienda Aceptada " + tipoComprobanteLabel, message,xmlFile,pdfFile,respuestaFile,tipoComprobanteLabel,clave);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;

    }

    private Integer enviarCorreoATenantRechazo(String clave, String tipoComprobanteLabel, ResultFe r, BillSenderDetail b, GeneralParameter nombreEmpresaParameter) {

        try {
            String message = generarMensajeComprobanteRechazo(tipoComprobanteLabel, clave, r, nombreEmpresaParameter) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;
            return this.emailSenderService.sendMessageProcessResultWithAttachments("Respuesta Hacienda Rechazada " + tipoComprobanteLabel, message,xmlFile,pdfFile,respuestaFile,tipoComprobanteLabel,clave);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
        return 0;

    }

    private String generarMensajeProceso(MensajeReceptor e, Integer totalSent, ResultFe mensaje1) {
        if (mensaje1.getEstado()==null || (!mensaje1.getEstado().equals(BillHelper.RESPUESTA_PENDIENTE) && !mensaje1.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA))) {
            return "Estimados, %n%n  No fue posible completar el proceso de confirmacion/rechazo para el comprobante "+ e.getNumeroConsecutivoComprobante() + " a hacienda el dia "  + dateFormat.format(new Date()) + ". " + mensaje1.getEstado() + " " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        }

        String facturas = "";

        String mensaje = " Estimados, %n%n Se  hizo un envio del confirmacion del electronico " + e.getNumeroConsecutivoComprobante() + " a Hacienda. %n%n " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        mensaje = mensaje + facturas;
        mensaje = mensaje + "%n%nSaludos Cordiales";
        return mensaje;
    }

    private String generarMensajeProceso(FacturaElectronica e, Integer totalSent, ResultFe mensaje1) {
        if (mensaje1.getEstado()==null || (!mensaje1.getEstado().equals(BillHelper.RESPUESTA_PENDIENTE) && !mensaje1.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA))) {
            return "Estimados, %n%nNo fue posible completar el proceso de envio para la factura "+ e.getNumeroConsecutivo() + " a hacienda el dia "  + dateFormat.format(new Date()) + ". " + mensaje1.getEstado() + " " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        }

        String facturas = "";

        String mensaje = " Estimados, %n%n Se  hizo un envio de la Facturas Electronica " + e.getNumeroConsecutivo() + " a Hacienda. La respuesta se obtendrá automaticámente de hacienda y será notificado por este medio. %n%n " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        mensaje = mensaje + facturas;
        mensaje = mensaje + "%n%nSaludos Cordiales";
        return mensaje;
    }

    private String generarMensajeProceso(FacturaElectronicaExportacion e, Integer totalSent, ResultFe mensaje1) {
        if (mensaje1.getEstado()==null || (!mensaje1.getEstado().equals(BillHelper.RESPUESTA_PENDIENTE) && !mensaje1.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA))) {
            return "Estimados, %n%nNo fue posible completar el proceso de envio para la factura exportacion "+ e.getNumeroConsecutivo() + " a hacienda el dia "  + dateFormat.format(new Date()) + ". " + mensaje1.getEstado() + " " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        }

        String facturas = "";

        String mensaje = " Estimados, %n%n Se  hizo un envio de la Facturas Electronica Exportacion " + e.getNumeroConsecutivo() + " a Hacienda. La respuesta se obtendrá automaticámente de hacienda y será notificado por este medio. %n%n " + mensaje1.getResultStr()!=null? mensaje1.getResultStr() : "";
        mensaje = mensaje + facturas;
        mensaje = mensaje + "%n%nSaludos Cordiales";
        return mensaje;
    }

    private String generarMensajeProceso(NotaCreditoElectronica e, Integer totalSent, ResultFe mensaje1) {
        if (mensaje1.getEstado()==null || (!mensaje1.getEstado().equals(BillHelper.RESPUESTA_PENDIENTE) && !mensaje1.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA))) {
            return "Estimados, %n%n No fue posible completar el proceso de envio para la nota de credito "+ e.getNumeroConsecutivo() + " a hacienda el dia "  + dateFormat.format(new Date()) + ". " + mensaje1.getEstado() + " " + mensaje1.getResultStr()!=null? mensaje1.getResultStr()  : "";
        }

        String facturas = "";

        String mensaje = " Estimados, %n%n Se  hizo un envio de la Nota de Credito Electronica " + e.getNumeroConsecutivo() + " a Hacienda. La respuesta se obtendrá automaticámente de hacienda y será notificado por este medio. %n%n " + mensaje1.getResultStr()!=null? mensaje1.getResultStr()  : "";
        mensaje = mensaje + facturas;
        mensaje = mensaje + "%n%nSaludos Cordiales";
        return mensaje;
    }

    private String generarMensajeComprobante(String tipoLabel, String clave, ResultFe mensaje1, GeneralParameter nombreEmpresaParameter) throws Exception {

        String mensaje = " Estimados, %n%n Se  obtuvo la aceptación de " + tipoLabel + " clave " + clave + ". Razon: %n%n " + mensaje1.getResultStr() + "%n%n" ;

        mensaje = mensaje + "%n%nSaludos Cordiales. %n%n " + nombreEmpresaParameter.getVal();
        return mensaje;
    }

    private String generarMensajeComprobanteRechazo(String tipoLabel, String clave, ResultFe mensaje1, GeneralParameter nombreEmpresaParameter) throws Exception {

        String mensaje = " Estimados, %n%n Se  obtuvo el rechazo de " + tipoLabel + " clave " + clave + ". Razon: %n%n " + mensaje1.getResultStr() + "%n%n" ;

        mensaje = mensaje + "%n%nSaludos Cordiales. %n%n " +  nombreEmpresaParameter.getVal();
        return mensaje;
    }

    private BillSenderDetail obtenerBillSender(Integer id, String tipo, String clave) {
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByClave(clave);
        if (l!=null && l.size()>0) {
            return l.get(0);
        }
        return createProcesoEmisionFacturaDetalle(id, tipo);
//        return createProcesoEmisionFacturaDetalle(id, BillHelper.TIPO_FACTURA_FE);
    }

    private Boolean existsFile(String file) {
        boolean existsFile = false;

        try {

            existsFile = new File(file).isFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existsFile;
    }



}
