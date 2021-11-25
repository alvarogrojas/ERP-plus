package com.ndl.erp.dto;


import com.ndl.erp.domain.BillSenderDetail;

public class ResultFe {

    private String resultStr;
    private Integer result = -1;
    BillSenderDetail billSenderDetail;
    private String location;
    private String mensaje;



    private String estado;
    public Integer getResult() {
        return result;
    }
    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public BillSenderDetail getBillSenderDetail() {
        return billSenderDetail;
    }

    public void setBillSenderDetail(BillSenderDetail billSenderDetail) {
        this.billSenderDetail = billSenderDetail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
