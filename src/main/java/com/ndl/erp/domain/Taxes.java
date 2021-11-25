package com.ndl.erp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
public class Taxes {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    private Double percent;
    private Double mountStart;
    private Double mountEnd;



    private Integer idUserRegister;

    private Date createDate;
    private Date updateDate;


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

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public Double getMountStart() {
        return mountStart;
    }

    public void setMountStart(Double mountStart) {
        this.mountStart = mountStart;
    }

    public Double getMountEnd() {
        return mountEnd;
    }

    public void setMountEnd(Double mountEnd) {
        this.mountEnd = mountEnd;
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


}
