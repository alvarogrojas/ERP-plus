package com.ndl.erp.fe.core.impl;

import com.ndl.erp.fe.core.BillConfigurationService;
import com.ndl.erp.fe.core.BillProcessEmiter;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.core.impl.tasks.*;
import com.ndl.erp.fe.core.impl.tasks.util.FeWrapper;
import com.ndl.erp.fe.core.impl.tasks.util.ResponderWrapper;

import com.ndl.erp.domain.ProcesoEmision;
import com.ndl.erp.repository.ProcesoEmisionRepository;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.mr.MensajeReceptor;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.fe.helpers.BillHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

//import javax.transaction.Transactional;

@Service
public class BillProcessEmiterImpl implements BillProcessEmiter {

    @Autowired
    private AuthenticationTask authenticationTask;

    @Autowired
    private SignerTask signerTask;

    @Autowired
    private SenderTask senderTask;

    @Autowired
    private ResponderTask responderTask;

    @Autowired
    private FeGeneratorConsecutivoClaveTask feGeneratorConsecutivoClaveTask;

    @Autowired
    private XMLMarshallerTask xmlMarshallerTask;

    @Autowired
    private ProcesoEmisionRepository procesoEmisionRepository;

    @Autowired
    private BillConfigurationService billConfigurationService;



    private Boolean currentState;
    private ProcesoEmision procesoEmision;
    private String accessToken;
    private String refreshToken;
    private String fileName;


    //@Async
    @Transactional
    @Override
    public void emitirFactura(FacturaElectronica fe) {

        //Inicializa proces de factura
        initializeProcess(fe);

        //Tarea de autenticacion
        ejecutarTareaAuthenticacion();

        //Generar la clave.
        ejecutarGenClaveConsecutivo(fe);

        ejecutarXLMMarshaller(fe);

        OptionSet os = this.billConfigurationService.initOptionsValues(this.fileName, BillHelper.FACTURA_NAMESPACE_V43, BillHelper.FACTURA_ELECTRONICA_BASE_XML, this.accessToken,this.procesoEmision.getId());

        ejecutarSigner(os);

        ejecutarSender(os);
    }



    private void initializeProcess(FacturaElectronica fe) {

        ProcesoEmision pe = new ProcesoEmision();
        pe.setTenantId(this.billConfigurationService.getTenantId());
        pe.setFechaEjecucion(new Date());
        pe.setResultado("INICIANDO");
        pe.setTarea("");
        pe.setTenantIdFactura(fe.getNumeroConsecutivoFe());
        pe.setTipo("FACTURA_ELECTRONICA");
//        this.procesoEmision = procesoEmisionRepository.addProcesoEmision(pe);
    }

    @Override
    public void emitirNotaCredito(NotaCreditoElectronica nce) {

    }

    @Override
    public void emitirConfirmacionRechazo(MensajeReceptor mensajeReceptor) {

    }

    private void ejecutarTareaAuthenticacion() {

        AuthResult result = authenticationTask.executeTask(this.procesoEmision.getId());
        this.currentState = result.getResult();
        updateProceso("Autenticacion");
        this.accessToken = result.getAccessToken();
        this.refreshToken = result.getRefreshToken();

    }

    private void ejecutarGenClaveConsecutivo(FacturaElectronica fe) {
        if (!this.currentState) {
            return;
        }
        FeWrapper few = new FeWrapper(fe, this.procesoEmision.getId());

        ResultBase result = feGeneratorConsecutivoClaveTask.executeTask(few);

        this.currentState = result.getResult();
        updateProceso("CONSECUTIVE_CLAVE_GEN");

    }

    private void ejecutarXLMMarshaller(FacturaElectronica fe) {
        if (!this.currentState) {
            return;
        }
        FeWrapper few = new FeWrapper(fe, this.procesoEmision.getId());

        FilenameResult result = xmlMarshallerTask.executeTask(few);

        this.currentState = result.getResult();
        updateProceso("XML_MARSHALER");
        this.fileName = result.getFileName();

    }

    private void ejecutarSigner(OptionSet os) {

        if (!this.currentState) {
            return;
        }

        ResultBase rb = this.signerTask.executeTask(os);

        this.currentState = rb.getResult();
        updateProceso(signerTask.getTaskCode());



    }

    private void ejecutarSender(OptionSet os) {

        if (!this.currentState) {
            return;
        }

        ResultBase rb = this.senderTask.executeTask(os);

        this.currentState = rb.getResult();
        updateProceso(signerTask.getTaskCode());

    }

    private void ejecutarResponder(String clave) {

        if (!this.currentState) {
            return;
        }
        ResponderWrapper rw = new ResponderWrapper(clave, accessToken, this.procesoEmision.getId());
        ResultBase rb = this.responderTask.executeTask(rw);

        this.currentState = rb.getResult();
        updateProceso(signerTask.getTaskCode());

    }

    private void updateProceso(String tarea) {
        if (currentState) {
            this.procesoEmision.setResultado("EXITOSO");
        } else {
            this.procesoEmision.setResultado("FALLO");

        }
        this.procesoEmision.setTarea(tarea);
        // this.procesoEmision = procesoEmisionRepository.updateProcesoEmision(this.procesoEmision);
    }


}
