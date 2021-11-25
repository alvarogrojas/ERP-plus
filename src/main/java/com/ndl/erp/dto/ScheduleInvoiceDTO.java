package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Invoice;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;


public class ScheduleInvoiceDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000056L;
    private Integer id;
    private Integer idCurrency;
    private Currency currency;
    private Date date;
    private Date dateInvoice;
    private Double iv;
    private Double subTotal;
    private Double total;
    private Integer week;

    public ScheduleInvoiceDTO() {
    }

    public ScheduleInvoiceDTO(Invoice i) {
        this.id = i.getId();
        this.idCurrency = i.getCurrency().getId();
        this.date = i.getDate();
        this.dateInvoice = i.getDatePay();
        this.iv = i.getIv();
        this.subTotal = i.getSubTotal();
        this.total = i.getTotal();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateInvoice);
        this.week = cal.get(Calendar.WEEK_OF_YEAR);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(Date dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
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

    public Integer getWeek() {
        return week;
    }

    public void setWeek(BigInteger week) {
        this.week =  week.intValue();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
