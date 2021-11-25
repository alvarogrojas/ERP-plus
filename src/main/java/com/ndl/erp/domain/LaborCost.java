package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ndl.erp.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LaborCost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String notes;

    private Date laborDate;

    private String status = "EDITABLE";

    private Date createDate;

    private Date updateDate;

    private Integer idUserRegister;

    @OneToOne
    @JoinColumn(name = "id_collaborator", referencedColumnName = "id")
    private Collaborator collaborator;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "laborCost", orphanRemoval = true)
    private List<LaborCostDetail> details = new ArrayList<LaborCostDetail>(0);

    public LaborCost() {
        setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
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


    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<LaborCostDetail> getDetails() {
        return details;
    }

    public void setDetails(List<LaborCostDetail> details) {
        this.details = details;
    }



 }
