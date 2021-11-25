package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DeductionsRefunds {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private Double mount;
    private String status = "Activo";
    private String type;

    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "deductionRefund", orphanRemoval = true)
    private List<DeductionsRefundsDetails> details = new ArrayList<DeductionsRefundsDetails>(0);

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
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

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DeductionsRefundsDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DeductionsRefundsDetails> details) {
        this.details = details;
    }
}
