package com.ndl.erp.fe.core.impl.tasks;

import com.ndl.erp.fe.core.BillErrorManager;
import com.ndl.erp.fe.core.BillTask;
import com.ndl.erp.fe.core.impl.BillMarshaller;
import com.ndl.erp.fe.core.impl.GlobalManager;
import com.ndl.erp.fe.core.impl.tasks.util.FeWrapper;

import com.ndl.erp.domain.ErrorProcess;
import com.ndl.erp.fe.v43.FacturaElectronica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XMLMarshallerTask extends BillTask<FeWrapper, FilenameResult> {

    @Autowired
    private BillMarshaller fem;

    XMLMarshallerTask() {
    }

    XMLMarshallerTask(BillErrorManager bm) {

    }

    @Autowired
    private GlobalManager globalManager;


    @Override
    public FilenameResult executeTask(FeWrapper few) {
        this.processId = few.getProcessId();
        FacturaElectronica fe = few.getFe();
        FilenameResult result = new FilenameResult();
        try {
            String fileName = getXmlFileName(fe.getClave());
            this.fem.writeXMLFile(fe, billConfigurationService.getCompletePath() + fileName);
            result.setFileName(fileName);
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
        return "XML_MARSHALER";
    }


    public String getXmlFileName(String clave) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = billConfigurationService.getCompletePath() + fileName;
        return fileName;
    }
}
