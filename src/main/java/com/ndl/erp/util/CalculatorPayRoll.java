package com.ndl.erp.util;

import com.ndl.erp.domain.*;


import java.util.ArrayList;
import java.util.List;


public class CalculatorPayRoll {

    private static final Double HM = 1.5d;
    private static final Double HD = 2d;
    private static final int DAYS_BY_MOUNT = 30;
    private static final int DAYS_BY_BIWEEK = 14;

    private static final String COLLABORATOR_BY_HOUR = "HORA";
    public static final int PORCENT = 100;
    private static final Integer RENT_HOUR_208 = 208;
    private static final Integer RENT_HOUR_240 = 240;




    private PayRoll payRoll;

    private Collaborator collaborator;

    private Taxes renta1;
    private Taxes renta2;
    private Taxes renta3;
    private Taxes renta4;

    private Deductions ccssDeduction;
    private List<Deductions> othersDeduction;

    private List<RefundDevolution> devolutions;
    private List<RefundDevolution> refunds;
    private List<LaborCostDetail> laborCostDetails;


    private Double hs=0d;
    private Double hsTotal=0d;

    private Double hm=0d;
    private Double hmTotal=0d;

    private Double hd=0d;
    private Double hdTotal=0d;




    private Double tax1=0d;
    private Double tax2=0d;
    private Double tax3=0d;
    private Double tax4=0d;
    private Double ccss=0d;
    private Double refund=0d;
    private Double devolution=0d;
    private Double mtoOthersDeductions=0d;


    private Double netSalary=0d;

    private Double bruteSalary=0d;

    public CalculatorPayRoll() {
        this.setHs(0d);
        this.setHm(0d);
        this.setHd(0d);
    }


    public void addHs(Double hs) {
        if(this.getCollaborator().getTypePayroll().equals(COLLABORATOR_BY_HOUR))
            this.setHs(this.getHs() + hs);
        else
            this.setHs(this.getCollaborator().getCcssHoursWork());
            //this.setHs((double)CCSS_COLLABORATOR_BY_MONTH);
        this.setHsTotal(this.getHs() * collaborator.getRate());
        this.calcBruteSalry();
    }

    public void addHm(Double hm) {
        this.setHm(this.getHm() + hm);
        this.setHmTotal(this.getHm() * HM * collaborator.getRate());
        this.calcBruteSalry();
    }

    public void addHd(Double hd) {
        this.setHd(this.getHd() + hd);
        this.setHdTotal(this.getHd() * HD * collaborator.getRate());
        this.calcBruteSalry();
    }

    private void addOtherDeduction(Double od){
        this.setMtoOthersDeductions(this.getMtoOthersDeductions() + od);
    }

    private void addRefund(Double r){
        this.setRefund(this.getRefund() + r);
    }

    private void minusDevolution(Double r){
        this.setDevolution(this.getDevolution() + r);
    }



    public void calcNetSalry() {

        //1. Calcular las rentas.
        this.calcTaxOne();
        this.calcTaxTwo();
        this.calcTaxThree();
        this.calcTaxFourth();

        //2. Calcular la ccss
        this.calcCCSS();

        //3. Cacular reintegros
        this.calcRefunds();

        //4. Calcular devoliciones
        this.calcDevolutions();

        //5. Otras deducciones
        this.calcOthersDecuctions();

        Double netSalaryTemp =  this.getBruteSalary();
        netSalaryTemp += this.getRefund();
        netSalaryTemp -= this.getDevolution();
        netSalaryTemp -= this.getTax1();
        netSalaryTemp -= this.getTax2();
        netSalaryTemp -= this.getTax3();
        netSalaryTemp -= this.getCcss();
        netSalaryTemp -= this.getMtoOthersDeductions();

        this.setNetSalary(netSalaryTemp);
    }



    /**
     * Calcuclo del 1er tracto de la renta
     */
    public void calcTaxOne(){
//        this.setTax1(0d);

        Double rangeStart     = this.getRenta1().getMountStart();
        Double rangeEnd       = this.getRenta1().getMountEnd();
        Double salaryHSTotal  = this.getSalaryRent() ; //this.getHsTotal();
        Double mtoCalc = 0d;
        Double taxCalc = 0d;
        if(salaryHSTotal > rangeStart && salaryHSTotal < rangeEnd){
            mtoCalc = salaryHSTotal - rangeStart;
        }else if(salaryHSTotal > rangeEnd){
            mtoCalc = rangeEnd - rangeStart;
        }
        taxCalc = ((mtoCalc * this.getRenta1().getPercent())  / DAYS_BY_MOUNT) * DAYS_BY_BIWEEK ;
        this.setTax1(taxCalc / PORCENT);
    }


