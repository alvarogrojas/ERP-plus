package com.ndl.erp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by wugalde on 3/24/17.
 */
@Entity
@Table(name = "pay_roll_collaborator_refund_devolution_detail")
public class PayRollCollaboratorRefundDevolutionDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="id_pay_roll", referencedColumnName="id")
    private PayRoll payRoll;

    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

    private Integer idDevolRefundDetail;

    private String description;
    private Double mount;

    private Integer idUserRegister;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;
    private String type;
    private Integer coutaNumber;

    @Transient
    private Integer maxCuota;

    public Integer getCoutaNumber() {
        return coutaNumber;
    }

    public void setCoutaNumber(Integer coutaNumber) {
        this.coutaNumber = coutaNumber;
    }

    public Integer getIdDevolRefundDetail() {
        return idDevolRefundDetail;
    }

    public void setIdDevolRefundDetail(Integer idDevolRefundDetail) {
        this.idDevolRefundDetail = idDevolRefundDetail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Integer getMaxCuota() {
        return maxCuota;
    }

    public void setMaxCuota(Integer maxCuota) {
        this.maxCuota = maxCuota;
    }
}
