package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.ProjectionsMaster;
import com.ndl.erp.domain.PurchaseOrderClient;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;


public class ScheduleProjectionsDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000056L;
    private Integer id;
    private Integer idPoc;
    private PurchaseOrderClient poc;
    private Integer idCostCenter;
    private CostCenter costCenter;
    private Integer idCurrency;
    private Currency currency;
    private Date dateProjection;
    private Double amount;
    private Integer week;


    public ScheduleProjectionsDTO() {
    }

    public ScheduleProjectionsDTO(ProjectionsMaster i) {
        this.id = i.getId();
        this.idPoc = i.getPoc().getId();
        this.currency = i.getPoc().getCurrency();
        this.idCurrency = currency.getId();
        this.costCenter = i.getCostCenter();
        this.dateProjection = i.getDate();
        this.amount = i.getAmount();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateProjection);
        this.week = cal.get(Calendar.WEEK_OF_YEAR);

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPoc() {
        return idPoc;
    }

    public void setIdPoc(Integer idPoc) {
        this.idPoc = idPoc;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Date getDateProjection() {
        return dateProjection;
    }

    public void setDateProjection(Date dateProjection) {
        this.dateProjection = dateProjection;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(BigInteger week) {
        this.week = week.intValue();
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public PurchaseOrderClient getPoc() {
        return poc;
    }

    public void setPoc(PurchaseOrderClient poc) {
        this.poc = poc;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
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
