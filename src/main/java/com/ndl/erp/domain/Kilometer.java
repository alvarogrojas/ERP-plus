package com.ndl.erp.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Kilometer {


    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="collaborator_id", referencedColumnName="id")
    private Collaborator collaborator;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    @OneToOne
    @JoinColumn(name="department_id", referencedColumnName="id")
    private Department department;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "kilometer", orphanRemoval = true)
    private List<KilometerDetail> details = new ArrayList<KilometerDetail>(0);

    private String codeKm;

    private Date dateKm;

    private String reason;

    private Double totalKm;
    private Double total;
    private String status;
    private Date createDate;
    private Integer idUser;
    private Date updateDate;
    private Date dueDate;
    private Boolean inClosing = false;
    private String statusClosing = "Ingresado";

    public Kilometer() {
        java.util.Date date = new java.util.Date();
        Calendar c = Calendar.getInstance();
        Integer day = c.get(Calendar.DATE);
        Integer monthIndex = c.get(Calendar.MONTH) +  1;
        Integer year = c.get(Calendar.YEAR) - 2000;
        this.codeKm = day.toString() + '-' + monthIndex.toString()   + '-' + year.toString() + '_'+'A';
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDateKm() {
        return dateKm;
    }

    public void setDateKm(Date dateKm) {
        this.dateKm = dateKm;
    }

    public String getCodeKm() {
        return codeKm;
    }

    public void setCodeKm(String codeKm) {
        this.codeKm = codeKm;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDeparment(Department department) {
        this.department = department;
    }

    public Double getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(Double totalKm) {
        this.totalKm = totalKm;
    }

    public List<KilometerDetail> getDetails() {
        return details;
    }

    public void setDetails(List<KilometerDetail> details) {
        this.details = details;
    }
}
