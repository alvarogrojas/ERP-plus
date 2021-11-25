package com.ndl.erp.fe.core.impl.tasks.util;

import com.ndl.erp.fe.v43.FacturaElectronica;

public class FeWrapper {

    private FacturaElectronica fe;

    private Integer processId;

    public FeWrapper(FacturaElectronica fe, Integer processId) {
        this.fe = fe;
        this.processId = processId;
    }

    public FeWrapper() {

    }

    public FacturaElectronica getFe() {
        return fe;
    }

    public void setFe(FacturaElectronica fe) {
        this.fe = fe;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
}
