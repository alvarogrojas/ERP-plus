package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPay;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Calendar;


public class ScheduleBillPayWeekDTO implements Serializable {
    private static final long serialVersionUID = 1777000000000000061L;


    private Integer id;
    private Integer idCurrency;
    private String billNumber;
    private Date payDate;
    private Integer week;
    private Integer year;
    private String status;
    private String provider;
    private String type;
    private Double total;
    private Double subTotal;
    private Double tax;
    private Integer creditDay;

    public ScheduleBillPayWeekDTO() {
    }

    public ScheduleBillPayWeekDTO(BillPay i) {
        this.id = i.getId();
        this.idCurrency = i.getCurrency().getId();
        this.billNumber = i.getBillNumber();
        this.payDate = i.getPayDate();
        this.status = i.getStatus();
        this.provider = i.getProviderName();
        this.type = i.getType();
        this.total = i.getTotal();
        this.subTotal = i.getSubTotal();
        this.tax = i.getTax();
        this.creditDay = i.getCreditDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        this.week = cal.get(Calendar.WEEK_OF_YEAR);
        this.year = cal.get(Calendar.YEAR);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(BigInteger week) {
        this.week = week.intValue();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Integer getCreditDay() {
        return creditDay;
    }

    public void setCreditDay(Integer creditDay) {
        this.creditDay = creditDay;
    }



    public Integer getYear() {
        return year;
    }

    public void setYear(BigInteger year) {
        this.year = year.intValue();
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }
}
