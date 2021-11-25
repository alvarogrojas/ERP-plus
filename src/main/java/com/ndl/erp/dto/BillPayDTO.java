package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.domain.ExchangeRate;
import com.ndl.erp.util.StringHelper;

import java.util.ArrayList;
import java.util.List;
//import org.springframework.data.domain.Page;


public class BillPayDTO {


    private List<String> estados;
    private List<Provider> providers;
    private List<String> types;
    private List<Currency> currency;
    private List<GeneralParameter> taxes;
    private List<CostCenter> costCenters;
    private List<Bodega> bodegas;

    private BillPay current;

    private Integer id;
    private String accion;
    private String status;
    private Boolean inClosing;
    private String billNumber;
    private String billDate;
    private String endDate;
    private Integer providerId;
    private String providerName;
    private String purchaseOrder;
    private String payDate;
    private String proofPayment;
    private Integer creditDay;
    private Integer currencyId;
    private String CurrenyName;
    private String currencySimbol;
    private Double tax;
    private Double subTotal;
    private Double total;
    private CostCenter defaultCostCenter;


    private String type;
    private Integer typeId;
    private String collaboratorName;

    private String inThisClosure="FALSE";
    private String statusClosing = StringHelper.STATUS_DATA_ING;

    private List<ServiceCabys> serviceCabys = new ArrayList<>();
    private List<InventarioItem> items = new ArrayList<>();
    private List<ExchangeRate> exchangeRates = new ArrayList<>();
    private Integer ingresoAutomatico = 0;
    private boolean isLiteDetail = false;
    private List<TaxesIva> taxesList;
    private TaxesIva defaultIva;
    private Integer billNumberAutomatic = 0;


    public BillPayDTO() {  }

    public  BillPayDTO(BillPay bp){
        this.current = bp;
        this.setId(bp.getId());
        this.setStatus(bp.getStatus());
        this.setStatus(bp.getStatus());
        this.setBillNumber(bp.getBillNumber());
        this.setBillDate(StringHelper.getDate2String(bp.getBillDate()));
        this.setEndDate(StringHelper.getDate2String(bp.getEndDate()));
        this.setPurchaseOrder(bp.getPurchaseOrder());
        this.setProofPayment(bp.getProofPayment());
        this.setPayDate(StringHelper.getDate2String(bp.getPayDate()));
        this.setCreditDay(bp.getCreditDay());
        this.setCurrencyId(bp.getCurrency().getId());
        this.setCurrenyName(bp.getCurrency().getName());
        this.setCurrencySimbol(bp.getCurrency().getSimbol());
        this.setAccion("edit");
        this.setType(bp.getType());
        if(!bp.getType().equals("CXP")){
            this.setProviderName(bp.getProviderName());//bp.getCollaborator().getName() + " " + bp.getCollaborator().getLastName()
            this.setTypeId(bp.getTypeId());
        }else{
            this.setProviderId(bp.getProvider().getId());
            this.setProviderName(bp.getProviderName());//bp.getProvider().getName()
        }
        this.setTax(bp.getTax());
        this.setTotal(bp.getTotal());
        this.setSubTotal(bp.getSubTotal());
        this.setInClosing(bp.getInClosing());
        this.setStatusClosing(bp.getStatusClosing());
    }


    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public CostCenter getDefaultCostCenter() {
        return defaultCostCenter;
    }

    public void setDefaultCostCenter(CostCenter defaultCostCenter) {
        this.defaultCostCenter = defaultCostCenter;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public BillPay getCurrent() {
        return current;
    }

    public void setCurrent(BillPay current) {
        this.current = current;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<GeneralParameter> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<GeneralParameter> taxes) {
        this.taxes = taxes;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getProofPayment() {
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

    public String getCurrenyName() {
        return CurrenyName;
    }

    public void setCurrenyName(String currenyName) {
        CurrenyName = currenyName;
    }

    public String getCurrencySimbol() {
        return currencySimbol;
    }

    public void setCurrencySimbol(String currencySimbol) {
        this.currencySimbol = currencySimbol;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCollaboratorName() {
        return collaboratorName;
    }

    public void setCollaboratorName(String collaboratorName) {
        this.collaboratorName = collaboratorName;
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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<ServiceCabys> getServiceCabys() {
        return serviceCabys;
    }

    public void setServiceCabys(List<ServiceCabys> serviceCabys) {
        if (serviceCabys==null) {
            return;
        }
        this.serviceCabys = serviceCabys;
    }

//    public List<ProductCabys> getItems() {
//        return items;
//    }
//
//    public void setItems(List<ProductCabys> items) {
//        if (items==null) {
//            return;
//        }
//        this.items = items;
//    }

    public List<InventarioItem> getItems() {
        return items;
    }

    public void setItems(List<InventarioItem> items) {
        if (items==null) {
            return;
        }
        this.items = items;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public void setIngresoAutomatico(Integer intVal) {
        this.ingresoAutomatico = intVal;
    }

    public Integer getIngresoAutomatico() {
        return ingresoAutomatico;
    }

    public void setIsLiteDetail(Integer v) {
        if(v!=null && v==1){
            this.isLiteDetail = true;
        }  else {
            this.isLiteDetail = false;
        }
    }

    public boolean getIsLiteDetail() {
        return isLiteDetail;
    }


    public void setTaxesList(List<TaxesIva> taxes) {
        this.taxesList = taxes;
    }

    public List<TaxesIva> getTaxesList() {
        return taxesList;
    }

    public void setDefaultIva(TaxesIva defaultIva) {

        this.defaultIva = defaultIva;
    }

    public TaxesIva getDefaultIva() {
        return defaultIva;
    }

    public void setBillNumberAutomatic(Integer v) {
        this.billNumberAutomatic = v;
    }

    public Integer getBillNumberAutomatic() {
        return billNumberAutomatic;
    }
}
