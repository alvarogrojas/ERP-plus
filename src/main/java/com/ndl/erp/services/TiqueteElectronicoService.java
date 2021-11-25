package com.ndl.erp.services;

import com.ndl.erp.domain.BillSenderDetail;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.TransaccionPendiente;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.mappers.InvoiceTiqueteMapper;
import com.ndl.erp.fe.v43.te.TiqueteElectronico;
import com.ndl.erp.repository.TransaccionPendienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

import static com.ndl.erp.constants.InvoiceConstants.INVOICE_TIPO_TRANSACCION_FE;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_TIPO_TRANSACCION_TIQUETE;



@Transactional
@Component
public class TiqueteElectronicoService {

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    FacturaElectronicaService facturaElectronicaService;

    @Autowired
    BillConfigService billConfigService;

    @Autowired
    InvoiceService invoiceService;


    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    TransaccionPendienteRepository transaccionPendienteRepository;


    /************************************************
     Envio de un tiquete electrónico a hacienda
     Felix Saborio : 14/09/2021
     ************************************************/
    @Transactional(rollbackFor = {Exception.class})
    public ResultFe sendTiqueteElectronicoToHacienda(Invoice iv, TransaccionPendiente t) throws Exception{

        ResultFe rfe = new ResultFe();
        String claveActual = iv.getClave();
        OptionSet options;

        boolean ret = false;
        try {

            GeneralParameter gp = this.generalParameterService.get(4);
            List<GeneralParameter> emisor = this.generalParameterService.getByCodes("INGP_INF");
            InvoiceTiqueteMapper ivm = new InvoiceTiqueteMapper(emisor, gp);

            TiqueteElectronico te = ivm.mapFacturaElectronica(iv);
            te.setNumeroConsecutivoFe(Integer.parseInt(iv.getConsecutivo()));
            te.setNumeroConsecutivo(iv.getConsecutivo());

            iv.setEstadoHacienda(BillHelper.ENVIANDO);

            BillSenderDetail pefd = this.facturaElectronicaService.createProcesoEmisionFacturaDetalle(iv.getId(), BillHelper.TIPO_TIQUETE_E);
            billConfigService.initBasePath(te.getNumeroConsecutivo());



            String fileName = this.invoiceService.getXmlFileName(te.getClave());


            this.fem.writeXMLFile(te, billConfigService.getCompletePath() + fileName);

            options = billConfigService.initOptionsValues(fileName);

            pefd.setClave(te.getClave());
            pefd.setStatus(BillHelper.NO_ENVIADA);
            pefd.setConsecutivo(te.getNumeroConsecutivo());
            pefd.setPath(billConfigService.getCompletePath());
            if (!this.facturaElectronicaService.firmarFactura(options, BillHelper.TIQUETE_NAMESPACE_V43, te.getId(), BillHelper.TIPO_TIQUETE_E, pefd)) {
                rfe.setResultStr("Hubo un error al firmar la factura");
                rfe.setResult(-1);
                return rfe;
            }

            rfe = facturaElectronicaService.emitFacturaTiqueteElectronico(te);

            this.invoiceService.update(iv);


            ret = rfe.getResult() == 1;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        } catch (Exception e) {
            e.printStackTrace();
            rfe.setResult(0);
            if (e.getMessage() != null && !e.getMessage().equals(""))
                rfe.setResultStr(e.getMessage());
            else {
                rfe.setResultStr("Error Durante el envio");

            }
        }
        iv.setHacienda(ret);
        return rfe;
    }


    //@Scheduled(cron = "1 */3 * * *")  //Al primer minuto transcurridas 3 horas
    //@Scheduled(cron = "1 * * * *")  //Cada minuto (Para testing)
    @Transactional(rollbackFor = {Exception.class})
    public void enviarDocumentosPendientesHacienda() throws Exception {
        ResultFe r;
        List <TransaccionPendiente> transaccionPendientes = this.transaccionPendienteRepository.getAllTransaccionPendienteByEstado();

        for(TransaccionPendiente t : transaccionPendientes) {
            //TODO : Debemos cambiar el estado de la transaccion de acuerdo al resultodo del envio a hacienda
            if (t.getTipo().equals(INVOICE_TIPO_TRANSACCION_TIQUETE)) {
                r = sendTiqueteElectronicoToHacienda(t.getInvoice(), t);
                //this.transaccionPendienteRepository.save(t);
            } else if(t.getTipo().equals(INVOICE_TIPO_TRANSACCION_FE)) {
                r = this.invoiceService.sendToHacienda(t.getInvoice().getId(), false);
                //this.transaccionPendienteRepository.save(t);
            }
        }

    }

}
