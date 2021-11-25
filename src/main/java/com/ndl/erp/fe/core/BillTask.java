package com.ndl.erp.fe.core;


import com.ndl.erp.fe.core.impl.ResultBase;

import com.ndl.erp.domain.ErrorProcess;
import com.ndl.erp.domain.TareaEjecutada;
//import com.ndl.erp.repository.TareaEjecutadaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public abstract class BillTask<T, T1> {

//    protected static Logger logger = LogManager.getLogger(BillTask.class);

    @Autowired
    protected BillConfigurationService billConfigurationService;

    @Autowired
    protected BillErrorManager billErrorManager;

//    @Autowired
//    protected TareaEjecutadaRepository tareaEjecutadaRepository;

    protected Integer processId;

    protected BillErrorManager em;

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }


    public abstract T1 executeTask(T state);

    public abstract String getTaskCode();


//    public abstract void setBillErrorManager(BillErrorManager bem);

    public ErrorProcess manageError (String message) {
//        logger.error(" ERROR " + getTaskCode() + " " + message);
        ErrorProcess errorProcess = new ErrorProcess();
        errorProcess.setTenantId(billConfigurationService.getTenantId());
        errorProcess.setErrorDate(new Date());
        errorProcess.setMessage(message);
        errorProcess.setTareaCode(getTaskCode());
        errorProcess.setProcessId(getProcessId());
        billErrorManager.logError(errorProcess);
        return errorProcess;

    }

    public void saveTareaEjecutada(ResultBase resultBase) {
        //logger.info(" TAREA EJECUTADA " + getTaskCode() + " " + resultBase.getMessage());

        TareaEjecutada te = new TareaEjecutada();
        te.setFechaEjecucion(new Date());
        te.setNombre(this.getTaskCode());
        te.setObservacion(resultBase.getMessage());
        te.setProcesoEmisionId(getProcessId());
        // this.tareaEjecutadaRepository.addATareaEjecutada(te);
    }
}
