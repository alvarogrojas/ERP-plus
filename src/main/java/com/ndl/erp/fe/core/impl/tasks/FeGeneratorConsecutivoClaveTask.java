package com.ndl.erp.fe.core.impl.tasks;

import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;
import com.ndl.erp.fe.core.impl.GlobalManager;
import com.ndl.erp.fe.core.impl.ResultBase;
import com.ndl.erp.fe.core.impl.tasks.util.FeWrapper;

import com.ndl.erp.domain.ErrorProcess;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.helpers.BillHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component

public class FeGeneratorConsecutivoClaveTask extends BillTask<FeWrapper, ResultBase> {

    @Autowired
    private GlobalManager globalManager;


    public FeGeneratorConsecutivoClaveTask() {}

    public FeGeneratorConsecutivoClaveTask(BillErrorManager e) {
        this.em = e;
    }

    @Override
    public ResultBase executeTask(FeWrapper few) {
        FacturaElectronica fe = few.getFe();
        this.processId = few.getProcessId();
        ResultBase result = new ResultBase();

        try {

            //fe.setNumeroConsecutivoFe(globalManager.getFacturaElectronicaNext(this.billConfigurationService.getTenantId()));
            String consecutiveFe = fe.getNumeroConsecutivoFe().toString();
            fe.setNumeroConsecutivo(consecutiveFe);
            generarClave(fe);
            billConfigurationService.initBasePath(consecutiveFe);
        } catch (Exception e) {
            ErrorProcess errorProcess = manageError(e.getMessage());
            result.setMessage(e.getMessage());
            result.setResult(false);

        } finally {
            this.saveTareaEjecutada(result);
        }
        return result;

    }

    @Override
    public String getTaskCode() {
        return "CONSECUTIVE_CLAVE_GEN";
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

    private String generarConsecutivoFactura(String consecutivoFactura, String tipoDocumento) {
        String consecutivoStr =  StringUtils.leftPad(consecutivoFactura, 10, "0");
        return  "001" + "00001" + tipoDocumento + consecutivoStr;
    }

    private String generarCedula(String cedula) {
        cedula = cedula.replaceAll("-","");

        StringBuilder stringBuilder = new StringBuilder(cedula);
        while (stringBuilder.length() < 12) {
            stringBuilder.insert(0, Integer.toString(0));
        }
        return stringBuilder.toString();
    }

    private String generarSeguridad() {
        Random rnd = new Random();
        Integer n = 10000000 + rnd.nextInt(90000000);
        return StringUtils.leftPad(n.toString(), 8, "0");
    }

    public void generarClave(FacturaElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(this.billConfigurationService.getPaisCode() + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + BillHelper.SITUATION_FE + nStr);

    }
}
