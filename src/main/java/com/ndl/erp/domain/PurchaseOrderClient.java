package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
public class PurchaseOrderClient {


    @Id
    @GeneratedValue
    private Integer id;

    private String orderNumber;
    private Date date = new Date(Calendar.getInstance().getTime().getTime());
    private String observations;
    private Integer consecutive;
    private Double subTotal;
    private Double iv;
    private Double exonerated;
    private Double total;
    private Double discountTotal = 0d;
    private String status = "Edicion";
    private Integer userId;

    @OneToOne
    @JoinColumn(name="client_id", referencedColumnName="clientId")
    private Client client;

    private Date createAt;
    private Date updateAt;
    private Date dateMp = new Date(Calendar.getInstance().getTime().getTime());
    private String statusMp = "Activo";

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "parent", orphanRemoval = true)
    private List<PurchaseOrderClientDetail> details = new ArrayList<>(0);

    @OneToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id")
    private ExchangeRate exchangeRate;


    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public PurchaseOrderClient() {
        this.date = new Date(Calendar.getInstance().getTime().getTime());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Integer getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(Integer consecutive) {
        this.consecutive = consecutive;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDateMp() {
        return dateMp;
    }

    public void setDateMp(Date dateMp) {
        this.dateMp = dateMp;
    }

    public String getStatusMp() {
        return statusMp;
    }

    public void setStatusMp(String statusMp) {
        this.statusMp = statusMp;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<PurchaseOrderClientDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseOrderClientDetail> details) {
        this.details = details;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }


}


