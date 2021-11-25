package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Refundable implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="collaborator_id", referencedColumnName="id")
    private Collaborator collaborator;

    private String codeInvoice;
    private Date dateInvoice;
    private Date dueDate;



    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    @OneToOne
    @JoinColumn(name="department_id", referencedColumnName="id")
    private Department department;

    private String reason;

    private String status;

    private Double advancesReceived =  0d;

    private Double netoPendingIngpro = 0d;
    private Double netoPendingCollab = 0d;

    private Double total;

    private Date createDate;
    private Integer idUser;
    private Date updateDate;

    private Boolean inClosing = false;
    private String statusClosing = "Ingresado";
    private Boolean ingresadoBodega = false;



    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,
            mappedBy = "refundable", orphanRemoval = true)
    private List<RefundableDetail> details = new ArrayList<RefundableDetail>(0);

    @OneToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id")
    private ExchangeRate exchangeRate;




    public Refundable() {
        java.util.Date date = new java.util.Date();
        Calendar c = Calendar.getInstance();
        Integer day = c.get(Calendar.DATE);
        Integer monthIndex = c.get(Calendar.MONTH) +  1;
        Integer year = c.get(Calendar.YEAR) - 2000;
        this.codeInvoice = day.toString() + '-' + monthIndex.toString()   + '-' + year.toString() + '_'+'A';
    }


    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Boolean getIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(Boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

    public String getStatusClosing() {
        return statusClosing;
    }

    public void setStatusClosing(String statusClosing) {
        this.statusClosing = statusClosing;
    }

    public Boolean getInClosing() {
        return inClosing;
    }

    public void setInClosing(Boolean inClosing) {
        this.inClosing = inClosing;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getNetoPendingCollab() {
        return netoPendingCollab;
    }

    public void setNetoPendingCollab(Double netoPendingCollab) {
        this.netoPendingCollab = netoPendingCollab;
    }

    public Double getNetoPendingIngpro() {
        return netoPendingIngpro;
    }

    public void setNetoPendingIngpro(Double netoPendingIngpro) {
        this.netoPendingIngpro = netoPendingIngpro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public String getCodeInvoice() {
        return codeInvoice;
    }

    public void setCodeInvoice(String codeInvoice) {
        this.codeInvoice = codeInvoice;
    }

    public Date getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(Date dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Double getAdvancesReceived() {
        return advancesReceived;
    }

    public void setAdvancesReceived(Double advancesReceived) {

        this.advancesReceived = advancesReceived;
    }

    public List<RefundableDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RefundableDetail> details) {
        this.details = details;
    }
}
