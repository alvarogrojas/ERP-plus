package com.ndl.erp.fe.core.impl.tasks.util;

public class ResponderWrapper {

    private String clave;

    private String accessToken;

    private Integer processId;

    public ResponderWrapper(String clave, String accessToken, Integer processId) {
        this.clave = clave;
        this.accessToken = accessToken;
        this.processId = processId;
    }

    public ResponderWrapper() {

    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
}
