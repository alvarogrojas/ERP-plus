package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Deductions {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String type;
    private Double percent;
    private Integer idUserRegister;

    private Date createDate;
    private Date updateDate;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "deduction", orphanRemoval = true)
    private List<DeductionsDetails> details = new ArrayList<DeductionsDetails>(0);


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
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

    public List<DeductionsDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DeductionsDetails> details) {
        this.details = details;
    }
}
