package com.ndl.erp.services;


import com.ndl.erp.domain.*;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.repository.*;
//import com.ndl.fe.v43.FacturaElectronica;
//import com.ndl.fe.v43.fee.FacturaElectronicaExportacion;
//import com.ndl.fe.v43.mr.MensajeReceptor;
//import com.ndl.fe.v43.nc.NotaCreditoElectronica;
//import com.ndl.helpers.BillHelper;
//import org.bitsoftware.inventory.dao.InvoiceNotaCreditoRepository;
//import org.bitsoftware.inventory.dao.InvoiceRepository;
//import org.bitsoftware.inventory.dao.impl.BillSenderDetailRepositoryImpl;
//import org.bitsoftware.inventory.dao.impl.HistoricoMensajeHaciendaRepositoryImpl;
//import org.bitsoftware.inventory.dao.impl.PendienteMensajeHaciendaRepositoryImpl;
//import org.bitsoftware.inventory.dto.ResultFe;
//import org.bitsoftware.inventory.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_DATOS_EMPRESA;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_NOMBRE_EMPRESA;

@Component
@Transactional
public class FacturaMensajeHaciendaTask {

    @Autowired
    private FacturaElectronicaUnmarshaller facturaElectronicaUnmarshaller;

    private Logger logger = Logger.getLogger("FacturaMensajeHaciendaTask");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private PendienteMensajeHaciendaRepository pendienteMensajeHaciendaRepository;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private BillTaskService billTaskService;


    @Autowired
    private InvoiceRepository facturaRepository;

    @Autowired
    private InvoiceNotaCreditoRepository notaCreditoRepository;


    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private HistoricoMensajeHaciendaRepository historicoMensajeHaciendaRepository;

