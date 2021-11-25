package com.ndl.erp.dto;


public class Result {

    public static enum RESULT_CODE {
        CREATE,UPDATE,DELETE,LIST,TEST,ERROR, INFO, GENERATE, OK;
    }


    private RESULT_CODE code;
    private String mesage;
    private Object result;
    private String stackTrace;
    private String estado;

    private String resultStr;
//    private Integer result = -1;

    private String location;
    private String mensaje;

    public Result(RESULT_CODE code, String mesage) {
        this.code = code;
        this.mesage = mesage;
    }

    public Result(RESULT_CODE code, String mesage, Object result) {
        this.code = code;
        this.mesage = mesage;
        this.result = result;
    }

    public RESULT_CODE getCode() {
        return code;
    }

    public void setCode(RESULT_CODE code) {
        this.code = code;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    //    private String estado;


}