    /**
     * Obtiene el salario para el caclulo de la renta
     * @return Salario para el calclo de retna
     */
    private Double getSalaryRent(){
        Integer hours = 0;
        if(this.getCollaborator().getTypePayroll().equals(StringHelper.HORA))
            hours = RENT_HOUR_208;
        else
            hours = RENT_HOUR_240;

        Double price = this.getCollaborator().getRate() * hours ;
        return price;
    }

    /**
     * CAlculo del 2do tracto de la renta
     */
    public void calcTaxTwo(){
        Double rangeStart     = this.getRenta2().getMountStart();
        Double rangeEnd       = this.getRenta2().getMountEnd();
        Double salaryHSTotal  = this.getSalaryRent() ; //this.getHsTotal();
        Double mtoCalc = 0d;
        Double taxCalc = 0d;
        if(salaryHSTotal > rangeStart && salaryHSTotal < rangeEnd){
            mtoCalc = salaryHSTotal - rangeStart;
        }else if(salaryHSTotal > rangeEnd){
            mtoCalc = rangeEnd - rangeStart;
        }
        taxCalc = ((mtoCalc * this.getRenta2().getPercent())  / DAYS_BY_MOUNT) * DAYS_BY_BIWEEK ;
        this.setTax2(taxCalc / PORCENT);
    }

    /**
     * Calculo del 3er tracto de la renta
     */
    public void calcTaxThree(){
        Double rangeStart = this.getRenta3().getMountStart();
        Double salaryHSTotal = getSalaryRent();//this.getHsTotal();
        Double mtoCalc = 0d;
        Double taxCalc = 0d;
        if(salaryHSTotal > rangeStart){
            mtoCalc = salaryHSTotal - rangeStart;
        }
        taxCalc = ((mtoCalc * this.getRenta3().getPercent())  / DAYS_BY_MOUNT) * DAYS_BY_BIWEEK ;
        this.setTax3(taxCalc / PORCENT);
    }

/**
     * Calculo del 3er tracto de la renta
     */
    public void calcTaxFourth(){
        if (this.getRenta4()==null) {
            this.setTax4(0d);
            return;
        }
        Double rangeStart = this.getRenta4().getMountStart();
        Double salaryHSTotal = getSalaryRent();//this.getHsTotal();
        Double mtoCalc = 0d;
        Double taxCalc = 0d;
        if(salaryHSTotal > rangeStart){
            mtoCalc = salaryHSTotal - rangeStart;
        }
        taxCalc = ((mtoCalc * this.getRenta4().getPercent())  / DAYS_BY_MOUNT) * DAYS_BY_BIWEEK ;
        this.setTax4(taxCalc / PORCENT);
    }


    /**
     * Calculo para la caja.
     */
    public void calcCCSS(){
        
        Double mtoCalc = 0d;
        Double ccssDeduc = 0d;
        if(this.getCollaborator().getTypePayroll().equals(COLLABORATOR_BY_HOUR)){
            if(this.getHs() > this.getCollaborator().getCcssHoursWork()){
                mtoCalc = this.getCollaborator().getCcssHoursWork() * collaborator.getRate();
            }else{
                mtoCalc = this.getHsTotal();
            }
        }else{
            //if(this.getHs() > CCSS_COLLABORATOR_BY_MONTH){
            mtoCalc = this.getCollaborator().getCcssHoursWork() * collaborator.getRate();
                //mtoCalc = CCSS_COLLABORATOR_BY_MONTH * collaborator.getRate();
            //}else{
                //mtoCalc = this.getHsTotal();
            //}
        }

        Double porcentage=0d;
        if(this.getCcssDeduction()!=null)
            porcentage = this.getCcssDeduction().getPercent();

        ccssDeduc = mtoCalc * porcentage;

        this.setCcss(ccssDeduc / PORCENT);

    }

    public void calcOthersDecuctions(){
        if(this.getOthersDeduction()!=null){
            for(Deductions d:this.getOthersDeduction()){
                Double tempD= (this.getHsTotal() * d.getPercent()) / PORCENT;
                this.addOtherDeduction(tempD);
            }
        }
    }



    public void calcBruteSalry() {
       this.setBruteSalary(hsTotal + hmTotal + hdTotal);
    }


    private void calcRefunds(){
        for(RefundDevolution r: this.getRefunds()){
            if(r.getDeductionsRefundsDetails()!=null)
                this.addRefund(r.getDeductionsRefundsDetails().getFee());
        }
    }


