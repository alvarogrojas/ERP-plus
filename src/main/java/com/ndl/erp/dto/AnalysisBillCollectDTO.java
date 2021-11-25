package com.ndl.erp.dto;

import com.ndl.erp.domain.BillCollect;
import com.ndl.erp.domain.BillCollectDetail;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;

/**
 * Created by wugalde on 4/25/17.
 */
public class AnalysisBillCollectDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000032L;

    private Integer idBillCollect;
    private Integer idBillCollectDetail;
    private Integer idCostCenter;
    private String codCostCenter;
    private String nameCostCenter;
    private String billNumber; //# de la factura
    private String type;
    private Integer clientId;
    private String nameClient;
    private String description;
    private String orderBuy;
    private String bcdStatus;
    private String bcStatus;
    private Double subTotal;
    private Double tax;
    private Double total;
    private String billDateDelivery; //Fecha de entrega de la factura.

    private Currency currency;



    public AnalysisBillCollectDTO() {

    }

    public AnalysisBillCollectDTO(BillCollect bc, BillCollectDetail bcd, Currency curDef) {
        this.idBillCollectDetail = bcd.getId();
        this.idBillCollect = bcd.getId();
        this.idCostCenter = bcd.getCostCenter().getId();
        this.codCostCenter = bcd.getCostCenter().getCode();
        this.nameCostCenter = bcd.getCostCenter().getName();
        this.billNumber = bc.getBillNumber();
        this.type = bcd.getType();
        this.clientId = bc.getClient().getClientId();
        this.nameClient = bc.getClient().getName();
        this.description = bcd.getDetail();
        this.orderBuy = bc.getPurchageOrder();
        this.billDateDelivery = StringHelper.getDate2String(bc.getTransactDate());
        this.bcdStatus = bcd.getStatus();
        this.bcStatus = bc.getStatus();
        this.tax =  bcd.getTax();
        this.subTotal = bcd.getSubTotal() ;
        this.total = bcd.getTotal() ;
        if(!bc.getCurrency().getDefault()) {
            this.tax =  (bcd.getTax() * curDef.getValueBuy()) / bc.getCurrency().getValueBuy();
            this.subTotal = (bcd.getSubTotal() * curDef.getValueBuy()) / bc.getCurrency().getValueBuy();;
            this.total = (bcd.getTotal() * curDef.getValueBuy()) / bc.getCurrency().getValueBuy();
            this.currency = curDef;
        }else{
            this.currency = bc.getCurrency();
        }


    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getIdBillCollect() {
        return idBillCollect;
    }

    public void setIdBillCollect(Integer idBillCollect) {
        this.idBillCollect = idBillCollect;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderBuy() {
        return orderBuy;
    }

    public void setOrderBuy(String orderBuy) {
        this.orderBuy = orderBuy;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getBillDateDelivery() {
        return billDateDelivery;
    }

    public void setBillDateDelivery(String billDateDelivery) {
        this.billDateDelivery = billDateDelivery;
    }

    public Integer getIdBillCollectDetail() {
        return idBillCollectDetail;
    }

    public void setIdBillCollectDetail(Integer idBillCollectDetail) {
        this.idBillCollectDetail = idBillCollectDetail;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public String getNameCostCenter() {
        return nameCostCenter;
    }

    public void setNameCostCenter(String nameCostCenter) {
        this.nameCostCenter = nameCostCenter;
    }

    public String getCodCostCenter() {
        return codCostCenter;
    }

    public void setCodCostCenter(String codCostCenter) {
        this.codCostCenter = codCostCenter;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getBcdStatus() {
        return bcdStatus;
    }

    public void setBcdStatus(String bcdStatus) {
        this.bcdStatus = bcdStatus;
    }

    public String getBcStatus() {
        return bcStatus;
    }

    public void setBcStatus(String bcStatus) {
        this.bcStatus = bcStatus;
    }
}
