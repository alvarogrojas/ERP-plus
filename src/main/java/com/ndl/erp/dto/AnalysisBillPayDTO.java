package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.util.StringHelper;


import java.io.Serializable;


public class AnalysisBillPayDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000030L;

    private Integer idBillPay;
    private Integer idCostCenter;
    private String codCostCenter;
    private String nameCostCenter;
    private StringBuffer description= new StringBuffer();
    private Double subTotal;
    private Double tax;
    private Double total;
    private Integer idProvider;
    private String nameProvider;
    private String noBillProvider;
    private String billDate;
    private String type;



    private Currency currency;


    private String active="0";



    public AnalysisBillPayDTO() {

    }

    public AnalysisBillPayDTO(BillPay bp, BillPayDetail bpd, Currency rowCurrency, Currency systemCurrency) {

        this.setBillPayData(bp);
        this.setCostCenterData(bpd.getCostCenter());

        this.description.append(bpd.getDetail());
        this.subTotal = bpd.getSubTotal();
        this.tax = bpd.getTax();
        this.total = bpd.getTotal();

        //if(!rowCurrency.getIsDefault()){
        if(!systemCurrency.getId().equals(rowCurrency.getId())){
            this.subTotal = (this.subTotal  * systemCurrency.getValueBuy())  / rowCurrency.getValueBuy();
            this.tax =      (this.tax       * systemCurrency.getValueBuy())  / rowCurrency.getValueBuy();
            this.total =    (this.total     * systemCurrency.getValueBuy())  / rowCurrency.getValueBuy();
            this.currency =  systemCurrency;
        }else{
            this.currency =  rowCurrency;
        }
    }

    public AnalysisBillPayDTO(BillPay bp, RefundableDetail rd, Currency rowCurrency, Currency systemCurrency) {

        this.setCostCenterData(rd.getCostCenter());
        this.setBillPayData(bp);

        this.description.append(rd.getDescription());
        this.subTotal = rd.getMount();
        this.tax = rd.getTax();
        this.total = rd.getSubTotal();//+ this.tax;//getTotal();


        //if(!bp.getCurrency().getIsDefault()){
        if(!systemCurrency.getId().equals(rowCurrency.getId())){
            this.subTotal   = (this.subTotal    * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();
            this.tax        = (this.tax         * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();
            this.total      = (this.total       * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();
            this.currency   =  systemCurrency;
        }else{
            this.currency =  rowCurrency;
        }
    }

    public AnalysisBillPayDTO(BillPay bp, KilometerDetail kmd, Currency rowCurrency,Currency systemCurrency) {
        this.idBillPay = bp.getId();

        this.setCostCenterData(kmd.getCostCenter());
        this.setBillPayData(bp);

        this.description.append(kmd.getDescription());

        this.subTotal = kmd.getSubTotal();
        this.tax = 0d;
        this.total = kmd.getSubTotal();//bp.getTotal();

        if(!systemCurrency.getId().equals(rowCurrency.getId())){
            this.subTotal   = (this.subTotal    * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();;
            this.tax        = (this.tax         * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();
            this.total      = (this.total       * systemCurrency.getValueBuy()) / rowCurrency.getValueBuy();
            this.currency   =  systemCurrency;
        }else{
            this.currency =  rowCurrency;
        }
    }


    private void setCostCenterData(CostCenter cc){
        this.idCostCenter = cc.getId();
        this.codCostCenter = cc.getCode();
        this.nameCostCenter = cc.getName();
    }

    private void setBillPayData(BillPay bp){
        this.idBillPay = bp.getId();
        if(StringHelper.CXP.equals(bp.getType())){
            this.idProvider = bp.getProvider().getId();
            this.nameProvider = bp.getProvider().getName();
        }else{
            this.idProvider = bp.getCollaborator().getId();
            this.nameProvider = bp.getCollaborator().getName();
        }
        this.noBillProvider = bp.getBillNumber();
        this.billDate = StringHelper.getDate2String(bp.getBillDate());
        this.type = bp.getType();
    }

    public void addDescription(String desc){
        this.description.append( " \n ").append(desc);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalysisBillPayDTO that = (AnalysisBillPayDTO) o;

        if (!idBillPay.equals(that.idBillPay)) return false;
        if (!idCostCenter.equals(that.idCostCenter)) return false;
        return idProvider.equals(that.idProvider);
    }

    @Override
    public int hashCode() {
        int result = idBillPay.hashCode();
        result = 31 * result + idCostCenter.hashCode();
        result = 31 * result + idProvider.hashCode();
        return result;
    }


    public Integer getIdBillPay() {
        return idBillPay;
    }

    public void setIdBillPay(Integer idBillPay) {
        this.idBillPay = idBillPay;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public String getCodCostCenter() {
        return codCostCenter;
    }

    public void setCodCostCenter(String codCostCenter) {
        this.codCostCenter = codCostCenter;
    }

    public String getNameCostCenter() {
        return nameCostCenter;
    }

    public void setNameCostCenter(String nameCostCenter) {
        this.nameCostCenter = nameCostCenter;
    }

    public String getDescription() {
        return description.toString();
    }

    public void setDescription(StringBuffer description) {
        this.description = description;
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

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public String getNoBillProvider() {
        return noBillProvider;
    }

    public void setNoBillProvider(String noBillProvider) {
        this.noBillProvider = noBillProvider;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
