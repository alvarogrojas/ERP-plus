package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="pay_roll")
public class PayRoll implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String note;
    private String status;
    private Boolean inClosing;


    private Date createDate;
    private Date updateDate;


    private Integer idUserGenerate;
    private Integer idUserAprove;
    private Integer idUserAction;
    private Date start;
    private Date end;
    private String statusClosing;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "payRoll", orphanRemoval = true)
    private Set<PayRollDetail> details;

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


    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getIdUserAction() {
        return idUserAction;
    }

    public void setIdUserAction(Integer idUserAction) {
        this.idUserAction = idUserAction;
    }

    public Integer getIdUserAprove() {
        return idUserAprove;
    }

    public void setIdUserAprove(Integer idUserAprove) {
        this.idUserAprove = idUserAprove;
    }

    public Integer getIdUserGenerate() {
        return idUserGenerate;
    }

    public void setIdUserGenerate(Integer idUserGenerate) {
        this.idUserGenerate = idUserGenerate;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public Set<PayRollDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<PayRollDetail> details) {
        this.details = details;
    }
}
