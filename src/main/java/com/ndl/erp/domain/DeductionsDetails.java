package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class DeductionsDetails {

    @Id
    @GeneratedValue
    private Integer id;


//    private Integer idDeduction;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_deduction", referencedColumnName = "id")
    private Deductions deduction;

    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

    private Integer idUserRegister;

    private Date createDate;
    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Deductions getDeduction() {
        return deduction;
    }

    public void setDeduction(Deductions deduction) {
        this.deduction = deduction;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }
}
