package com.ndl.erp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ScheduleWeekDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000054L;


    private String title;
    private String  monthName;
    private Integer month;
    private Integer year;
    private Integer week;
    private boolean lastWeek=false;

    //Entradas y projeccioes
    private Double bank=0d;
    private Double amountInvoice=0d;
    private Double amountProjection=0d;
    private Double subTotal1=0d;


    //Gastos fijos
    private Double subTotal2=0d;

    //Cuentas por pagar
    private Double subTotal3=0d;

    //Gastos variables
    private Double subTotal4=0d;


    public ScheduleWeekDTO(String monthName, Integer month, Integer year, Integer week) {
        this.monthName = monthName;
        this.month = month;
        this.year = year;
        this.week = week;
    }

    public void plusSubTotal2(Double amount) {
        this.subTotal2+=amount;
    }
    public void plusSubTotal3(Double amount) {
        this.subTotal3+=amount;
    }

    public void plusSubTotal4(Double amount) {
        this.subTotal4+=amount;
    }

    public void plussInvoice(Double invoice){
       this.amountInvoice+= invoice;
    }

   public void plussProjection(Double projection){
       this.amountProjection+= projection;
    }

    public void calcBank(Double subTotal1Ant,Double subTotal2Ant, Double subTotal3Ant, Double subTotal4Ant){
        this.bank += subTotal1Ant - (subTotal2Ant + subTotal3Ant + subTotal4Ant);
    }

    public void calcSubtotal1(){
       this.subTotal1 += this.bank + this.amountInvoice + this.amountProjection;
    }

    public void calcSubtotal2(){
       //this.subTotal1 += this.bank + this.amountInvoice + this.amountProjection;
    }
    public void calcSubtotal3(){
       //this.subTotal1 += this.bank + this.amountInvoice + this.amountProjection;
    }

    public void calcSubtotal4(){
       //this.subTotal1 += this.bank + this.amountInvoice + this.amountProjection;
    }

    public ScheduleWeekDTO() {

    }



    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getAmountInvoice() {
        return amountInvoice;
    }

    public void setAmountInvoice(Double amountInvoice) {
        this.amountInvoice = amountInvoice;
    }

    public Double getAmountProjection() {
        return amountProjection;
    }

    public void setAmountProjection(Double amountProjection) {
        this.amountProjection = amountProjection;
    }

    public Double getBank() {
        return bank;
    }

    public void setBank(Double bank) {
        this.bank = bank;
    }

    public Double getSubTotal1() {
        return subTotal1;
    }

    public void setSubTotal1(Double subTotal1) {
        this.subTotal1 = subTotal1;
    }

    public Double getSubTotal2() {
        return subTotal2;
    }

    public void setSubTotal2(Double subTotal2) {
        this.subTotal2 = subTotal2;
    }

    public Double getSubTotal3() {
        return subTotal3;
    }

    public void setSubTotal3(Double subTotal3) {
        this.subTotal3 = subTotal3;
    }

    public Double getSubTotal4() {
        return subTotal4;
    }

    public void setSubTotal4(Double subTotal4) {
        this.subTotal4 = subTotal4;
    }

    public boolean isLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(boolean lastWeek) {
        this.lastWeek = lastWeek;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }







}