    @Autowired
    private GeneralParameterService generalParameterService;


    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    public void obtenerMensajeHacienda() throws Exception {
//        System.out.println("INGRESANDO A OBTENER MENSAJE HACIENDA");
        List<PendienteMensajeHacienda> l = pendienteMensajeHaciendaRepository.findByEstado(BillHelper.ESPERA);
        BillSenderDetail bsd = null;
        GeneralParameter nombreEmpresaParameter;
        nombreEmpresaParameter = this.generalParameterService.getGeneralParameterByNameAndCode(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        ResultFe r;
        Boolean connected = false;
        try {
            if (l!=null && l.size()>0) {
                billEmitter.authenticate();
                connected = true;
                int total = 0;
                for (PendienteMensajeHacienda pmh : l) {
                    //                System.out.println("PendienteMensajeHacienda " + pmh.getComprobanteId());

                    Optional<BillSenderDetail> bsdo = billSenderDetailRepository.findById(pmh.getBillSenderId());
                    if (bsdo == null) {
                        throw new RuntimeException("Not found ");
                    }
                    bsd = bsdo.get();
                    r = obtenerMensajeHacienda(bsd, bsd.getBillId(), pmh.getTipoComprobante(), nombreEmpresaParameter);

                    actualizarBitacoras(r, pmh, bsd);
                    total++;
                    if (total >= BillHelper.MAX_SENDER_EACH_FIVE_MINUTES) {
                        break;
                    }

                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (connected) {
                billEmitter.desconectar();
            }
        }
    }

    private void actualizarBitacoras(ResultFe r, PendienteMensajeHacienda pmh, BillSenderDetail bsd) {
        if (r==null) {
            if (pmh.getIntentos() < 100) {
                pmh.setIntentos(pmh.getIntentos() + 1);
                this.pendienteMensajeHaciendaRepository.save(pmh);
                return;
            } else {
                String mensaje = "Se intento obtener respuesta durante el limite de intentos y no fue posible. Revisar la bitacora para " + bsd.getClave() + " consecutivo " + bsd.getConsecutivo();
                this.billTaskService.enviarDevCorreo(bsd.getClave(), bsd.getConsecutivo(), "--> RESPUESTA NO OBTENIDA", "");
            }
            // PROCESANDO: Mover datos a tabla en es espera
        }
        HistoricoMensajeHacienda historicoMensajeHacienda = new HistoricoMensajeHacienda();
        historicoMensajeHacienda.setComprobanteId(pmh.getComprobanteId());
        historicoMensajeHacienda.setFechaIngreso(new Date());
        historicoMensajeHacienda.setFechaUltimoRequest(new Date());

        historicoMensajeHacienda.setIntentos(pmh.getIntentos() + 1);

        historicoMensajeHacienda.setTipoComprobante(pmh.getTipoComprobante());

        if (r.getResultStr()!=null && r.getResultStr().length()>127) {
            historicoMensajeHacienda.setObservaciones(r.getResultStr().substring(0,127));

        } else {
            historicoMensajeHacienda.setObservaciones(r.getResultStr());
        }

        if (r.getEstado()!=null && r.getEstado().equals("aceptado")) {
            historicoMensajeHacienda.setEstado("FINALIZADO");
        } else {
            historicoMensajeHacienda.setEstado("ESPERA");

        }
        if (r.getEstado()!=null && !r.getEstado().equals("")) {

            historicoMensajeHacienda.setEstadoHacienda(r.getEstado());

        } else {
            historicoMensajeHacienda.setEstadoHacienda("procesando");
        }
        historicoMensajeHacienda.setBillSenderId(pmh.getBillSenderId());

        historicoMensajeHaciendaRepository.save(historicoMensajeHacienda);
//        pendienteMensajeHaciendaRepository.delete(pmh);
        this.pendienteMensajeHaciendaRepository.delete(pmh);

    }

    public ResultFe obtenerMensajeHacienda(BillSenderDetail bsd1, Integer id, String tipo, GeneralParameter nombreEmpresaParameter) throws Exception {
        if (bsd1.getClave()==null || bsd1.getClave().equals("")) {
            return null;
        }
        String basePath = bsd1.getPath().substring(billConfigService.getDirSystemPath().length());
        billConfigService.setBasePath(basePath);

        ResultFe result = this.billTaskService.getComprobanteResultAuthenticated(bsd1.getClave(),bsd1.getBillId());
        String statusActual = bsd1.getStatus();
        Integer validada = bsd1.getValidada();
        if (result!=null && result.getEstado()!=null && !result.getEstado().equals("")) {
            statusActual = result.getEstado();

        }
        if (result.getResult()==1 || result.getResult()==3 || result.getResult()==-99) {

            validada = 1;
        } else {
//            System.out.println("*** obtenerMensajeHacienda tipo:" +  tipo  + "  validada " + validada + " getResult " + result.getResult());

            return null;
        }
//        System.out.println("*** obtenerMensajeHacienda tipo:" +  tipo  + "  validada " + validada + " getResult " + result.getResult());

        if ((tipo.equals(BillHelper.TIPO_FACTURA_FE)) &&
                (result.getResult()==1 || result.getResult()==3)) {
            sendFENotificacionCliente(bsd1, id, result, statusActual, validada, nombreEmpresaParameter);
        } else if (
                tipo.equals(BillHelper.TIPO_FACTURA_FEE) &&  //EXPORTACION
                (result.getResult()==1 || result.getResult()==3)) {
            sendFEENotificacionCliente(bsd1, id, result, statusActual, validada, nombreEmpresaParameter);
        } else if (tipo.equals(BillHelper.TIPO_NOTA_CREDITO_FE) && (result.getResult()==1 || result.getResult()==3)) {
            sendNCNotificacionCliente(bsd1, id, result, statusActual, validada, nombreEmpresaParameter);
        } else if ((tipo.equals(BillHelper.TIPO_CONFIRMACION_FE) || tipo.equals(BillHelper.TIPO_RECHAZO_FE)) && (result.getResult().intValue()==1 || result.getResult().intValue()==3)) {

            sendConfirmacionRechazoNotificacionTenant(bsd1, id, result, statusActual, validada, nombreEmpresaParameter);
        } else if (result.getResult()==-99 && tipo.equals(BillHelper.TIPO_FACTURA_FE)) {
                this.billTaskService.updateFactura(bsd1.getBillId(), 0, null, "no_enviada",
                        bsd1.getClave(), bsd1.getConsecutivo(), bsd1.getBillId(), false);


        } else if (result.getResult()==-99 && tipo.equals(BillHelper.TIPO_NOTA_CREDITO_FE)) {
            this.billTaskService.updateNotaCredito(bsd1.getBillId(),0, null, "no_enviada",
                bsd1.getClave(), bsd1.getConsecutivo(), bsd1.getBillId(), id, 1);


        } else if (result.getResult()==-99 && (tipo.equals(BillHelper.TIPO_CONFIRMACION_FE) || tipo.equals(BillHelper.TIPO_RECHAZO_FE))) {
            this.billTaskService.updateConfirmacionRechazo(bsd1.getBillId(), null, statusActual, bsd1.getId(), null);


        }

        return result;
    }

    private void sendFENotificacionCliente(BillSenderDetail bsd1, Integer id, ResultFe result, String statusActual, Integer validada, GeneralParameter nombreEmpresaParameter) throws Exception{
        String xmlFileName = bsd1.getPath()  + File.separator + bsd1.getClave() + ".xml";
       // FacturaDataDTO f =this.facturaRepository.findByIdToFacturaDTO(id);

        try {
            Optional<Invoice> ivo = this.invoiceRepository.findById(id);// Used to BUILD PDF
            Invoice iv = ivo.get();// Used to BUILD PDF

            FacturaElectronica fe = this.invoiceService.getFacturaElectronica(iv);
            String correos = fe.getCorreo();
            fe.setClave(bsd1.getClave());
            fe.setNumeroConsecutivo(bsd1.getConsecutivo());
            fe.setCorreo(correos);
            fe.setId(id);
            this.billTaskService.notificarClienteConFactura(fe, bsd1, statusActual, result.getResultStr(), validada, result, nombreEmpresaParameter);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void sendFEENotificacionCliente(BillSenderDetail bsd1, Integer id, ResultFe result, String statusActual, Integer validada, GeneralParameter nombreEmpresaParameter) {
        String xmlFileName = bsd1.getPath()  + File.separator + bsd1.getClave() + ".xml";
       // FacturaDataDTO f =this.facturaRepository.findByIdToFacturaDTO(id);

        try {
//            Invoice iv = this.invoiceRepository.get(id);// Used to BUILD PDF
            Optional<Invoice> ivo = this.invoiceRepository.findById(id);// Used to BUILD PDF
            Invoice iv = ivo.get();// Used to BUILD PDF
            FacturaElectronicaExportacion fe = this.invoiceService.getFacturaElectronicaExportacion(iv);
            String correos = fe.getCorreo();
            fe.setClave(bsd1.getClave());
            fe.setNumeroConsecutivo(bsd1.getConsecutivo());
            fe.setCorreo(correos);
            fe.setId(id);
            this.billTaskService.notificarClienteConFactura(fe, bsd1, statusActual, result.getResultStr(), validada, result, nombreEmpresaParameter);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void sendNCNotificacionCliente(BillSenderDetail bsd1, Integer id, ResultFe result, String statusActual, Integer validada, GeneralParameter nombreEmpresaParameter) {
        String xmlFileName = bsd1.getPath()  + File.separator + bsd1.getClave() + ".xml";
       // FacturaDataDTO f =this.facturaRepository.findByIdToFacturaDTO(id);

        try {
            Optional<InvoiceNotaCredito> ivno = this.notaCreditoRepository.findById(id);// Used to BUILD PDF
            InvoiceNotaCredito ivnc = ivno.get();
//            InvoiceNotaCredito ivnc = this.notaCreditoRepository.get(id);// Used to BUILD PDF
            NotaCreditoElectronica fe = invoiceService.getNotaCreditoElectronica(ivnc);
            String correos = fe.getCorreo();
            fe.setClave(bsd1.getClave());
            fe.setNumeroConsecutivo(bsd1.getConsecutivo());
            fe.setCorreo(correos);
            fe.setId(id);
            this.billTaskService.notificarClienteConNC(fe, bsd1, statusActual, result.getResultStr(), validada, result, nombreEmpresaParameter);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void sendConfirmacionRechazoNotificacionTenant(BillSenderDetail bsd1, Integer id, ResultFe result, String statusActual, Integer validada, GeneralParameter nombreEmpresaParameter) {
        String xmlFileName = bsd1.getPath()  + File.separator + bsd1.getClave() + ".xml";
       // FacturaDataDTO f =this.facturaRepository.findByIdToFacturaDTO(id);

        try {
            MensajeReceptor mr = this.facturaElectronicaUnmarshaller.readMensajeReceptorXMLFile(xmlFileName);
            mr.setId(id);
            String correos = mr.getCorreo();
            this.billTaskService.notificarConfirmacionRechazo(mr, bsd1, statusActual, result.getResultStr(), validada, result, mr.getTipoDocumento(), 1, nombreEmpresaParameter);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public BillTaskService getBillTaskService() {
        return billTaskService;
    }

    public void setBillTaskService(BillTaskService billTaskService) {
        this.billTaskService = billTaskService;
    }

}
