package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.Refundable;

public class RechazoEntradaDTO {

    private Integer id;
    private String type;
    private Refundable refundable;
    private BillPay billPay;
    private boolean result;

    public RechazoEntradaDTO() {
        this.result = false;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Refundable getRefundable() {
        return refundable;
    }

    public void setRefundable(Refundable refundable) {
        this.refundable = refundable;
    }

    public BillPay getBillPay() {
        return billPay;
    }

    public void setBillPay(BillPay billPay) {
        this.billPay = billPay;
    }
}