    private void calcDevolutions(){
        for(RefundDevolution d: this.getDevolutions()){
            if(d.getDeductionsRefundsDetails()!=null)
                this.minusDevolution(d.getDeductionsRefundsDetails().getFee());
        }
    }

    public void addOthersDeduction(Deductions othersDeduction) {
        if(this.othersDeduction==null)
            this.othersDeduction = new ArrayList<Deductions>();
        this.othersDeduction.add(othersDeduction);
    }



    public void addLaborCostDetails(LaborCostDetail laborCostDetail){
        if(this.getLaborCostDetails()==null)
            this.laborCostDetails=new ArrayList<LaborCostDetail>();
        this.laborCostDetails.add(laborCostDetail);
        this.addHs(laborCostDetail.getHoursSimple());
        this.addHm(laborCostDetail.getHoursMedia());
        this.addHd(laborCostDetail.getHoursDouble());
    }

    public void addTaxes(List<Taxes> taxes){
        if (taxes.size() > 0)
            this.setRenta1(taxes.get(0));
        if (taxes.size() > 1)
            this.setRenta2(taxes.get(1));
        if (taxes.size() > 2)
            this.setRenta3(taxes.get(2));
        if (taxes.size() > 3)
            this.setRenta4(taxes.get(3));
    }












    //SETTER & GETTERS

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public Taxes getRenta1() {
        return renta1;
    }

    public void setRenta1(Taxes renta1) {
        this.renta1 = renta1;
    }

    public Taxes getRenta2() {
        return renta2;
    }

    public void setRenta2(Taxes renta2) {
        this.renta2 = renta2;
    }

    public Taxes getRenta3() {
        return renta3;
    }

    public void setRenta3(Taxes renta3) {
        this.renta3 = renta3;
    }


    public Taxes getRenta4() {
        return renta4;
    }

    public void setRenta4(Taxes r4) {
        this.renta4 = r4;
    }

    public Deductions getCcssDeduction() {
        return ccssDeduction;
    }

    public void setCcssDeduction(Deductions ccssDeduction) {
        this.ccssDeduction = ccssDeduction;
    }

    public Double getHs() {
        return hs;
    }

    public void setHs(Double hs) {
        this.hs = hs;
    }

    public Double getHsTotal() {
        return hsTotal;
    }

    public void setHsTotal(Double hsTotal) {
        this.hsTotal = hsTotal;
    }

    public Double getHm() {
        return hm;
    }

    public void setHm(Double hm) {
        this.hm = hm;
    }

    public Double getHmTotal() {
        return hmTotal;
    }

    public void setHmTotal(Double hmTotal) {
        this.hmTotal = hmTotal;
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public Double getHdTotal() {
        return hdTotal;
    }

    public void setHdTotal(Double hdTotal) {
        this.hdTotal = hdTotal;
    }



    public Double getTax1() {
        return tax1;
    }

    public void setTax1(Double tax1) {
        this.tax1 = tax1;
    }

    public Double getTax2() {
        return tax2;
    }

    public void setTax2(Double tax2) {
        this.tax2 = tax2;
    }

    public Double getTax3() {
        return tax3;
    }

    public void setTax3(Double tax3) {
        this.tax3 = tax3;
    }

    public Double getTax4() {
        return tax4;
    }

    public void setTax4(Double tax4) {
        this.tax4 = tax4;
    }

    public Double getCcss() {
        return ccss;
    }

    public void setCcss(Double ccss) {
        this.ccss = ccss;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public Double getBruteSalary() {
        return bruteSalary;
    }

    public void setBruteSalary(Double bruteSalary) {
        this.bruteSalary = bruteSalary;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Double getDevolution() {
        return devolution;
    }

    public void setDevolution(Double devolution) {
        this.devolution = devolution;
    }

    public List<RefundDevolution> getDevolutions() {
        return devolutions;
    }

    public void setDevolutions(List<RefundDevolution> devolutions) {
        this.devolutions = devolutions;
    }

    public List<RefundDevolution> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundDevolution> refunds) {
        this.refunds = refunds;
    }

    public List<Deductions> getOthersDeduction() {
        return othersDeduction;
    }

    public void setOthersDeduction(List<Deductions> othersDeduction) {
        this.othersDeduction = othersDeduction;
    }




    public Double getMtoOthersDeductions() {
        return mtoOthersDeductions;
    }

    public void setMtoOthersDeductions(Double mtoOthersDeductions) {
        this.mtoOthersDeductions = mtoOthersDeductions;
    }

    public List<LaborCostDetail> getLaborCostDetails() {
        return laborCostDetails;
    }

    public void setLaborCostDetails(List<LaborCostDetail> laborCostDetails) {
        this.laborCostDetails = laborCostDetails;
    }


}
