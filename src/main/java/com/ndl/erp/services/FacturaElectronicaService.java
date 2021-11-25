package com.ndl.erp.services;

import com.ndl.erp.domain.BillSenderDetail;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.core.OptionSet;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import com.ndl.erp.fe.helpers.BillHelper;

import com.ndl.erp.fe.v43.te.TiqueteElectronico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaElectronicaService {


    @Autowired
    private BillTaskService billTaskService;

    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    private BillConfigService billConfigService;

    public ResultFe emitFacturaElectronica(FacturaElectronica facturaElectronica) {

//        return this.billTaskService.emitFactura(facturaElectronica);
        this.billTaskService.emitFactura(facturaElectronica);
        ResultFe rfe = new ResultFe();
        rfe.setResult(-1);
        rfe.setResultStr("Factura Electronica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
        rfe.setEstado(BillHelper.ENVIANDO);
        return rfe;
    }


    public ResultFe emitFacturaElectronica(FacturaElectronicaExportacion facturaElectronica) {

//        return this.billTaskService.emitFactura(facturaElectronica);
        this.billTaskService.emitFactura(facturaElectronica);
        ResultFe rfe = new ResultFe();
        rfe.setResult(-1);
        rfe.setResultStr("Factura Electronica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
        rfe.setEstado(BillHelper.ENVIANDO);
        return rfe;
    }

    public ResultFe emitFacturaTiqueteElectronico(TiqueteElectronico tiqueteElectronico) {

//        return this.billTaskService.emitFactura(facturaElectronica);
        this.billTaskService.emitTiquete(tiqueteElectronico);
        ResultFe rfe = new ResultFe();
        rfe.setResult(-1);
        rfe.setResultStr("Tiquete Electronico se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
        rfe.setEstado(BillHelper.ENVIANDO);
        return rfe;
    }

    public ResultFe emitNotaCreditoElectronica(NotaCreditoElectronica fe, Integer ncId, String correoDestino, String receptor) {
//        return this.billTaskService.emitNotaCredito(fe, ncId, correoDestino, fe.getEmisor().getNombre(), receptor);
        this.billTaskService.emitNotaCredito(fe, ncId, correoDestino, fe.getEmisor().getNombre(), receptor);
        ResultFe rfe = new ResultFe();
        rfe.setResult(-1);
        rfe.setResultStr("Nota de Crédito Electrónica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
        rfe.setEstado(BillHelper.ENVIANDO);
        return rfe;

    }

    public BillTaskService getBillTaskService() {
        return billTaskService;
    }

    public void setBillTaskService(BillTaskService billTaskService) {
        this.billTaskService = billTaskService;
    }

    public BillSenderDetail createProcesoEmisionFacturaDetalle(Integer id, String tipo) {
        return this.billTaskService.createProcesoEmisionFacturaDetalle(id, tipo);
    }

    public boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd) {
        if (!billEmitter.sign(options, facturaNamespaceV42)) {
            this.billTaskService.agregarError("FIRMA", billEmitter.getMessageError(), id);
            this.billTaskService.createProcesoEmisionDetalle(id, new java.util.Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo);

            return false;
        }
        return true;
    }



}
