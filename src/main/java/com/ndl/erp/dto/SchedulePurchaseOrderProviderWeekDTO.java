package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.PurchaseOrderProvider;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;


public class SchedulePurchaseOrderProviderWeekDTO implements Serializable {
    private static final long serialVersionUID = 1777000000000000062L;


    private Integer id;
    private Integer idCurrency;
    private Currency currency;
    private String orderNumber;
    private Date payDate;
    private Integer week;
    private Integer year;
    private String status;
    private String provider;
    private Double total;
    private Double subTotal;
    private Double iv;

    public SchedulePurchaseOrderProviderWeekDTO() {
    }

    public SchedulePurchaseOrderProviderWeekDTO(PurchaseOrderProvider pop) {
        id = pop.getId();
        orderNumber = pop.getOrderNumber();
        idCurrency = pop.getCurrency().getId();
        currency = pop.getCurrency();
        payDate = pop.getDatePay();
        status = pop.getStatus();
        provider = pop.getOrderNumber() + ", " + pop.getSourceName() + ", " + pop.getObservations();
        total = pop.getTotal();
        subTotal = pop.getSubTotal();
        iv = pop.getIv();
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


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
