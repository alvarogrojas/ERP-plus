package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class DeductionsRefundsDetails implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer indice;
    private Double fee;
    private String status;

//    private Integer  idDeductionRefund;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_deduction_refund", referencedColumnName = "id")
    private DeductionsRefunds deductionRefund;

    private Integer idUserRegister;
    private Date createDate;
    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public DeductionsRefunds getDeductionRefund() {
        return deductionRefund;
    }

    public void setDeductionRefund(DeductionsRefunds deductionRefund) {
        this.deductionRefund = deductionRefund;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }
}
