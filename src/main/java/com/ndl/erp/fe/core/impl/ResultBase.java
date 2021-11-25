package com.ndl.erp.fe.core.impl;

public class ResultBase {

    private Boolean result = true;

    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean resut) {
        this.result = resut;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
