package com.ndl.erp.dto;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Various;

import java.io.Serializable;
import java.sql.Date;


public class ScheduleVariosDTO implements ScheduleGastos, Serializable {


    private static final long serialVersionUID = 1777000000000000058L;
    private Integer id;
    private Integer idCurrency;
    private Currency currency;
    private String name;
    private Double amount;
    private Date  dateVarios;
    private String periodicity;



    public ScheduleVariosDTO() {
    }

    public ScheduleVariosDTO(Various i) {
        id = i.getId();
        idCurrency = i.getCurrency().getId();
        currency = i.getCurrency();
        name = i.getName();
        amount = i.getMount();
        dateVarios = i.getDate();
        periodicity = i.getPeriodicity();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDateVarios() {
        return dateVarios;
    }

    public void setDateVarios(Date dateVarios) {
        this.dateVarios = dateVarios;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
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
}
