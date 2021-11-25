package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.MonthlyClosure;
import com.ndl.erp.domain.PayRoll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalysisDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000033L;

    private Currency systemCurrency;

    private MonthlyClosure monthlyClosure;

    private List<AnalysisCostCenterDTO> costCenter = new ArrayList<>(); //Centros de costos o proyectos.

    private  List<PayRollDTO> payRollDTOS;

    //private List<AnalysisCostCenterDTO> costCenterBillCollect = new ArrayList<>(); //Centros de costos o proyectos.

    private List<AnalysisBillCollectDTO> billCollects; //cuentas por cobrar
    private List<AnalysisBillPayDTO> billPays; //cuentas por cobrar

    private Map<String,AnalysisBillPayDTO> bpMap;
    private Map<String,AnalysisCostCenterDTO> ccMap;

    Double crudSalaryTotal = 0d;
    Double devolucionesTotal  = 0d;
    Double ccssTotal  = 0d;
    Double netSalaryTotal  = 0d;

    Double subTotalBillCollect = 0d;
    Double taxTotalBillCollect  = 0d;
    Double totalBillCollect  = 0d;
    Double totalBillPay = 0d;
    Double totalCC= 0d;


    public AnalysisDTO(MonthlyClosure monthlyClosure) {
        this.monthlyClosure = monthlyClosure;
        this.billCollects =  new ArrayList();
        this.billPays = new ArrayList();
    }

    public MonthlyClosure getMonthlyClosure() {
        return monthlyClosure;
    }

    public void setMonthlyClosure(MonthlyClosure monthlyClosure) {
        this.monthlyClosure = monthlyClosure;
    }

    public List<AnalysisCostCenterDTO> getCostCenter() {
        if(this.ccMap!=null && !this.ccMap.isEmpty())
            costCenter =  new ArrayList(this.ccMap.values());
        return costCenter;
    }

    public void setCostCenter(List<AnalysisCostCenterDTO> costCenter) {
        this.costCenter = costCenter;
    }

    public List<AnalysisBillCollectDTO> getBillCollects() {
        return billCollects;
    }

    public void setBillCollects(List<AnalysisBillCollectDTO> billCollects) {
        this.billCollects = billCollects;
    }


    public List<AnalysisBillPayDTO> getBillPays() {
        if(this.bpMap!=null && !this.bpMap.isEmpty())
            billPays = new ArrayList(this.bpMap.values());
        return billPays;
    }

    public void setBillPays(List<AnalysisBillPayDTO> billPays) {
        this.billPays = billPays;
    }

    public void addBillCollect(AnalysisBillCollectDTO bc){
        if(this.billCollects==null){
            this.billCollects = new ArrayList<AnalysisBillCollectDTO>();
        }
        this.billCollects.add(bc);
        this.subTotalBillCollect = this.subTotalBillCollect + bc.getSubTotal();
        this.taxTotalBillCollect = this.taxTotalBillCollect + bc.getTax();
        this.totalBillCollect = this.totalBillCollect + bc.getTotal();

    }


    private AnalysisBillPayDTO findBillPay(AnalysisBillPayDTO bp){
        for(AnalysisBillPayDTO abp: this.getBillPays()){
            if(abp.equals(bp)){
                return abp;
            }
        }
        return null;
    }

    int indexBP = 1;
    public void addBillPay(AnalysisBillPayDTO bp){

        if(this.bpMap==null){
            this.bpMap = new HashMap<String,AnalysisBillPayDTO>();
        }
        String key= bp.getIdBillPay() + "-" + bp.getIdCostCenter() + "-" + bp.getIdProvider();
        AnalysisBillPayDTO find = null;
        if(this.bpMap.containsKey(key)){
            find = this.bpMap.get(key);
            find.addDescription(bp.getDescription());
            find.setTotal(find.getTotal() + bp.getTotal());
            find.setSubTotal(find.getSubTotal() + bp.getSubTotal());
            find.setTax(find.getTax() + bp.getTax());
            this.bpMap.put(key,find);
            //this.infoCC("Cuentas por pagar "+ indexBP++ +" ::: ",bp.getCodCostCenter(),bp.getTotal(),find.getTotal());
        }else{
            this.bpMap.put(key,bp);
            //this.infoCC("Cuentas por pagar "+ indexBP++ +" ::: ",bp.getCodCostCenter(),bp.getTotal(),0);
        }


    }

    int index = 1;
    public void addCostCenter(AnalysisCostCenterDTO cc){
        if(this.ccMap==null){
            this.ccMap = new HashMap<String,AnalysisCostCenterDTO>();
        }

        String key =cc.getId().toString();
        if(this.ccMap.containsKey(key)){
            AnalysisCostCenterDTO find = this.ccMap.get(key);
            find.setTotal(find.getTotal() + cc.getTotal());
            this.ccMap.put(key,find);
            //this.infoCC("Centro de costo " + index++ +":::",cc.getCode(),cc.getTotal(),find.getTotal());
        }else{
            this.ccMap.put(key,cc);
            //this.infoCC("Centro de costo #" +  index++ + " ::: ", cc.getCode(),cc.getTotal(),0);
        }
    }

    public List<PayRollDTO> getPayRollDTOS() {
        return payRollDTOS;
    }

    public void setPayRollDTOS(List<PayRollDTO> payRollDTOS) {
        this.payRollDTOS = payRollDTOS;
    }

    public Double getCrudSalaryTotal() {
        return crudSalaryTotal;
    }

    public void setCrudSalaryTotal(Double crudSalaryTotal) {
        this.crudSalaryTotal = crudSalaryTotal;
    }

    public Double getDevolucionesTotal() {
        return devolucionesTotal;
    }

    public void setDevolucionesTotal(Double devolucionesTotal) {
        this.devolucionesTotal = devolucionesTotal;
    }

    public Double getCcssTotal() {
        return ccssTotal;
    }

    public void setCcssTotal(Double ccssTotal) {
        this.ccssTotal = ccssTotal;
    }

    public Double getNetSalaryTotal() {
        return netSalaryTotal;
    }

    public void setNetSalaryTotal(Double netSalaryTotal) {
        this.netSalaryTotal = netSalaryTotal;
    }

    public Double getSubTotalBillCollect() {
        return subTotalBillCollect;
    }

    public void setSubTotalBillCollect(Double subTotalBillCollect) {
        this.subTotalBillCollect = subTotalBillCollect;
    }

    public Double getTaxTotalBillCollect() {
        return taxTotalBillCollect;
    }

    public void setTaxTotalBillCollect(Double taxTotalBillCollect) {
        this.taxTotalBillCollect = taxTotalBillCollect;
    }

    public Double getTotalBillCollect() {
        return totalBillCollect;
    }

    public void setTotalBillCollect(Double totalBillCollect) {
        this.totalBillCollect = totalBillCollect;
    }

    public Double getTotalBillPay() {
        return totalBillPay;
    }

    public void setTotalBillPay(Double totalBillPay) {
        this.totalBillPay = totalBillPay;
    }

    public Double getTotalCC() {
        return totalCC;
    }

    public void setTotalCC(Double totalCC) {
        this.totalCC = totalCC;
    }

    public Currency getSystemCurrency() {
        return systemCurrency;
    }

    public void setSystemCurrency(Currency systemCurrency) {
        this.systemCurrency = systemCurrency;
    }
}
