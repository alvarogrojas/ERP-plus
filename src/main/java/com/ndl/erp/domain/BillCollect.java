package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
public class BillCollect implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String billNumber;
    private Date billDate;
    private Date endDate;
    private String description;
    private String status;
    private Date payDate;
    private Date transactDate;
    private String proofPayment;
    private Integer creditDay;
    private String purchageOrder;
    private String interPurchageOrder;

    private Double tax;
    private Double exonerated;

    private Double subTotal;
    @OneToOne
    @JoinColumn(name="client_id", referencedColumnName="clientId")
    private Client client;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    private Integer userId;

    private Date createDate;
    private Date updateDate;

    private Double total;

    private Boolean inClosing;
    private String statusClosing;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "billCollect", orphanRemoval = true)
    private Set<BillCollectDetail> details;

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

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getTransactDate() {
        return transactDate;
    }

    public void setTransactDate(Date transactDate) {
        this.transactDate = transactDate;
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

    public String getPurchageOrder() {
        return purchageOrder;
    }

    public void setPurchageOrder(String purchageOrder) {
        this.purchageOrder = purchageOrder;
    }

    public String getInterPurchageOrder() {
        return interPurchageOrder;
    }

    public void setInterPurchageOrder(String interPurchageOrder) {
        this.interPurchageOrder = interPurchageOrder;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getInClosing() {
        return inClosing;
    }

    public void setInClosing(Boolean inClosing) {
        this.inClosing = inClosing;
    }

    public String getStatusClosing() {
        return statusClosing;
    }

    public void setStatusClosing(String statusClosing) {
        this.statusClosing = statusClosing;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public Set<BillCollectDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<BillCollectDetail> details) {
        this.details = details;
    }
}
