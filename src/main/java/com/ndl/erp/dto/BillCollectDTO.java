package com.ndl.erp.dto;

import com.ndl.erp.domain.BillCollect;
import com.ndl.erp.util.StringHelper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wugalde on 4/25/17.
 */
public class BillCollectDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000019L;

    private Integer id;
    private String accion;
    private String status;
    private Boolean inClosing;
    private String billNumber;
    private String billDate;
    private String endDate;

    private String purchageOrder;
    private String interPurchageOrder;
    private String payDate;
    private String transactDate;
    private String proofPayment;
    private Integer creditDay;
    private Double tax;
    private Double total;
    private Double subTotal;
    private String description;

    private Integer clientId;
    private String clientName;

    private Integer currencyId;
    private String CurrenyName;
    private String currencySimbol;

    private BillCollect current;


    private String inThisClosure = "FALSE";

    private String statusClosing = StringHelper.STATUS_DATA_ING;

    public BillCollectDTO() {  }

    public static final BillCollect initBillCollect(){
        BillCollect ret =new BillCollect();
        ret.setId(0);
        ret.setStatus(StringHelper.STATUS_DATA_ACT);
        return ret;
    }


//    public BillCollect newBillCollect(){
//        BillCollect ret= new BillCollect();
//        ret.setStatus(this.getStatus());
//        ret.setBillNumber(this.getBillNumber());
//        ret.setTransactDate(StringHelper.getString2Date(this.getTransactDate()));
//        ret.setBillDate(StringHelper.getString2Date(this.getBillDate()));
//        ret.setEndDate(StringHelper.getString2Date(this.getEndDate()));
//        ret.setPurchageOrder(this.getPurchageOrder());
//        ret.setInternalPurchageOrder(this.getInterPurchageOrder());
//        ret.setProofPayment(this.getProofPayment());
//        ret.setCreditDay(this.getCreditDay());
//        ret.setPayDate(StringHelper.plusDays(ret.getBillDate(),this.getCreditDay()));
//        ret.setTax(this.getTax());
//        ret.setSubTotal(this.getSubTotal());
//        ret.setTotal(this.getTotal());
//        ret.setDescription(this.getDescription());
//        ret.setInClosing(this.getInClosing());
//        ret.setStatusClosing(this.getStatusClosing());
//        return ret;
//    }

    public void getUpdateBillCollect(BillCollect billCollect) {
        billCollect.setStatus(this.getStatus());
        billCollect.setBillNumber(this.getBillNumber());
        billCollect.setTransactDate(StringHelper.getString2Date(this.getTransactDate()));
        billCollect.setBillDate(StringHelper.getString2Date(this.getBillDate()));
        billCollect.setEndDate(StringHelper.getString2Date(this.getEndDate()));
        billCollect.setPurchageOrder(this.getPurchageOrder());
        billCollect.setInterPurchageOrder(this.getInterPurchageOrder());
        billCollect.setProofPayment(this.getProofPayment());
        billCollect.setCreditDay(this.getCreditDay());
        billCollect.setPayDate(StringHelper.plusDays(billCollect.getBillDate(),this.getCreditDay()));
        billCollect.setSubTotal(this.getSubTotal());
        billCollect.setTotal(this.getTotal());
        billCollect.setTax(this.getTax());
        billCollect.setDescription(this.getDescription());
        billCollect.setInClosing(this.getInClosing());
        //billCollect.setStatusClosing(this.getStatusClosing());
    }


    public BillCollectDTO(BillCollect bp){
        this.current = bp;
        this.setId(bp.getId());
        this.setStatus(bp.getStatus());
        this.setStatus(bp.getStatus());
        this.setBillNumber(bp.getBillNumber());
        this.setTransactDate(StringHelper.getDate2String(bp.getTransactDate()));
        this.setBillDate(StringHelper.getDate2String(bp.getBillDate()));
        this.setEndDate(StringHelper.getDate2String(bp.getEndDate()));
        this.setClientId(bp.getClient().getClientId());
        this.setClientName(bp.getClient().getName());
        this.setPurchageOrder(bp.getPurchageOrder());
        this.setInterPurchageOrder(bp.getInterPurchageOrder());
        this.setProofPayment(bp.getProofPayment());
        this.setPayDate(StringHelper.getDate2String(bp.getPayDate()));
        this.setCreditDay(bp.getCreditDay());
        this.setCurrencyId(bp.getCurrency().getId());
        this.setCurrenyName(bp.getCurrency().getName());
        this.setCurrencySimbol(bp.getCurrency().getSimbol());
        this.setSubTotal(bp.getSubTotal());
        this.setTax(bp.getTax());
        this.setTotal(bp.getTotal());
        this.setDescription(bp.getDescription());
        this.setInClosing(bp.getInClosing());
        this.setAccion("edit");
        this.setStatusClosing(bp.getStatusClosing());

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BillCollect addBillCollect(Integer userId) {
        BillCollect ret= new BillCollect();
        ret.setStatus(this.getStatus());
        ret.setUserId(userId);
        ret.setTotal(0d);
        ret.setSubTotal(0d);
        ret.setTax(0d);
        ret.setInClosing(false);
        return ret;
    }

    public void updateBillCollect(BillCollect dep) {
        dep.setStatus(this.getStatus());
    }


    public Boolean getInClosing() {
        return inClosing;
    }

    public void setInClosing(Boolean inClosing) {
        this.inClosing = inClosing;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



    public String getCollectDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getProofCollectment() {
        return proofPayment;
    }

    public void setProofPayment(String proofPayment) {
        this.proofPayment = proofPayment;
    }

    public Integer getCreditDay() {
        return creditDay;
    }

    public void setCreditDay(Integer creditDay) {
        this.creditDay = creditDay;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }



    public String getCurrenyName() {
        return CurrenyName;
    }

    public void setCurrenyName(String currenyName) {
        CurrenyName = currenyName;
    }

    public String getPurchageOrder() {
        return purchageOrder;
    }

    public void setPurchageOrder(String purchageOrder) {
        this.purchageOrder = purchageOrder;
    }

    public String getPayDate() {
        return payDate;
    }

    public String getProofPayment() {
        return proofPayment;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInterPurchageOrder() {
        return interPurchageOrder;
    }

    public void setInterPurchageOrder(String interPurchageOrder) {
        this.interPurchageOrder = interPurchageOrder;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrencySimbol() {
        return currencySimbol;
    }

    public void setCurrencySimbol(String currencySimbol) {
        this.currencySimbol = currencySimbol;
    }

    public String getTransactDate() {
        return transactDate;
    }

    public void setTransactDate(String transactDate) {
        this.transactDate = transactDate;
    }

    public String getInThisClosure() {
        return inThisClosure;
    }

    public void setInThisClosure(String inThisClosure) {
        this.inThisClosure = inThisClosure;
    }


    public String getStatusClosing() {
        return statusClosing;
    }

    public void setStatusClosing(String statusClosing) {
        this.statusClosing = statusClosing;
    }

    public BillCollect getCurrent() {
        return current;
    }

    public void setCurrent(BillCollect current) {
        this.current = current;
    }
}
